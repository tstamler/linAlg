// Instructions:
// Write code to systematically search over alpha, beta values.


public class LinCombExample3 {

    public static void main (String[] argv) 
    {
		double[] u = {1,4};
		double[] v = {3,2};
		double[] z = {7.5,10};
		double[] temp = new double[2];
		double error = 0.00000000000001;
		
		for (double alpha=0; alpha<=10; alpha+=0.1) {
			for (double beta=0; beta<=10; beta+=0.1) {
				temp = add(scalarMult(alpha, u), scalarMult(beta, v));
				if((temp[0] < z[0] + error && temp[0] > z[0] - error) && (temp[1] < z[1] + error && temp[1] > z[1] - error)) 
				{
					System.out.println("Found z, alpha: " + alpha + ", beta: " + beta + ", error: " + error);
				}	
			}
		}

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
