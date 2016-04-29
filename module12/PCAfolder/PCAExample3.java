// Instructions:
// Compile and execute.

import java.util.*;

public class PCAExample3 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (-10,10, -10,10);
        //DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 20;
	ArrayList<DataPoint> dataPoints = DataSource.getData (3, numPoints);

	// Apply PCA to change coordinates, 4 clusters.
	ArrayList<DataPoint> changedPoints = PCA.pcaCoords (dataPoints);

	double sum0=0, sum1=0;
	for (DataPoint p: changedPoints) {
	    sum0 += p.x[0];
	    sum1 += p.x[1];
	}
	System.out.println ("sum0=" + sum0 + " sum1=" + sum1);

	// Display:
	DataViewer.drawResults (changedPoints);

    }

}
