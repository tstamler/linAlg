// Instructions:
// (1) Compile and execute to try v = Au where u=(0,1)
// (2) Change to u=(1,2) and repeat.
// (3) Change to u=(1,0) and repeat.
// (4) With u=(1,0), set A[0][0]=-5.

public class MatrixVectorExample {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-5,5, -5,5);
	DrawTool.drawMiddleAxes (true);

	double[][] A = {
	    {5,-2},
	    {0,1}
	};

	// Try (1) u=(0,1), (2) u=(1,2), (3) u=(1,0)
	double[] u = {1,2};

	// Apply A to u
	double[] v = MatrixTool.matrixVectorMult (A, u);

	// Print both:
	MatrixTool.print (u);
	MatrixTool.print (v);

	// Draw both.
	DrawTool.drawVector (u);             // Draw u in black.
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v);
    }

}

