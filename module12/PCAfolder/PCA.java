// PCA.java
//
// Author: Rahul Simha
// Spring 2016
//
// Demonstrates basic PCA and kernel-PCA

import edu.gwu.lintool.*;
import java.util.*;

public class PCA {

    static ArrayList<DataPoint> pcaCoords (ArrayList<DataPoint> dataPoints)
    {
	// Center the data so that the mean is zero:
	ArrayList<DataPoint> centeredPoints = center (dataPoints);

	// Compute the covariance matrix:
	double[][] C = computeCovariance (centeredPoints);

	// Eigenvector basis for the covariance matrix:
	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (C);
	double[][] S = L.S;
	S = reverseColumns (L.S);         // From big-lambda to small-lambda

	// Transpose
	double[][] ST = MatrixTool.transpose (S);

	// Compute new coordinates.
	ArrayList<DataPoint> changedPoints = new ArrayList<DataPoint> ();
	for (DataPoint p: dataPoints) {
	    double[] y = MatrixTool.matrixVectorMult (ST, p.x);
	    DataPoint q = new DataPoint (y, p.trueCluster);
	    changedPoints.add (q);
	}

	return changedPoints;
    }


    static double[][] pcaEigenvectors (ArrayList<DataPoint> dataPoints)
    {
	// Center the data so that the mean is zero:
	ArrayList<DataPoint> centeredPoints = center (dataPoints);

	// Compute the covariance matrix:
	double[][] C = computeCovariance (centeredPoints);

	// Eigenvector basis for the covariance matrix:
	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (C);
	double[][] S = L.S;
	S = reverseColumns (L.S);         // From big-lambda to small-lambda

	return S;
    }


    // When the number of clusters K is known, the first K coords of
    // the PCA coords are useful in computing clusters.

    static ArrayList<DataPoint> pcaCoords (ArrayList<DataPoint> dataPoints, int numClusters)
    {
	// Center the data so that the mean is zero:
	ArrayList<DataPoint> centeredPoints = center (dataPoints);

	// Compute the covariance matrix:
	double[][] C = computeCovariance (centeredPoints);

	// Eigenvector basis for the covariance matrix:
	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (C);
	double[][] S = L.S;
	S = reverseColumns (L.S);

	// Now remove all but the first K columns.
	S = extractColumns (S, numClusters);

	// Transpose
	double[][] ST = MatrixTool.transpose (S);
	ST = normalizeRows (ST);

	// Compute new coordinates.
	ArrayList<DataPoint> changedPoints = new ArrayList<DataPoint> ();
	for (DataPoint p: dataPoints) {
	    double[] y = MatrixTool.matrixVectorMult (ST, p.x);
	    DataPoint q = new DataPoint (y, p.trueCluster);
	    changedPoints.add (q);
	}

	// Here is where a clustering algorithm would cluster based
	// on the changed coordinates.

	return changedPoints;
    }

    static ArrayList<DataPoint> kernelPcaCoords (ArrayList<DataPoint> dataPoints, int numClusters)
    {
	// sim(xi, xj) = exp ( |xi - xj| / sigma^2 )
	double sigma = 1;

	// Compute the similarity (high => more similar) for each pair of points.
	ArrayList<DataPoint> simPoints = computeSimilarity (dataPoints, sigma);

	// Now, the rest of PCA, but on the rows of the similarity matrix.
	ArrayList<DataPoint> centeredPoints = center (simPoints);
	double[][] C = computeCovariance (centeredPoints);
	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (C);
	double[][] S = L.S;
	S = reverseColumns (L.S);
	S = extractColumns (S, numClusters);
	double[][] ST = MatrixTool.transpose (S);
	ST = normalizeRows (ST);

	// Compute new coordinates.
	ArrayList<DataPoint> changedPoints = new ArrayList<DataPoint> ();
	for (DataPoint p: simPoints) {
	    double[] y = MatrixTool.matrixVectorMult (ST, p.x);
	    DataPoint q = new DataPoint (y, p.trueCluster);
	    changedPoints.add (q);
	}

	return changedPoints;
    }


    // No need to read further. 
    /////////////////////////////////////////////////////////////


    static ArrayList<DataPoint> center (ArrayList<DataPoint> dataPoints)
    {
	ArrayList<DataPoint> centeredPoints = new ArrayList<DataPoint>();
	int d = dataPoints.get(0).x.length;
	double[] sum = new double [d];
	for (DataPoint p: dataPoints) {
	    for (int i=0; i<d; i++) {
		sum[i] += p.x[i];
	    }
	}
	for (DataPoint p: dataPoints) {
	    double[] x = new double [d];
	    for (int i=0; i<d; i++) {
		x[i] = p.x[i] - (sum[i] / dataPoints.size());
	    }
	    DataPoint q = new DataPoint (x, p.trueCluster);
	    centeredPoints.add (q);
	}

	return centeredPoints;
    }


    static double[][] computeCovariance (ArrayList<DataPoint> dataPoints)
    {
	int n = dataPoints.size ();
	int d = dataPoints.get(0).x.length;
	double[][] C = new double [d][d];
	for (int k=0; k<n; k++) {
	    DataPoint p = dataPoints.get (k);
	    // Covariance sum
	    for (int i=0; i<d; i++) {
		for (int j=0; j<d; j++) {
		    C[i][j] += p.x[i] * p.x[j];
		}
	    }
	}

	// Average.
	for (int i=0; i<d; i++) {
	    for (int j=0; j<d; j++) {
		C[i][j] = C[i][j] / n;
	    }
	}

	return C;
    }

    static ArrayList<DataPoint> computeSimilarity (ArrayList<DataPoint> dataPoints, double sigma)
    {
	int n = dataPoints.size ();
	double[][] A = computeSimilarityMatrix (dataPoints, sigma);
	A = normalizeRows (A);
	
	ArrayList<DataPoint> simPoints = new ArrayList<DataPoint>();
	for (int i=0; i<n; i++) {
	    DataPoint pi = dataPoints.get (i);
	    DataPoint q = new DataPoint (A[i], pi.trueCluster);
	    simPoints.add (q);
	}

	return simPoints;
    }

    static double[][] computeSimilarityMatrix (ArrayList<DataPoint> dataPoints, double sigma)
    {
	int n = dataPoints.size ();
	double[][] A = new double [n][n];
	for (int i=0; i<n; i++) {
	    DataPoint pi = dataPoints.get (i);
	    for (int j=0; j<n; j++) {
		DataPoint pj = dataPoints.get (j);
		double d = distance (pi, pj);
		double similarity = Math.exp (-d*d / sigma);
		A[i][j] = similarity;
	    }
	}
	
	// This is to provide more markov-mixing:
	double[][] B = computePowerSum (A, 5);
	return B;
    }

    static double[][] computePowerSum (double[][] A, int p)
    {
	int n = A.length;
	if (A[0].length != n) {
	    System.out.println ("ERROR: Compute power: non-square matrix");
	    System.exit (0);
	}
	double[][] B = new double [n][n];
	for (int k=0; k<=p; k++) {
	    double[][] C = computePower (A, k);
	    for (int i=0; i<n; i++) {
		for (int j=0; j<n; j++) {
		    B[i][j] += C[i][j];
		}
	    }
	}

	return B;
    }

    static double[][] computePower (double[][] A, int p)
    {
	int n = A.length;
	if (A[0].length != n) {
	    System.out.println ("ERROR: Compute power: non-square matrix");
	    System.exit (0);
	}
	if (p == 0) {
	    // Return identity.
	    double[][] B = new double [n][n];
	    for (int k=0; k<n; k++) B[k][k] = 1;
	    return B;
	}

	// Otherwise, p-th power.
	double[][] B = copy (A);
	for (int k=2; k<=p; k++) {
	    B = MatrixTool.matrixMult (A, B);
	}

	return B;
    }

    static double distance (DataPoint p, DataPoint q)
    {
	double sum = 0;
	for (int k=0; k<p.x.length; k++) {
	    sum += (p.x[k] - q.x[k]) * (p.x[k] - q.x[k]);
	}
	return Math.sqrt (sum);
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


    static double[][] normalize (double[][] A)
    {
	double[][] B = new double [A.length][A[0].length];
	int numCols = A[0].length;
	int numRows = A.length;
	for (int k=0; k<numCols; k++) {
	    // k-th col of B = 
	    // Get norm.
	    double sum = 0;
	    for (int i=0; i<A.length; i++) {
		sum += A[i][k] * A[i][k];
	    }
	    double norm = Math.sqrt (sum);
	    // Normalize.
	    for (int i=0; i<A.length; i++) {
		B[i][k] = A[i][k] / norm;
	    }
	}
	return B;
    }


    static double[][] normalizeRows (double[][] A)
    {
	double[][] B = new double [A.length][A[0].length];
	int numCols = A[0].length;
	int numRows = A.length;
	for (int r=0; r<numRows; r++) {
	    double sum = 0;
	    for (int c=0; c<numCols; c++) {
		sum += A[r][c] * A[r][c];
	    }
	    double norm = Math.sqrt (sum);
	    // Normalize.
	    for (int c=0; c<numCols; c++) {
		B[r][c] = A[r][c] / norm;
	    }
	}
	return B;
    }

    static double[][] extractColumns (double[][] A, int numColumns)
    {
	double[][] B = new double [A.length][numColumns];
	for (int k=0; k<numColumns; k++) {
	    for (int i=0; i<A.length; i++) {
		B[i][k] = A[i][k];
	    }
	}	
	return B;
    }    


    static double[][] copy (double[][] A)
    {
	double[][] B = new double [A.length][A[0].length];
	for (int i=0; i<A.length; i++) {
	    for (int j=0; j<A[0].length; j++) {
		B[i][j] = A[i][j];
	    }
	}
	return B;
    }

    static double rowNorm (double[][] A, int r)
    {
	double sum = 0;
	for (int k=0; k<A[0].length; k++) {
	    sum += A[r][k] * A[r][k];
	}
	return Math.sqrt (sum);
    }

}


