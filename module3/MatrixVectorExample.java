// Instructions:
// Write code in the method matrixVectorMult() below to 
// compute the product of a matrix and a vector. Although
// the examples are 2x2, assume a general NxN size. You can
// assume that the matrix and vector are size-matched.

public class MatrixVectorExample {

    public static void main (String[] argv)
	{
		double[][] A = {
			{2, -3},
				{0, 1}
			};
		double[] x = {2, 3};

		// Compute Ax = y and print y.
		double[] y = matrixVectorMult (A, x);
		print (y);

		double[][] B = {
			{1, 2},
				{0, -3}
			};
		double[] z = matrixVectorMult (B, y);
		print (z);

		double[][] C = {
			{2, -1},
				{0, -3}
			};
		double[] Cx = matrixVectorMult (C, x);
		print (Cx);
		
		double[][] C2 = {
			{1/2, -1/6},
				{0, -1/3}
			};
		double[] Cz = matrixVectorMult (C2, z);
		print (Cz);
		
		DrawTool.display ();
		DrawTool.setXYRange (-10,10, -10,10);
		DrawTool.drawMiddleAxes (true);
		DrawTool.drawVector (x);
		DrawTool.setArrowColor ("blue");
		DrawTool.drawVector (y);
		DrawTool.setArrowColor ("green");
		DrawTool.drawVector (z);
    }


    static double[] matrixVectorMult (double[][] A, double[] v)
    {
		double[] y = new double[v.length];
		
		for(int i = 0; i<v.length; i++)
		{
			for(int j = 0; j<A.length; j++)
			{
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

}
