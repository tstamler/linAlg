// Instructions:
// Try adding additional terms until k=13.

public class TrigPolyLinComb {

    public static void main (String[] argv)
    {
	Function Fsum = new Function ("sin-sum");
	for (double x=0; x<=1; x+=0.01) {
	    double y = Math.sin (2*Math.PI*x)
		+ 1.0/3.0 * Math.sin (2*Math.PI*3*x)
		+ 1.0/5.0 * Math.sin (2*Math.PI*5*x)
		+ 1.0/7.0 * Math.sin (2*Math.PI*7*x)
		+ 1.0/9.0 * Math.sin (2*Math.PI*9*x)
		+ 1.0/11.0 * Math.sin (2*Math.PI*11*x);

            // INSERT MORE HARMONICS TO ABOVE STATEMENT.

	    Fsum.add (x, y);
	}
	Fsum.show ();
    }

}
