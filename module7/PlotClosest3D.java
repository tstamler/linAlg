// Instructions:
// Insert your solution to alpha, beta to plot the closest vector.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class PlotClosest3D extends Application {

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

	// INSERT the solution to alpha, beta
	double alpha = -0.9;
	double beta = 2.6;

	// Use alpha, beta in the linear combination of c1, c2:
	double[] y = MatrixTool.add (
			MatrixTool.scalarMult (alpha, c1),
			MatrixTool.scalarMult (beta, c2)
		     );

	// The difference vector z:
	double[] z = MatrixTool.sub (b, y);

	// Draw y and z:
	d3.setDrawColor (Color.GREEN);
	d3.drawVector (y);
	d3.setDrawColor (Color.RED);
	d3.drawArrow (y[0],y[1],y[2], b[0],b[1],b[2]);
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
