// Instructions:
// Compile and execute

import java.util.*;

public class DataExample3 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (-10,10, -10,10);
        DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 20;
	ArrayList<DataPoint> dataPoints = DataSource.getData (3, numPoints);

	// Display:
	DataViewer.drawData (dataPoints);

    }
}
