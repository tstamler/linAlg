
import java.util.*;

public class DataViewer {

    static void drawData (ArrayList<DataPoint> points)
    {
	for (DataPoint p: points) {
	    DrawTool.setPointColor ( getColor(p.trueCluster) );
	    DrawTool.drawPoint (p.x[0], p.x[1]);
	}
    }

    static void drawResults (ArrayList<DataPoint> points)
    {
	for (DataPoint p: points) {
	    DrawTool.setPointColor ( getColor(p.trueCluster) );
	    //System.out.println ("result: p.cluster=" + p.cluster);
	    DrawTool.drawPoint (p.x[0], p.x[1]);
	}
    }


    static String getColor (int k)
    {
	String c = "red";
	if (k == 1) c = "blue";
	else if (k == 2) c = "green";
	else if (k == 3) c = "magenta";
	return c;
    }


}
