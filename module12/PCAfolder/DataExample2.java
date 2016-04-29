// Instructions:
// Compile and execute

import java.util.*;

public class DataExample2 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
        DrawTool.setXYRange (-10,10, -10,10);
        DrawTool.drawMiddleAxes (true);

	// Get data:
	int numPoints = 40;
	ArrayList<DataPoint> dataPoints = DataSource.getData (2, numPoints);

	// Display:
	DataViewer.drawData (dataPoints);

    }
}
