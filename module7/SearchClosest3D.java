// Instructions:
// (1) First, compile and execute.
// (2) Then, un-comment the "difference" vector. Compile and execute.
// (3) Draw by hand (on paper) the best difference.
// (4) Then, change the range of alpha/beta to broaden the search.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class SearchClosest3D extends Application {

    String title = "search example";

    void drawingCommands () 
    {
	// RHS b
	double[] b = {5,6,3};
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (b);

	// Only two columns.
	double[] c1 = {6,2,0};
	double[] c2 = {4,3,0};
	d3.drawVector (c1);
	d3.drawVector (c2);

	// Search through y = alpha*c1 + beta*c2 to find that y
	// which is closest to b.

	// Change the range to see if you can find a better y.
	double alphaLow=-1, alphaHigh=1, alphaStep=0.1;
	double betaLow=-1, betaHigh=1, betaStep=0.1;
	double min = Double.MAX_VALUE;
	double[] bestY = {0,0};

	for (double alpha=alphaLow; alpha<=alphaHigh; alpha+=alphaStep) {
	    for (double beta=betaLow; beta<=betaHigh; beta+=betaStep) {
		double[] y = MatrixTool.add (
				MatrixTool.scalarMult (alpha, c1),
				MatrixTool.scalarMult (beta, c2)
			     );
		// Find the difference vector and its length:
		double[] z = MatrixTool.sub (b, y);
		double zLength = MatrixTool.norm (z);
		if (zLength < min) {
		    // Record best so far.
		    min = zLength;
		    bestY = y;
		}
	    }
	}

	// Draw best y:
	d3.setDrawColor (Color.GREEN);
	d3.drawVector (bestY);

	// Un-comment to see the difference:
	
	d3.setDrawColor (Color.RED);
	d3.drawArrow (bestY[0],bestY[1],bestY[2], b[0],b[1],b[2]);
	
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
