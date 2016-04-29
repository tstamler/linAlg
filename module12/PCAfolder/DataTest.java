// This is just to print some of the data.

import edu.gwu.lintool.*;
import java.util.*;

public class DataTest {

    public static void main (String[] argv)
    {
	double[][] X = {
	    {1.0, 1.5, 0.5, 2.0, 1.5, 2.5},
	    {2.5, 3.5, 3.0, 4.0, 4.5, 0.5},
	    {-1.0, 0.5, 0, -0.5, 0, 1.0}
	};

	double[] mean = computeMean (X);
	X = centerData (X, mean);
	MatrixTool.print (X);
	double[][] C = computeCovariance (X);
	// Eigenvector basis for the covariance matrix:
	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (C);
	double[][] S = L.S;
	S = reverseColumns (L.S);         // From big-lambda to small-lambda
	double[][] ST = MatrixTool.transpose (S);
	double[][] Y = MatrixTool.matrixMult (ST, X);
	LinUtil.print ("Y", Y);
	mean = computeMean (Y);
    }

    static double[] computeMean (double[][] X)
    {
	int m = X.length;
	int n = X[0].length;

	double[] mean = new double [m];

	// Compute the mean.
	for (int i=0; i<m; i++) {
	    double sum = 0;
	    for (int j=0; j<n; j++) {
		sum += X[i][j];
	    }
	    mean[i] = sum / n;
	    System.out.println ("i=" + i + " mean=" + mean[i]);
	}

	return mean;
    }

    static double[][] centerData (double[][] X, double[] mean)
    {
	int m = X.length;
	int n = X[0].length;

	double[][] Y = new double [m][n];

	for (int i=0; i<m; i++) {
	    for (int j=0; j<n; j++) {
		Y[i][j] = X[i][j] - mean[i];
	    }
	}

	return Y;
    }

    static double[][] computeCovariance (double[][] X)
    {
	int m = X.length;
	int n = X[0].length;

	double[][] C = new double [m][m];
	for (int j=0; j<n; j++) {
	    for (int i=0; i<m; i++) {
		for (int k=0; k<m; k++) {
		    C[i][k] += X[i][j] * X[k][j];
		}
	    }
	}

	// Average: divide by n = # samples.
	for (int i=0; i<m; i++) {
	    for (int j=0; j<m; j++) {
		C[i][j] = C[i][j] / n;
	    }
	}
	
	return C;
    }

    static double[][] reverseColumns (double[][] A)
    {
	double[][] B = new double [A.length][A[0].length];
	int numCols = A[0].length;
	for (int k=0; k<numCols; k++) {
	    // k-th col of B = numCols-1-k column of A;
	    for (int i=0; i<A.length; i++) {
		B[i][k] = A[i][numCols-1-k];
	    }
	}
	return B;
    }

}
