// Instructions:
// Compile and execute.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class DataViewer3D extends Application {

    String title = "3D data view";

    void drawingCommands () 
    {
	// Get data:
	int numPoints = 50;
	ArrayList<DataPoint> dataPoints = DataSource.getData (1, numPoints);

	// Plot points.
	d3.setXYZRange (5, 5, 5);
	for (DataPoint p: dataPoints) {
	    if (p.trueCluster == 0) {
		d3.setDrawColor (Color.BLUE);
	    }
	    else {
		d3.setDrawColor (Color.RED);
	    }
	    d3.drawPoint (p.x[0], p.x[1], p.x[2]);
	}
    }

    // No need to read further
    //////////////////////////////////////////////////////////

    Draw3D d3;

    void preambleCommands ()
    {
	d3.setAmbientLight(false);
	d3.setPointLight(true);
	d3.setCumulate(false);
	d3.setSequencingOn();
	d3.setVectorRadius(1);
        d3.setArrowRadius(1);
    }

    public void start (Stage primaryStage) 
    {
	d3 = new Draw3D ();
	Scene scene = d3.buildScene ();
	preambleCommands ();
	drawingCommands ();
	d3.setStart ();
	primaryStage.setScene (scene);
	primaryStage.setTitle (title);
	primaryStage.show ();
    }

    public static void main (String[] argv)
    {
	launch (argv);
    }

}
