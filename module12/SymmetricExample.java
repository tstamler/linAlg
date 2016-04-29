// Instructions:
// Use your MatrixTool code for transpose and multiplication.
//
// Note: you will need lintool in your CLASSPATH

import edu.gwu.lintool.*;

public class SymmetricExample {

    public static void main (String[] argv)
    {
	// Symmetric A
	double[][] A = {
	    {1,2,3},
	    {2,4,8},
	    {3,8,2}
	};

	LinResult L = LinToolLibrary.computeEigenvaluesAndVectors (A);

	// The eigenvalues.
	LinUtil.print ("lambda", L.lambda);

	// The eigenvector matrix happens to be called S in LinResult:
	double[][] S = L.S;
	LinUtil.print ("S", L.S);

	// Compute the transpose of S:
	double[][] ST = MatrixTool.transpose (S);

	// Left multiply S by S^T:
	double[][] STS = MatrixTool.matrixMult (ST, S);

	// Verify orthogonality:
	LinUtil.print ("STS", STS);
    }


}
