// Instructions:
// Write code in factorial() and then numCombinations()
// Calculate by hand numCombinations(5,k) for k=0,1,..,5.
// Compare with the computation.

public class Combinations {

    public static void main (String[] argv)
    {
	// Try k=0,1,2,3,4,5.
	int k = 3;
	int r = numCombinations (5,k);
	System.out.println (r);
    }

    static int numCombinations (int n, int k)
    {
		return factorial(n) / (factorial(k) * factorial(n - k));
    }

    static int factorial (int n)
    {
		if(k == 0 || k == 1) return 1;
		else return k*factorial(k-1);

    }

}
