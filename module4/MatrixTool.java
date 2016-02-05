// Instructions:
// Write code for matrix-matrix multiplication and matrix-vector multiplication

public class MatrixTool {

    public static double[] matrixVectorMult (double[][] A, double[] v)
    {
	// INSERT YOUR CODE HERE:

    }

    public static double[][] matrixMult (double[][] A, double[][] B)
    {
	// INSERT YOUR CODE HERE to compute A times B.
	// Here, A and B are just parameters and not the same
	// as the A and B matrices in main().

	return null;  // Temporarily, so it will compile.
    }


    public static void print (double[] x)
    {
	System.out.print ("Vector:");
	for (int i=0; i<x.length; i++) {
	    System.out.printf (" %6.3f", x[i]);
	}
	System.out.println ();
    }

    public static void print (double[][] A)
    {
	System.out.println ("Matrix (" + A.length + "x" + A[0].length + "):");
	for (int i=0; i<A.length; i++) {
	    for (int j=0; j<A[0].length; j++) {
		System.out.printf (" %6.3f", A[i][j]);
	    }
	    System.out.println ();
	}
    }
    
    public static double dotProduct (double[] v, double[] u)
    {
	// INSERT YOUR CODE HERE

	return 0; // Temporarily
    }

    public static double norm (double[] u)
    {
	// INSERT YOUR CODE HERE

	return 0; // Temporarily
    }

    public static double[] proj (double[] v, double[] u)
    {
	// INSERT YOUR CODE HERE to compute the project of v on u.

	return null; // Temporarily
    }

    public static double[] sub (double[] u, double[] v)
    {
	double[] w = new double [u.length];
	for (int i=0; i<w.length; i++) {
	    w[i] = u[i] - v[i];
	}
	return w;
    }
}
