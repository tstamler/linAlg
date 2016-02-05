// Instructions:
// Write code in the matrixMult() method to compute the
// the product of two matrices. Also, copy over your code
// for matrixVectorMult() from MatrixVectorExample.java

public class MatrixVectorExample2 {

    public static void main (String[] argv)
    {
		double[][] A = {
			{2, -3},
				{0, 1}
			};
		double[][] B = {
			{1, 2},
				{0, -3}
			};
		double[] x = {2, 3};

		double[][] C = matrixMult (B, A);
		print (C);

		double[] z = matrixVectorMult (C, x);
		print (z);

		double[][] A2 = {
				{3, 2, 1},
				{-2, 3, 5},
				{0, 0, 3}
			};
		double[][] B2 = {
				{-4, 1, 0},
				{1, 0, 1},
				{3, -2, 1}
		};
		double[] x2 = {1, -1, 2};

		System.out.println("Exercise 21:");
		
		double[] y2 = matrixVectorMult (A2, x2);
		print (y2);

		double[] z2 = matrixVectorMult (B2, y2);
		print (z2);
		
		double[][] C2 = matrixMult(B2, A2);
		print (C2);
		
		double[] confirm = matrixVectorMult(C2, x2);
		print (confirm);
		
		DrawTool.display ();
		DrawTool.setXYRange (-10,10, -10,10);
		DrawTool.drawMiddleAxes (true);
		DrawTool.drawVector (x);
		DrawTool.setArrowColor ("green");
		DrawTool.drawVector (z);
    }

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

    static void print (double[] x)
    {
		System.out.print ("Vector:");
		for (int i=0; i<x.length; i++) {
			System.out.printf (" %6.3f", x[i]);
		}
		System.out.println ();
    }

    static void print (double[][] A)
    {
		System.out.println ("Matrix (" + A.length + "x" + A[0].length + "):");
		for (int i=0; i<A.length; i++) {
			for (int j=0; j<A[0].length; j++) {
			System.out.printf (" %6.3f", A[i][j]);
			}
			System.out.println ();
		}
    }
}

