// Instructions:
// Copy over your code from CombinationsComparison into 
// methods numCombinations() and numCombinationsRecursive()
// Then implement the second recursive approach in numCombinationsRecursive2()
// and the iterative approach in numCombinationsIterative()

public class CombinationsComparison2 {

    static int numCalls = 0;
    static int numCallsRecursive = 0;
    static int numCallsRecursive2 = 0;
    static int numIterations = 0;

    public static void main (String[] argv)
    {
	// Try n=5, n=10, n=20.
	int n = 5;
	for (int k=0; k<=n; k++) {
	    int p = numCombinations (n,k);
	    int q = numCombinationsRecursive (n,k);
	    double r = numCombinationsRecursive2 (n,k);
	    double s = numCombinationsIterative (n,k);
	    System.out.println ("n=" + n + " k=" + k + " p=" + p + " q=" + q + " r=" + r + " s=" + s);
	}
	System.out.println ("numCalls=" + numCalls + " numCallsRecursive=" + numCallsRecursive + " numCallsRecursive2=" + numCallsRecursive2 + " numIterations=" + numIterations);
    }

    static double numCombinationsIterative (int n, int k)
    {
	// INSERT YOUR CODE HERE. In the loop, add the line numIterations++;

    }

    static double numCombinationsRecursive2 (int n, int k)
    {
	numCallsRecursive2 ++;
	// INSERT YOUR CODE HERE:

    }

    static int numCombinationsRecursive (int n, int k)
    {
	numCallsRecursive ++;
	// INSERT YOUR CODE HERE:

    }

    static int numCombinations (int n, int k)
    {
	// INSERT YOUR CODE HERE:

    }

    static int factorial (int n)
    {
	numCalls ++;
	// INSERT YOUR CODE HERE:

    }

}
