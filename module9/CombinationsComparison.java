// Instructions:
// Copy over your code from CombinationsRecursive into 
// methods numCombinations() and numCombinationsRecursive()

public class CombinationsComparison {

    static int numCalls = 0;
    static int numCallsRecursive = 0;

    public static void main (String[] argv)
    {
	int n = 10;
	for (int k=0; k<=n; k++) {
	    int p = numCombinations (n,k);
	    int q = numCombinationsRecursive (n,k);
	    System.out.println ("n=" + n + " k=" + k + " p=" + p + " q=" + q);
	}
	System.out.println ("numCalls=" + numCalls + " numCallsRecursive=" + numCallsRecursive);
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
