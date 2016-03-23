// Instructions:
// Enter the solution x.


public class FourVariableExample2 {

    public static void main (String[] argv)
    {
	// The coefficient matrix A:
	double[][] A = {
	    {2, 1, 1, 3},
	    {1, -1, 1, 0},
	    {0, 0, 2, 1},
	    {1, 0, 1, 0}
        };

	// Right hand side:
	double[] b = {6,-2,-2,1};

	// Enter solution:
	double[] x = {};

	// b2 should be equal to b.
	double[] b2 = MatrixTool.matrixVectorMult (A, x);
	MatrixTool.print (b2);
    }

}
