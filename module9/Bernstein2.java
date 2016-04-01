// Instructions:
// Copy over your code for computing n-choose-k
// Implement the recursive version of computing Bernsteins.

import java.util.*;

public class Bernstein2 {

    static int numCalls = 0;
    static int numCallsRecursive = 0;

    public static void main (String[] argv)
    {
	int n = 5;

	// For variety ... we're swapping the loops. This time, the
	// outer loop is over t, and we do each berntein polynomial
	// for n,k inside. Nothing is being plotted, since we're only
	// comparing efficiency.

	int numIntervals = 100;
	double deltaT = 1.0 / numIntervals;
	for (double t=0; t<=1; t+=deltaT) {
	    for (int k=0; k<=n; k++) {
		double b = bernstein (n, k, t);
		double b2 = bernsteinRecursive (n, k, t);
		if (Math.abs(b-b2) > 0.0001) {
		    // Error.
		    System.out.println ("t=" + t + " n=" + n + " k=" + k + " b=" + b + " b2=" + b2);
		}
	    }
	}

	System.out.println ("numCalls=" + numCalls + " numCallsRecursive=" + numCallsRecursive);
    }

    public static double bernsteinRecursive (int n, int k, double t)
    {
	numCallsRecursive ++;
	// INSERT YOUR CODE HERE:
    }


    public static double bernstein (int n, int k, double t)
    {
	double b = numCombinations(n,k) * Math.pow (t, k) * Math.pow (1-t,n-k);
	return b;
    }

    static double numCombinations (int n, int k)
    {
	// INSERT YOUR CODE HERE:

    }

    static double factorial (int n)
    {
	numCalls ++;
	// INSERT YOUR CODE HERE:

    }

}
