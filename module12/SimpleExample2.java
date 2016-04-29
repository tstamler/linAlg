// Instructions:
// Write code in centerData() to center the data. Copy over your
// code for computing the mean.

public class SimpleExample2 {

    public static void main (String[] argv)
    {
	double[][] X = {
	    {1.0, 1.5, 0.5, 2.0, 1.5, 2.5},
	    {2.5, 3.5, 3.0, 4.0, 4.5, 0.5},
	    {-1.0, 0.5, 0, -0.5, 0, 1.0}
	};

	double[] mean = computeMean (X);
	MatrixTool.print (mean);
	
	// Subtract the mean from each column.
	X = centerData (X, mean);

	// Compute the mean again to check:
	mean = computeMean (X);
	MatrixTool.print (mean);

    }

    static double[] computeMean (double[][] X)
    {
	int m = X.length;
	int n = X[0].length;
	double[] mean = new double [m];

	// INSERT YOUR CODE HERE:
	double tmp_sum = 0D;
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			tmp_sum += X[i][j];
		}
		mean[i] = (tmp_sum/n);
		tmp_sum = 0D;
	}

	return mean;
    }

    static double[][] centerData (double[][] X, double[] mean)
    {
	int m = X.length;
	int n = X[0].length;

	double[][] Y = new double [m][n];

	// INSERT YOUR CODE HERE to put the centered data in Y


	return Y;
    }

}
