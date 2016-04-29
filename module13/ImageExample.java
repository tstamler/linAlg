// Instructions:
// Try different values of p.

import edu.gwu.lintool.*;

public class ImageExample {

    public static void main (String[] argv)
    {
	// Extract a greyscale image into a matrix A:
	ImageTool imTool = new ImageTool ();
	int[][] pixels = imTool.imageFileToGreyPixels ("Sylvester.png");
	double[][] A = convertToDouble (pixels);

	// Get the SVD.
	LinResult L = LinToolLibrary.computeSVD (A);
	int r = L.rank;

	// We'll now try different outerproducts, up through the rank.

	// Vary p: p=1, 5, 10, 50, 100, r
	int p = 100;
	double[][] B = outerProduct (p, L.U, L.Sigma, L.V);

	// Convert matrix back to image:
	int[][] pixels2 = convertToInt (B);
	imTool.writeToFile (pixels2, "testimage-" + p + ".png");

	// Examine actual image in the same directory.
    }



    static double[][] outerProduct (int p, double[][] U, double[][] Sigma, double[][] V)
    {
	int m = U.length;
	int n = V.length;
	double[][] S = new double [m][n];
	for (int k=0; k<p; k++) {
	    // Extract p-th column of U and scale by sigma.
	    double[] u = new double [m];
	    for (int i=0; i<m; i++) {
		u[i] = U[i][k] * Sigma[k][k];
	    }
	    // Extract p-th column of V
	    double[] v = new double [n];
	    for (int j=0; j<n; j++) {
		v[j] = V[j][k];
	    }
	    // Compute the matrix  u * v^T
	    double[][] T = columnRowProduct (u, v);
	    // Accumulate in S.
	    for (int i=0; i<m; i++) {
		for (int j=0; j<n; j++) {
		    S[i][j] += T[i][j];
		}
	    }
	}

	return S;
    }


    static double[][] columnRowProduct (double[] c, double[] r)
    {
	int m = c.length;
	int n = r.length;
	double[][] A = new double [m][n];
	for (int i=0; i<m; i++) {
	    for (int j=0; j<n; j++) {
		A[i][j] = c[i] * r[j];
	    }
	}
	return A;
    }

    static double[][] convertToDouble (int[][] A)
    {
	double[][] B = new double [A.length][A[0].length];
	for (int i=0; i<B.length; i++) {
	    for (int j=0; j<B[0].length; j++) {
		B[i][j] = A[i][j];
	    }
	}
	return B;
    }

    static int[][] convertToInt (double[][] B)
    {
	int[][] A = new int [B.length][B[0].length];
	for (int i=0; i<A.length; i++) {
	    for (int j=0; j<A[0].length; j++) {
		A[i][j] = (int) B[i][j];
		if (A[i][j] < 0) A[i][j] = 0;
		if (A[i][j] > 255) A[i][j] = 255;
	    }
	}
	return A;
    }

}
