// Instructions:
// (1) Compile and execute.
// (2) Un-comment the F3 code. Try x[0] vs x[1], x[0] vs x[2], x[2] vs x[3]

import java.util.*;

public class DataExample {

    public static void main (String[] argv)
    {
	// Get data.
	int numPoints = 50;
	ArrayList<DataPoint> dataPoints = DataSource.getData (1, numPoints);

	// Plot each dimension in isolation.
	Function F0 = new Function ("first-dim vs i");
	Function F1 = new Function ("second vs i");
	Function F2 = new Function ("third vs i");

	Function F3 = new Function ("first-dim vs second-dim");

	for (int i=0; i<dataPoints.size(); i++) {
	    DataPoint p = dataPoints.get (i);
	    F0.add (i, p.x[0]);
	    F1.add (i, p.x[1]);
	    F2.add (i, p.x[2]);

	    F3.add (p.x[0], p.x[1]);
	}

	//Function.show (F0, F1, F2);

	Function.show (F0, F1, F2, F3);
    }

}
