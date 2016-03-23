// Instructions:
// Add code to compute the coordinates and the projections

public class ProjectionExample2 {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (0,10, 0,10);
	DrawTool.drawMiddleAxes (true);

	double[] w = {4,3};
	DrawTool.drawVector (w);

	// The orthongonal basis v1, v2
	double[] v1 = {6,2};
	double[] v2 = {-1,3};
	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (v1);
	DrawTool.drawVector (v2);
	
	// INSERT YOUR CODE to compute the coordinates alpha1,alpha2
	// You should get alpha1=0.75, alpha2=0.5

	// Coordinates:
	double alpha1 = 
	double alpha2 = 
	// Projections:
	double[] y1 = 
	double[] y2 = 

	System.out.println ("alpha1=" + alpha1 + "  alpha2=" + alpha2);
	
	DrawTool.setArrowColor ("green");
	DrawTool.drawVector (y1);
	DrawTool.drawVector (y2);

	// Confirm:
	double v1Dotv2 = MatrixTool.dotProduct (v1,v2);
	System.out.println ("v1 dot v2 = " + v1Dotv2);

	double[] w2 = MatrixTool.add (y1,y2);
	System.out.println ("w2: (" + w2[0] + "," + w2[1] + ")");
    }

}
