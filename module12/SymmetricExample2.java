// Instructions:
// Create a rank=1 symmetric matrix
//
// Note: you will need lintool in your CLASSPATH

import edu.gwu.lintool.*;

public class SymmetricExample2 {

    public static void main (String[] argv)
    {
	// Symmetric A, 3x3, rank=1
	double[][] A = {
	    // INSERT YOUR CODE HERE
		{1,2,3},
		{2,4,6},
		{0,0,0}
	};

	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (A);

	// The eigenvalues.
	LinUtil.print ("lambda", L.lambda);
    }


}
