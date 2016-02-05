// Instructions:
// Write code for matrix-matrix multiplication and matrix-vector multiplication

public class MatrixTool {

    static double[][] matrixMult (double[][] A, double[][] B)
    {
	    double[][] C = new double[A[0].length][B.length];
    
		for(int i=0; i < C.length; i++){ 
			for(int j=0; j < C[0].length; j++){
				for(int k=0; k < A[0].length; k++){
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
    }

    static double[] matrixVectorMult (double[][] A, double[] v)
    {
		double[] y = new double[v.length];
		
		for(int i = 0; i<v.length; i++){
			for(int j = 0; j<A.length; j++){
				y[i] += A[i][j] * v[j];
			}
		}
		return y;
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
		double dp = 0;
		for(int i = 0; i< v.length; i++)
		{
			dp += v[i] * u[i];
		}
		return dp;
    }

    public static double norm (double[] u)
    {
		double norm = 0;
		for(int i = 0; i<u.length; i++)
		{
			norm += u[i]*u[i];
		}
		norm = sqrt(norm);
		return 0; // Temporarily
    }

    public static double[] proj (double[] v, double[] u)
    {
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
