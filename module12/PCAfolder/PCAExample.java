// Instructions:
// Compile and execute

import java.util.*;

public class PCAExample {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (0,50, -0.3,0.3);
        DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 50;
	ArrayList<DataPoint> dataPoints = DataSource.getData (1, numPoints);

	// Apply PCA to change coordinates:
	ArrayList<DataPoint> changedPoints = PCA.pcaCoords (dataPoints);	
	// Display:
	Function F = new Function ("PCA coords");
	for (int i=0; i<dataPoints.size(); i++) {
	    DataPoint p = dataPoints.get (i);
	    if (p.trueCluster == 0) {
		DrawTool.setPointColor ("blue");
	    }
	    else {
		DrawTool.setPointColor ("red");
	    }
	    // Plot only the first dimension:
	    DrawTool.drawPoint (i, p.x[0]);
	}

    }

}
