// Instructions:
// Compile and execute.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class MatrixVectorExample3D extends Application {

    String title = "3D matrix-vector example";

    void drawingCommands () 
    {
	double[][] A = {
	    {1,-2, 0},
	    {0,1,-2},
	    {2,-1,0}
	};

	// An initial vector:
	double[] u = {1,0,0};

	// Draw u
	d3.setXYZRange (2,2,2);
	d3.setDrawColor (Color.RED);
	d3.drawVector (u);            

	// We'll draw the vectors obtained in sequence:
	d3.setDrawColor (Color.BLUE);
	int n = 10;

	for (int i=0; i<n; i++) {

	    // Apply A to u.
	    u = MatrixTool.matrixVectorMult (A, u);

	    // We'll normalize the length so that we stay 
	    // within the bounds of the drawing area. This is fine
	    // because we just want the direction.
	    u = normalize (u);

	    d3.drawVector (u);
	    MatrixTool.print (u);
	}

    }

    double[] normalize (double[] x)
    {
	double normX = MatrixTool.norm (x);
	double[] y = new double [x.length];
	for (int i=0; i<x.length; i++) {
	    y[i] = (1.0 / normX) * x[i];
	}
	return y;
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
