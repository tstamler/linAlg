import org.edisonwj.draw3d.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class LinComb3DExample3 extends Application {

    String title = "Vector example";

    void drawingCommands () 
    {
	// Type all drawing commands in here.

	// The three vectors u,v,w:
	d3.setDrawColor (Color.BLUE);
	d3.drawVector (1,3,1);
	d3.drawVector (4,1,0);
	d3.drawVector (9,5,1);

	// Example of using drawArrow(). The vector z:
	d3.setDrawColor (Color.BLACK);
	d3.drawArrow (0,0,0, 1,7,8);
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
