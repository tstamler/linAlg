// ImageSearch.java
//
// Author: Rahul Simha
// July, 2015
//
// To demonstrate the eigenfaces algorithm.
// Needs: ImageTool.java in same directory.
// What to see in the code: all of the code up to findClosestImage().

import edu.gwu.lintool.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

public class ImageSearch {

    // Where the data is stored.
    static String trainingDir = "trainingImages";
    static String eigenDir = "eigenImages";

    // Number of images.
    static int M = 10;

    // For the actual image data, imageNumStart=1. For Geometric, it is 0.
    static int imageNumStart = 0;
    static int imageNumEnd = imageNumStart + M;

    // #rows, cols of images: assumed to be identical. Set after reading images.
    static int numImageRows, numImageCols;

    // Size of each vector: N = numImageRows*numImageCols.
    static int N;

    // ImageTool handles the reading, writing and display of images.
    static ImageTool imTool = new ImageTool ();

    // What we store from processing the training set:
    static int R;            // # singular values (from SVD).
    static double[] avg;     // to subtract off
    static double[][] Y_T;   // Transformed coords, tranposed for multiplying
    static double[][] U_T;   // Transformed new basis, transposed


    public static void main (String[] argv)
    {
	// Convert images to vectors:
	processTrainingImages ();

	// Get a test image and find the closest match in the training data.
	int k = findClosestImage ("queryImages/testimage0.png");
	System.out.println ("Best match for testimage0: image" + k + " in the training data");

    }

    static void processTrainingImages ()
    {
	// 1. Process images in training data.
	double[][] data = imagesToVectors ();

	// 2. Get the matrix U
	double[][] U = processTrainingImages (data);
	System.out.println ("U: " + U.length + " x " + U[0].length);

	// 3. This is not really needed, but interesting to see.
	storeEigenImages (U);
    }


    static int findClosestImage (String fileName)
    {
	// 1. Read in query image.
	int[][] testPixels = imTool.imageFileToGreyPixels (fileName);

	// 2. Convert query to vector: N x 1
	double[] w = imageToVector (testPixels);

	int k = findClosestImage (w);

	// 4. k is the closest image found.
	return k;
    }

    static int findClosestImage (double[] w)
    {
	// What's alredy been done:
	// 1.  Convert image to grey pixels.
	// 2.  Convert 2D image of pixels to 1D vector w.

	// 3. Subtract avg.
	for (int i=0; i<N; i++) {
	    w[i] = w[i] - avg[i];
	}

	// 4. Get y = U^T * w
	//      R x N  times N x 1  gives R x 1
	double[] y = MatrixTool.matrixVectorMult (U_T, w);

	// 5. Cosine similarity search: smaller angle, larger cosine.
	double max = 0;  int k = -1;
	for (int i=0; i<Y_T.length; i++) {
	    //  Y_T is M x R  and y is R x 1 so that each dot-product
	    //  is a row of Y_T (of length R) times y.
	    double c = cosine (Y_T[i], y);
	    if (c > max) {
		max = c;
		k = i;
	    }
	}
	// We get the closest among M.

	return k;
    }


    static double[][] processTrainingImages (double[][] data)
    {
	// 1. Get average column to normalize.
	avg = computeAverageVector (data);

	// 2. Make normalized data matrix. Each column is a normalized image.
	//    Note: X is N x M.
	double[][] X = normalizeData (data, avg);

	// 3. Get SVD of X, to get U.
	LinResult L = LinToolLibrary.computeSVDShortForm (X);
	R = L.rank;
	System.out.println ("R=" + R);

	// We're going to leave this out for now.
	// 4. Reduce by setting some sigma's to zero.

	// 5. Get eigenbasis U.
	double[][] U = L.U;

	System.out.println ("#rows of U=" + U.length + " #cols of U=" + U[0].length);

	// 6. Compute transformed coordinates
	// 6a. First, the transpose of U, which will now be R x N.
	U_T = MatrixTool.transpose (U);

	// 6b.Each column of Y has the transformed coords. 
	//    R x N  times a N x M matrix gives Y, which will be R x M
	double[][] Y = MatrixTool.matrixMult (U_T, X);

	// 6c. Easier to compare if we first transpose Y.
	//     Y_T is M x R
	Y_T = MatrixTool.transpose (Y);

	System.out.println("Size of U is: "+U.length+"\tSize of U[0]: "+U[0].length);

	return U;
    }


    // No need to read further, unless overpowered by a thirst for knowledge
    //
    ////////////////////////////////////////////////////////////////
    // BELOW: useful methods but not essential to understanding.

    static double cosine (double[] u, double[] v) 
    {
	if (v.length != u.length) {
	    System.out.println ("ERROR: cosine: unequal lengths: u.len=" + u.length + " v.len=" + v.length);
	    System.exit (0);
	}
	double c = MatrixTool.dotProduct (u, v) / ( MatrixTool.norm(u) *  MatrixTool.norm(v));
	return c;
    }


    static double[][] normalizeData (double[][] data, double[] avg)
    {
	double[][] X = new double [N][M];
	for (int i=0; i<N; i++) {
	    for (int j=0; j<M; j++) {
		X[i][j] = data[i][j] - avg[i];
	    }
	}
	return X;
    }

    static double[] computeAverageVector (double[][] data)
    {
	// Need column length.
	double[] avg = new double [N];

	for (int i=0; i<N; i++) {
	    double sum = 0;
	    for (int j=0; j<M; j++) {
		// Sum across the M columns.
		sum += data[i][j];
	    }
	    avg[i] = sum / M;
	}

	return avg;
    }




    static void storeEigenImages (double[][] U)
    {
	// Each column of U is an image. 
	for (int j=0; j<R; j++) {
	    double[] v = extractColumn (U, j);
	    for (int i=0; i<N; i++) {
		v[i] += avg[i];
	    }
	    int[][] greyPixels = vectorToImage (v, numImageRows, numImageCols);
	    imTool.writeToFile (greyPixels, eigenDir + "/eigen" + j + ".png");
	}
    }

    static double[] extractColumn (double[][] U, int j)
    {
	// Extract j-th eigen-image
	// Length of column is # rows.
	double[] v = new double [U.length];
	double max = Double.MIN_VALUE;
	for (int i=0; i<U.length; i++) {
	    v[i] = U[i][j];
	    if (v[i] > max) max = v[i];
	}
	return v;
    }


    static double[][] imagesToVectors ()
    {
	ArrayList<double[]> imagesAsVectors = new ArrayList<double[]> ();
	int minLength = Integer.MAX_VALUE; 
	int maxLength = -1;
	for (int i=imageNumStart; i<imageNumEnd; i++) {
	    String fileName = trainingDir + "/image" + i + ".png";
	    int[][] greyPixels = imTool.imageFileToGreyPixels (fileName);
	    numImageRows = greyPixels.length;
	    numImageCols = greyPixels[0].length;
	    double[] v = imageToVector (greyPixels);
	    imagesAsVectors.add (v);
	    if (v.length > maxLength) maxLength = v.length;
	    if (v.length < minLength) minLength = v.length;
	}
	
	// Fix lengths if they aren't all the same.
	if (minLength != maxLength) {
	    //** For now, we'll just note the error and stop.
	    System.out.println ("min=" + minLength + " max=" + maxLength);
	    System.exit (0);
	}

	// This is the length of each vector.
	N = minLength;

	// Now place vectors in a N x M matrix.
	double[][] data = new double [N][M];
	for (int i=0; i<M; i++) {
	    double[] v = imagesAsVectors.get (i);
	    for (int j=0; j<N; j++) {
		// Note the switch: there are N rows, M columns.
		data[j][i] = v[j];  
	    }
	}

	return data;
    }


    static int[][] vectorToImage (double[] v, int numRows, int numCols)
    {
	// Check length:
	if (numRows*numCols != v.length) {
	    System.out.println ("ERROR: incompatible conversion to image");
	    System.exit (0);
	}
	int[][] greyPixels = new int [numRows][numCols];
	int k = 0;
	for (int i=0; i<greyPixels.length; i++) {
	    for (int j=0; j<greyPixels[0].length; j++) {
		greyPixels[i][j] = (int) v[k++];
	    }
	}
	return greyPixels;
    }

    static double[] imageToVector (int[][] greyPixels)
    {
	int len = greyPixels.length * greyPixels[0].length;
	double[] v = new double [len];
	int k = 0;
	for (int i=0; i<greyPixels.length; i++) {
	    for (int j=0; j<greyPixels[0].length; j++) {
		v[k++] = (double) greyPixels[i][j];
	    }
	}
	return v;
    }

}
