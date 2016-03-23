// Instructions:
// (1) Compile and execute to see the points.
// (2) Un-comment the section for computing the least squares solution,
//     write the matrix operations, then compile/execute.


import edu.gwu.lintool.*;

public class Ellipse {

    public static void main (String[] argv)
    {
	// The data:
	int n = 12;
	double[] x = {0.5, 1.0, 1.5, 2.5, 3, 4, 
			   5, 5.5, 6, 7, 7.5, 8};
	double[] y = {4.3, 5.2, 5.538, 0.065, 5.8, -0.223,
			   5.83, 0.205, 5.404, 1.135, 4.312, 3.05};

	// Display the points.
	DrawTool.display ();
	DrawTool.setXYRange (0,10, 0,10);
	for (int i=0; i<n; i++) {
	    DrawTool.drawPoint (x[i],y[i]);
	}

	// Make the matrix from the data
	double[][] H = makeEllipse_H (x,y);

	// Make the RHS vector:
	double[] w = makeEllipse_w (x,y);

	// We wrote code earlier using "A" as the matrix. To reuse
	// that code without changing the code, let's equate A with H
	// even though we used "A" as the coefficient.
	double[][] A = H;

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
	// Step 1: first compute AT * w
	double[] ATb = 
	// Step 2: multiply the result on the left by ATA^-1
	double[] xhat = 

	// Now for the tricky part: converting the solution back
	// into the true ellipse parameters: h,k,a,b
	double e2 = xhat[0];                  // e^2 = A
	double h = xhat[1] / (-2*e2);         // h = B/-2e^2
	double k = xhat[2] / (-2.0);          // k = C/-2
	double a2 = (e2*h*h + k*k - xhat[3]) / e2;  
        // Above: a^2 = (e^2h^2 + k^2 - D)/e^2

	// Finally, a and b:
	double a = Math.sqrt (a2);
	double b2 = e2 * a2;
	double b = Math.sqrt (b2);
	System.out.println ("a=" + a + " b=" + b + " h=" + h + " k=" + k);
	
	drawEllipse (a,b, h,k);
	*/
    }


    static double[][] makeEllipse_H (double[] x, double[] y)
    {
	double[][] H = new double [x.length][4];
	for (int i=0; i<H.length; i++) {
	    H[i][0] = x[i]*x[i];
	    H[i][1] = x[i];
	    H[i][2] = y[i];
	    H[i][3] = 1;
	}
	return H;
    }

    static double[] makeEllipse_w (double[] x, double[] y)
    {
	double[] w = new double [x.length];
	for (int i=0; i<w.length; i++) {
	    w[i] = - y[i]*y[i];
	}	
	return w;
    }

    static void drawEllipse (double a, double b, double h, double k)
    {
	DrawTool.setLineColor ("blue");
	double prevXUpper = h-a, prevYUpper = k;
	double prevXLower = h-a, prevYLower = k;
	double xLimit = h+a;
	int numIntervals = 100;
	double delta = (2*a) / numIntervals;
	for (double x=prevXUpper; x<=xLimit; x+=delta) {
	    double p = Math.sqrt ( Math.max(0, 1-((x-h)/a)*(x-h)/a) );
	    double y1 = k + b*p;
	    double y2 = k - b*p;
	    DrawTool.drawLine (prevXUpper, prevYUpper, x,y1);
	    DrawTool.drawLine (prevXLower, prevYLower, x,y2);
	    prevXUpper = x;  prevYUpper = y1;
	    prevXLower = x;  prevYLower = y2;
	}
    }


}
