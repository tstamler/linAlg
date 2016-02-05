
public class ExploreSpan {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (-10,10, -10,10);
	DrawTool.drawMiddleAxes (true);

	// Two vectors:
	double[] u = {1, 4};
	double[] v = {3, 2};

	DrawTool.setArrowColor ("blue");
	DrawTool.drawVector (u);
	DrawTool.drawVector (v);

	// Range of alpha to explore:
	double alphaLow=-3, alphaHigh=3, alphaStep=0.01;
	// Range of beta:
	double betaLow=-3, betaHigh=3, betaStep=0.01;

	for (double alpha=alphaLow; alpha<=alphaHigh; alpha+=alphaStep) {
	    for (double beta=betaLow; beta<=betaHigh; beta+=betaStep) {
		double[] w = linComb (alpha, u, beta, v);
		DrawTool.drawLine (0,0, w[0], w[1]);
	    } 
	}
    }

    static double[] linComb (double alpha, double[] u, double beta, double[] v)
    {
	// INSERT YOUR CODE HERE:
	double[] w = new double [2];
	w[0] = alpha * u[0] + beta * v[0];
	w[1] = alpha * u[1] + beta * v[1];
	return w;
    }

}
