// Instructions:
// Try p=1 and p=r 
//
// Note: you will need lintool.jar in your CLASSPATH

import edu.gwu.lintool.*;

public class SVDExample2 {

    public static void main (String[] argv)
    {
	// A matrix, m=3, n=5
	double[][] A = {
	    {2,1,3,0,3},
	    {1,0,1,1,2},
	    {3,2,5,-1,4}
	};

	// We'll compute the SVD the usual way:
	LinResult L = LinToolLibrary.computeSVD (A);

	// Verify the product U * Sigma * V^T
	double[][] VT = MatrixTool.transpose (L.V);
	double[][] B = MatrixTool.matrixMult (L.U,L.Sigma);
	double[][] C = MatrixTool.matrixMult (B, VT);
	LinUtil.print ("C", C);

	// The rank of SVD
	int r = L.rank;

	// Now let's compute the outerproduct sum from k=1,...,p
	// The max value of p (the full matrix A) is p = r.

	// Try p=r, then p=1.

	int p = r;
	double[][] D = outerProduct (p, L.U, L.Sigma, L.V);
	LinUtil.print ("D", D);
    }

    static double[][] outerProduct (int p, double[][] U, double[][] Sigma, double[][] V)
    {
	int m = U.length;
	int n = V.length;

	// We'll accumulate the sum in here.
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

	} // outer-for

	return S;

    }


    static double[][] columnRowProduct (double[] c, double[] r)
    {
	int m = c.length;
	int n = r.length;

	double[][] A = new double [m][n];

	for (int i=0; i<m; i++) {
	    for (int j=0; j<n; j++) {
		// i-th element of column times j-th element of row
		A[i][j] = c[i] * r[j];
	    }
	}

	return A;
    }

}
