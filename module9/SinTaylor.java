// Instructions:
// Write code for implementing factorial below.

public class SinTaylor {

    public static void main (String[] argv)
    {
		for (int k=1; k<=13; k+=2) {
			double alpha = 1.0 / factorial (k);
			System.out.println ("alpha (without the sign): " + alpha);
		}
    }

    static double factorial (int k)
    {
		if(k == 0 || k == 1) return 1;
		else return k*factorial(k-1);
    }

}
