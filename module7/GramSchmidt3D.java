// Instructions:
// (1) Compile and execute to see the vectors w1, w2, w3
// (2) Un-comment the computation of v1, v2, v3. Compile and execute.

import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import java.util.*;

public class GramSchmidt3D extends Application {

    String title = "Gram-Schmidt 3D example";

    void drawingCommands ()
    {
	// The original vectors: not orthogonal.
	double[] w1 = {6,2,4};
	double[] w2 = {4,3,1};
	double[] w3 = {1,2,6};

	d3.drawVector (w1);
	d3.drawVector (w2);
	d3.drawVector (w3);

	// Un-comment this section in the second step:


	// v1 is always w1.
	double[] v1 = w1;

	// Compute projection of w2 on v1:
	double alpha = MatrixTool.dotProduct(w2,v1) / MatrixTool.dotProduct(v1,v1);
	double[] y1 = MatrixTool.scalarMult (alpha, v1);

	// Subtract the projection from w2 to give v2.
	double[] v2 = MatrixTool.sub (w2, y1);

	// Compute projection of w3 on plane defined by v1,v2. This is
	// the subspace W2.
	double alpha1 = MatrixTool.dotProduct(w3,v1) / MatrixTool.dotProduct(v1,v1);
	double alpha2 = MatrixTool.dotProduct(w3,v2) / MatrixTool.dotProduct(v2,v2);

	double[] y2 = MatrixTool.add (
			 MatrixTool.scalarMult (alpha1, v1),
			 MatrixTool.scalarMult (alpha2, v2)
		      );

	// Subtract projection from w3 to get v3.
	double[] v3 = MatrixTool.sub (w3, y2);

	d3.setDrawColor (Color.BLUE);
	d3.drawVector (v1);
	d3.drawVector (v2);
	d3.drawVector (v3);

	// Confirm orthogonality:
	double v1Dotv2 = MatrixTool.dotProduct (v1, v2);
	double v1Dotv3 = MatrixTool.dotProduct (v1, v3);
	double v2Dotv3 = MatrixTool.dotProduct (v2, v3);
	System.out.println ("v1 dot v2 = " + v1Dotv2);
	System.out.println ("v1 dot v3 = " + v1Dotv3);
	System.out.println ("v2 dot v3 = " + v2Dotv3);

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
