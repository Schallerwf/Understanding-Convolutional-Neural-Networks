from random import randint
from subprocess import call
import argparse
import sys

verbose = False
MIN_RADIUS = 2
MIN_SIZE = 2


def createSquareImage(imageSize, imageName, bgColor='white', borderColor='black', circleColor='black', size=-1, cords=[]):
    if size == -1:
        size = randint(MIN_SIZE, imageSize/3)
    if len(cords) == 0:
        xCord = randint(size, imageSize - size)
        yCord = randint(size, imageSize - size)
    else:
        xCord = cords[0]
        yCord = cords[1]
    
    cmd = 'convert -size {0}x{0} xc:{1} -fill {2} -stroke {3} -draw "rectangle {4},{5} {7},{8}" {6}'.format(imageSize, bgColor, circleColor, borderColor, xCord-size, yCord-size, imageName, xCord+size, yCord+size)

    if (verbose):
        print 'Executing imagemagick cmd: ' + cmd    

    result = call(cmd, shell=True)
    
    if result != 0:
        raise Exception('ImageMagick command failed with status code {0}. Halting.'.format(result))

    return [size, xCord, yCord]

def createCircleImage(imageSize, imageName, bgColor='white', borderColor='black', circleColor='black', radius=-1, cords=[]):
    if radius == -1:
        radius = randint(MIN_RADIUS, imageSize/3)
    if len(cords) == 0:
        xCord = randint(radius, imageSize - radius)
        yCord = randint(radius, imageSize - radius)
    else:
        xCord = cords[0]
        yCord = cords[1]

    cmd = 'convert -size {0}x{0} xc:{1} -fill {2} -stroke {3} -draw "circle {4},{5} {4},{7}" {6}'.format(imageSize, bgColor, circleColor, borderColor, xCord, yCord, imageName, yCord+radius)

    if (verbose):
        print 'Executing imagemagick cmd: ' + cmd    

    result = call(cmd, shell=True)
    
    if result != 0:
        raise Exception('ImageMagick command failed with status code {0}. Halting.'.format(result))

    return [radius, xCord, yCord]

def generateSimpleTruthSet(args):
    flag = False
    with open(args['truthLabel'], 'w+') as truthLabels:
        truthLabels.write('imageName,type,size,xCord,yCord\n')    

        for ndx in range(int(args['numSamples'])):
            if flag:
                imageName= '{1}/{0}_circle.jpg'.format(ndx, args['outputDir'])
                truth = createCircleImage(int(args['imageSize']), imageName)
                truthLabels.write('{0},0,{1},{2},{3}\n'.format(imageName, truth[0], truth[1], truth[2]))
            else: 
                imageName= '{1}/{0}_square.jpg'.format(ndx, args['outputDir'])
                truth = createSquareImage(int(args['imageSize']), imageName)
                truthLabels.write('{0},1,{1},{2},{3}\n'.format(imageName, truth[0], truth[1], truth[2]))

            flag = not flag
   
def main(args):
    parser = argparse.ArgumentParser()
    parser.add_argument('-s', '--imageSize', help='Size of images to generate. Default=13', default=13, required=False)
    parser.add_argument('-o', '--outputDir', help='Directory to output images', required=True)
    parser.add_argument('-n', '--numSamples', help='Number of samples to generate. Default=10000', default=10000, required=False)
    parser.add_argument('-t', '--truthLabel', help='Name of file to store truth labels. Default=truth.txt', default='truth.txt', required=False)    

    args = vars(parser.parse_args())

    generateSimpleTruthSet(args)


if __name__ == "__main__":
    main(sys.argv) 
