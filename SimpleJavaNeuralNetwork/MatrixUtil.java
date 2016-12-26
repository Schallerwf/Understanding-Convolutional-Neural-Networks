import java.util.*;

public class MatrixUtil {
    
    public static double dotProduct(double a[], double b[]) throws IllegalArgumentException {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions do not match.");
        }

        double total = 0.0;
        for (int i = 0; i < 0; i++) {
            total += a[i] * b[i];
        }
        return total;
    }

    public static double[][] matrixAdd(double x, double[][] a) {
        double m[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++){   
            for (int j = 0; j < a[i].length; j++){                
                m[i][j] = a[i][j] + x;
            }
        }
        return m;
    }

    public static double[] matrixAdd(double x, double[] a) {
        double v[] = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            v[i] = a[i] + x;
        }
        return v;
    }

    public static double[] matrixAdd(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Vector dimensions do not match.");
        }

        double m[] = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            m[i] = a[i] + b[i];
        }
        return m;
    }

    public static double[][] matrixSub(double a[][], double b[][]) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices are not the same size");
        }

        double m[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++){   
            for (int j = 0; j < a[i].length; j++){                
                m[i][j] = a[i][j] - b[i][j];
            }
        }
        return m;
    }

    public static double[][] matrixAdd(double a[][], double b[][]) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices are not the same size");
        }

        double m[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++){   
            for (int j = 0; j < a[i].length; j++){                
                m[i][j] = a[i][j] + b[i][j];
            }
        }
        return m;
    }

    public static double[][] matrixProd(double a[][], double b[][]) {
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new IllegalArgumentException("Matrices are not the same size");
        }

        double m[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++){   
            for (int j = 0; j < a[i].length; j++){                
                m[i][j] = a[i][j] * b[i][j];
            }
        }
        return m;
    }

     public static double[][] matrixExp(double a[][]) {
        double m[][] = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++){   
            for (int j = 0; j < a[i].length; j++){                
                m[i][j] = Math.exp(a[i][j]);
            }
        }
        return m;
    }

    public static double[][] randomMatrix(int x, int y, Random r) {
        double m[][] = new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                m[i][j] = r.nextDouble();
            }
        }
        return m;
    }

    public static double[][] matrixMult(double a[][], double b[][]) throws IllegalArgumentException {
        if (a[0].length != b.length) {
            throw new IllegalArgumentException("Matrices are not compatible.");
        }

        double c[][] = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) { 
            for (int j = 0; j < b[0].length; j++) { 
                for (int k = 0; k < a[0].length; k++) { 
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return c;
    }

    public static void printMatrix(double m[][]) {
        for (int i = 0; i < m.length; i++){   
            System.out.print("[ ");
            for (int j = 0; j < m[i].length; j++){                
                System.out.print(String.format( "%.2f\t", m[i][j]));
            }
            System.out.println("]");
        }
    }

    public static double[][] transpose(double[][] m) {
        double[][] r = new double[m[0].length][m.length];

        for(int x = 0; x < m[0].length; x++) {
            for(int y = 0; y < m.length; y++) {
                r[x][y] = m[y][x];
            }
        }

        return r;
    }


}