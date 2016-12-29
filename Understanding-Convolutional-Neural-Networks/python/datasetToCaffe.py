from random import randint
from subprocess import call
from sets import Set
import argparse
import sys

def linesInFile(fn):
    with open(fn) as f:
        count = 0
        for l in f:
            count += 1
    return count

def selectRandomPercent(totalSamples, percent):
    selected = Set()
    amountToSelect = (percent * totalSamples) / 100.0
    while len(selected) < amountToSelect:
        selected.add(randint(0, totalSamples))
    return selected

def generateLMDB(args):
    testSamples = selectRandomPercent(linesInFile(args['input']) - 1, int(args['splitRatio'].split('x')[0]))

    testFile = '{0}_test.txt'.format(args['prefix'])
    trainFile = '{0}_train.txt'.format(args['prefix'])
    # Split truth labels into two files. Test and train.
    with open(args['input']) as inputCSV, open(testFile, 'w+') as test, open(trainFile, 'w+') as train:
        ndx = 0
        inputCSV.readline() #ignore header of csv
        for line in inputCSV:
            parts = line.rstrip().split(',')
            
            if ndx in testSamples:
                test.write('{0} {1}\n'.format(parts[0], parts[1]))
            else:
                train.write('{0} {1}\n'.format(parts[0], parts[1]))

            ndx += 1

    # Use Caffe to generate LMDBs from images
    cmd = 'sudo {3} -shuffle=true -gray=false -resize_height={0} -resize_width={0} ./ {1} {2}_train.lmdb'.format(args['size'], trainFile, args['prefix'], args['convert'])

    result = call(cmd, shell=True)
    if result != 0:
        raise Exception('convert_imageset failed with status code {0}. Halting.'.format(result))

    cmd = 'sudo {3} -shuffle=true -gray=false -resize_height={0} -resize_width={0} ./ {1} {2}_test.lmdb'.format(args['size'], testFile, args['prefix'], args['convert'])

    result = call(cmd, shell=True)    
    if result != 0:
        raise Exception('convert_imageset failed with status code {0}. Halting.'.format(result))

def main(args):
    parser = argparse.ArgumentParser()
    parser.add_argument('-i', '--input', help='CSV file containing truth data.', required=True)
    parser.add_argument('-p', '--prefix', help='Prefix to apply to lmdb databases. Default=""', default='', required=False)
    parser.add_argument('-x', '--splitRatio', help='Ratio to split into test and train. Default=80x20', default='80x20', required=False)
    parser.add_argument('-s', '--size', help='Size of images. Default=13', default=13, required=False)
    parser.add_argument('-c', '--convert', help='Path to convert_imageset from Caffe.', required=True)

    args = vars(parser.parse_args())

    generateLMDB(args)

if __name__ == "__main__":
    main(sys.argv)
