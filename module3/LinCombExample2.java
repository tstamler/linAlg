// Instructions:
// 1. Write code to implement vector addition and scalar multiplication
// 2. Then compile and execute.

public class LinCombExample2 {

    public static void main (String[] argv) 
    {
		DrawTool.display ();
		DrawTool.setXYRange (-10,10, -10,10);
		DrawTool.drawMiddleAxes (true);

		double[] u = {1,4};
		double[] v = {3,2};
		double alpha = 1.5,  beta = 2;
		double[] z = add (scalarMult(alpha, u), scalarMult(beta,v));
		DrawTool.setArrowColor ("blue");
		DrawTool.drawVector (z);
    }

    static double[] add (double[] u, double[] v)
    {
		double[] z = new double[u.length];
		for(int i = 0; i<u.length; i++)
		{
			z[i] = u[i] + v[i];
		}
		return z;
    }

    static double[] scalarMult (double alpha, double[] v)
    {
		double[] z = new double[v.length];
		for(int i = 0; i<v.length; i++)
		{
			z[i] = alpha*v[i];
		}
		return z;

    }

}
