import java.util.*;

/*
Based on the basic-python-network (part 2)
http://iamtrask.github.io/2015/07/12/basic-python-network/

Simple 2 layer network that doesn't rely on any matrix/math libraries.
*/

public class SimpleNeuralNetwork {

    public static Random r = new Random(1);

    public static double[][] nonlin(double m[][]) {
        return nonlin(m, false);
    }

    public static double[][] nonlin(double m[][], boolean derivative) {
        for (int i = 0; i < m.length; i++){   
            for (int j = 0; j < m[i].length; j++){                
                m[i][j] = nonlin(m[i][j], derivative);
            }
        }
        return m;
    }

    public static double nonlin(double x) {
        return nonlin(x, false);
    }

    public static double nonlin(double x, boolean derivative) {
        if (derivative) {
            return x*(1-x);
        }

        return 1/(1+Math.exp(-x));
    }

    public static void main(String[] args) {
        /*
        The network consists of 3 input nodes.
        Then a hidden layer with 4 nodes.
        Then an output layer with just 1 node.

        This runs an input sample forward through the network,
        calculates loss, and updates the weights accordingly using backpropogation.

        It does NOT use batch updating like the original python sample this is based on.
        */

        //4 1x3 matrices
        double x[][][] = {{{0,0,1}},
                         {{0,1,1}},
                         {{1,0,1}},
                         {{1,1,1}}};

        //4 1x1 matrices
        double y[][][] = {{{0}},
                         {{1}},
                         {{1}},
                         {{0}}};

        //3x4 = 12 connections
        double l1_weights[][] = MatrixUtil.randomMatrix(3,4,r);
        //4x1 = 4 connections
        double l2_weights[][] = MatrixUtil.randomMatrix(4,1,r);
        
        for (int epoch = 0; epoch < 5000; epoch++) {
            double error = 0.0;
            for (int s = 0; s < x.length; s++) {
                //1x3 = 3 values for inputs
                double l0[][] = x[s];

                //1x4 = values for 4 hidden nodes
                double l1[][] = nonlin(MatrixUtil.matrixMult(l0,l1_weights));

                //1x1 = output from network
                double l2[][] = nonlin(MatrixUtil.matrixMult(l1,l2_weights));
                
                //1x1 = error for network with single output node
                double l2_error[][] = MatrixUtil.matrixSub(y[s],l2);
                error += Math.abs(l2_error[0][0]);

                //1x1 = delta for network with single output node
                double l2_delta[][] = MatrixUtil.matrixMult(l2_error,nonlin(l2,true));

                //1x4 = error for connections back to hidden layer
                double l1_error[][] = MatrixUtil.matrixMult(l2_delta, MatrixUtil.transpose(l2_weights));

                //1x4 = delta for connections 
                double l1_delta[][] = MatrixUtil.matrixProd(l1_error, nonlin(l1,true));

                //Update weights
                l2_weights = MatrixUtil.matrixAdd(l2_weights, MatrixUtil.matrixMult(MatrixUtil.transpose(l1), l2_delta));
                l1_weights = MatrixUtil.matrixAdd(l1_weights, MatrixUtil.matrixMult(MatrixUtil.transpose(l0), l1_delta));
            }
            if (epoch % 1000 == 0) {
                System.out.println("Error: " + error/4.0);
            }
        }

    }
}