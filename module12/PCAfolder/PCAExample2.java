// Instructions:
// Compile and execute.

import java.util.*;

public class PCAExample2 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (-10,10, -10,10);
        DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 40;
	ArrayList<DataPoint> dataPoints = DataSource.getData (2, numPoints);

	// Get the eigenvector matrix S
	double[][] S  = PCA.pcaEigenvectors (dataPoints);

	// Display:
	DataViewer.drawData (dataPoints);
	DrawTool.setArrowColor ("green");
	// We'll stretch the arrow just for visual emphasis of the direction.
	double alpha = 5;
	DrawTool.drawVector (alpha*S[0][0], alpha*S[1][0]);

	// The original (unit) vector, which appears small.
	DrawTool.setArrowColor ("black");
	DrawTool.drawVector (S[0][0], S[1][0]);

    }

}
