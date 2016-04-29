
public class MatrixTool {

    public static double[][] matrixMult (double[][] A, double[][] B)
    {
	double[][] C = new double [A.length][B[0].length];
	for (int i=0; i<C.length; i++) {
	    for (int j=0; j<C[0].length; j++) {
		// Compute C[i][j] = prod of A[i] row and B[j] column.
		double s = 0;
		for (int k=0; k<A[i].length; k++) {
		    s += A[i][k] * B[k][j];
		}
		C[i][j] = s;
	    }
	}

	return C;
    }

    public static double[] matrixVectorMult (double[][] A, double[] v)
    {
	// INSERT YOUR CODE HERE:
	double[] b = new double [A.length];
	for (int i=0; i<A.length; i++) {
	    double s = 0;
	    for (int j=0; j<A[i].length; j++) {
		s += A[i][j] * v[j];
	    }
	    b[i] = s;
	}

	return b;
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
	double sum = 0;
	for (int i=0; i<v.length; i++) {
	    sum += v[i] * u[i];
	}
	return sum;
    }

    public static double norm (double[] u)
    {
	return Math.sqrt (dotProduct(u,u));
    }

    public static double[] proj (double[] v, double[] u)
    {
	// INSERT YOUR CODE HERE
	double alpha = dotProduct(u,v) / (norm(u)*norm(u));
	double[] w = new double [u.length];
	for (int i=0; i<u.length; i++) {
	    w[i] = alpha * u[i];
	}
	return w;
    }

    public static double[] scalarMult (double alpha, double[] u)
    {
	double[] w = new double [u.length];
	for (int i=0; i<u.length; i++) {
	    w[i] = alpha * u[i];
	}
	return w;
    }

    public static double[] sub (double[] u, double[] v)
    {
	double[] w = new double [u.length];
	for (int i=0; i<w.length; i++) {
	    w[i] = u[i] - v[i];
	}
	return w;
    }

    public static double[] add (double[] u, double[] v)
    {
	double[] w = new double [u.length];
	for (int i=0; i<w.length; i++) {
	    w[i] = u[i] + v[i];
	}
	return w;
    }

    public static double[][] transpose (double[][] A)
    {
	// INSERT YOUR CODE HERE:

	if (A == null) {
	    return null;
	}

	double[][] B = new double [A[0].length][A.length];
	for (int i=0; i<B.length; i++) {
	    for (int j=0; j<B[0].length; j++) {
		B[i][j] = A[j][i];
	    }
	}
	return B;
    }

}
