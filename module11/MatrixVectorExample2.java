// Instructions:
// Try the following starting vectors: (1,1), (0,1), (0.1,1), (1,2), (1.1,2), (1.01,2).

public class MatrixVectorExample2 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-2,2, -2,2);
	DrawTool.drawMiddleAxes (true);

	double[][] A = {
	    {5,-2},
	    {0,1}
	};

	// Now start with some initial vector u
	double[] u = {1,0};

	// Draw u
	DrawTool.drawVector (u);             // In black.

	// We'll draw the vectors obtained in sequence:
	DrawTool.setArrowColor ("blue");

	int n = 10;
	for (int i=0; i<n; i++) {

	    // Apply A to u.
	    u = MatrixTool.matrixVectorMult (A, u);

	    // We'll normalize the length so that we stay 
	    // within the bounds of the drawing area. This is fine
	    // because we just want the direction.
	    u = normalize (u);

	    DrawTool.drawVector (u);
	}

    }

    static double[] normalize (double[] x)
    {
	double normX = MatrixTool.norm (x);
	double[] y = new double [x.length];
	for (int i=0; i<x.length; i++) {
	    y[i] = (1.0 / normX) * x[i];
	}
	return y;
    }

}

