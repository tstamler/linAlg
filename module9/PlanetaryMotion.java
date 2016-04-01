
import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class PlanetaryMotion extends Application {

    String title = "Planetary Motion";

    void drawingCommands () 
    {
	// Rotation about z:
	double theta = Math.PI/3.0;
	double a11 = Math.cos (theta);
	double a12 = -Math.sin (theta);
	double a21 = Math.sin (theta);
	double a22 = Math.cos (theta);
	double[][] A = {
	    {a11, a12, 0, 0},
	    {a21, a22, 0, 0},
	    {0,   0,   1, 0},
	    {0,   0,   0, 1}
	};

	/*
	// INSERT YOUR CODE for rotation about x by 30 degrees
	double[][] B = {

	};

	// INSERT YOUR CODE for translation by (2,3,4)
	double[][] C = {

	};

	double[][] temp = MatrixTool.matrixMult (B,A);
	double[][] transform = MatrixTool.matrixMult (C,temp);
	*/

	// Replace this transform with the above after implementing
	// matrices B and C
	double[][] transform = A;


	double x0 = 0, y0 = 0; 	  // Center of original 2D ellipse.
	double a = 7;             // Major axis
	double b = 4;             // Minor axis
	double delT = 0.25;       // t-increment for drawing

	d3.setDrawColor (Color.RED);
	for (double t=0; t<=2*Math.PI+delT; t+=delT) {
	    double[] x = new double[4];
	    x[0] = a * Math.cos(t);
	    x[1] = b * Math.sin(t);
	    x[2] = 0;
	    x[3] = 1;
	    double[] z = MatrixTool.matrixVectorMult (transform,x);
	    d3.drawPoint (z[0],z[1],z[2]);
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
