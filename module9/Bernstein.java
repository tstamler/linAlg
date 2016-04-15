// Instructions:
// Copy over your code for computing n-choose-k
// Try n=1, 2, 3, 4, 5

import java.util.*;

public class Bernstein {

    public static void main (String[] argv)
    {
	// Try different values of n:
	int n = 5;

	drawBernsteins (n);
    }

    static void drawBernsteins (int n)
    {
	//ArrayList<Function> bernsteins = new ArrayList<Function> ();
	Function[] bernsteins = new Function[n];
	for (int k=0; k<n; k++) {
	    Function F = bernsteinPoly (n, k);
	    bernsteins[k] = F;
	}
	Function.show (bernsteins);
    }

    static Function bernsteinPoly (int n, int k)
    {
	int numIntervals = 100;
	double deltaT = 1.0 / numIntervals;
	Function F = new Function ("n=" + n + " k=" + k);
	for (double t=0; t<=1; t+=deltaT) {
	    double b = bernstein (n, k, t);
	    F.add (t, b);
	}
	return F;
    }

    public static double bernstein (int n, int k, double t)
    {
	double b = numCombinations(n,k) * Math.pow (t, k) * Math.pow (1-t,n-k);
	return b;
    }

    static double numCombinations (int n, int k)
    {
		return factorial(n) / (factorial(k) * factorial(n - k));
    }

    static double factorial (int n)
    {
		if(n == 0 || n == 1) return 1;
		else return n*factorial(n-1);
    }

}
