// Instructions:
// (1) First compile and execute to see data points.
// (2) Un-comment the section below and add your code for
//     the least-squares matrix and matrix-vector products.
//     Compile and execute to the see the plane.

import edu.gwu.lintool.*;
import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class RegressionExample3D  extends Application {

    String title = "3-variable regression example";

    void drawingCommands () 
    {
	int n = 9;
	double[] x = {1.1,  1.5, 2.2,  2,   3.1,  3.2, 2.9, 1.1, 2};
	double[] y = {0.9,  1.9, 1.2,  2.2, 1,    2.1, 3.2, 2.8, 3};
	double[] z = {-0.1, 0.6, 0.63, 1.2, 0.85, 1.8, 2.1, 1.1, 1.4};


	// Display the points
	d3.setDrawColor (Color.RED);
	for (int i=0; i<n; i++) {
	    d3.drawSphere (x[i],y[i],z[i], 0.2);
	}

	// Put the data in a matrix:
        double[][] A = {
	    {1.1, 0.9, -1},
	    {1.5, 1.9, -1},
	    {2.2, 1.2, -1},
	    {2.0, 2.2, -1},
	    {3.1, 1,   -1},
	    {3.2, 2.1, -1},
	    {2.9, 3.2, -1},
	    {1.1, 2.8, -1},
	    {2.0, 3.0, -1}
	};

	// The RHS vector b is -z
	double[] b = new double [n];
	for (int i=0; i<n; i++) {
	    b[i] = -z[i];
	}

	/*

	// INSERT YOUR CODE HERE for least squares
	// Compute the transpose of A:
	double[][] AT = 

	// Multiply A by AT (with AT on the left)
	double[][] ATA = 

	// Get the inverse of ATA:
	LinResult L = LinToolLibrary.inverse (ATA);
	if (L.Ainv == null) {
	    System.out.println ("No inverse exists for ATA");
	    System.exit (0);
	}

	// Compute approx solution x = ATA^-1 * A^T * b in two steps:
	// Step 1: first compute AT * b
	double[] ATb = 
	// Step 2: multiply the result on the left by ATA^-1
	double[] xhat = 

	MatrixTool.print (xhat);   
	// Should print a'=-0.499, b'=-0.583, d'=-1.179

	// Now we need to convert the least-squares solution to the
	// parameters of the plane: a',b',c=1,d'
	d3.setDrawColor (Color.BLUE);
	d3.drawPlane (xhat[0], xhat[1], 1, xhat[2]);

	*/

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
