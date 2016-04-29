// Instructions:
// Compile and execute.

import java.util.*;

public class KernelPCAExample {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (0,40, -1,1);
        DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 40;
	ArrayList<DataPoint> dataPoints = DataSource.getData (4, numPoints);

	// Apply PCA to change coordinates, 2 clusters.
	ArrayList<DataPoint> changedPoints = PCA.kernelPcaCoords (dataPoints, 2);

	// Display:
	for (int i=0; i<changedPoints.size(); i++) {
	    DataPoint p = changedPoints.get (i);
	    if (p.trueCluster == 0) {
		DrawTool.setPointColor ("blue");
	    }
	    else {
		DrawTool.setPointColor ("red");
	    }
	    DrawTool.drawPoint (i, p.x[0]);
	}



    }

}
