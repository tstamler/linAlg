
import java.io.*;
import java.util.*;
import edu.gwu.lintool.*;

public class LSAStats {

    static int M, N;

    public static void setSizes (int dataM, int dataN)
    {
	M = dataM;
	N = dataN;
    }

    public static void printCovariance (double[][] cov)
    {
	for (int i=0; i<cov.length; i++) {
	    for (int j=0; j<cov[0].length; j++) {
		System.out.printf ("  %6.3f", cov[i][j]);
	    }
	    System.out.println ();
	}
    }


    ///////////////////////////////////////////////////////////////
    // Statistics

    static double[][] normalizeData (double[][] data)
    {
	int m = data.length;  int n = data[0].length;
	double[] avg = computeAverageVector (data);
	double[][] X = new double [m][n];
	for (int i=0; i<m; i++) {
	    for (int j=0; j<n; j++) {
		X[i][j] = data[i][j] - avg[i];
	    }
	}
	return X;
    }


    static double[] computeAverageVector (double[][] data)
    {
	int m = data.length;  int n = data[0].length;

	double[] avg = new double [m];

	for (int i=0; i<m; i++) {
	    double sum = 0;
	    for (int j=0; j<n; j++) {
		// Sum across the M columns.
		sum += data[i][j];
	    }
	    avg[i] = sum / n;
	}

	return avg;
    }


    static double[][] covariance (double[][] X)
    {
	return spearmanCovariance (X);
    }

    static double[][] spearmanCovariance (double[][] X)
    {
	int K = X[0].length;     // # cols.

	double[][] cov = new double [K][K];
	for (int i=0; i<K; i++) {
	    for (int j=0; j<K; j++) {
		cov[i][j] = spearman (X, i, j);
	    }
	}

	return cov;
    }


    static double spearman (double[][] X, int a, int b)
    {
	int n = X.length;
	double[] Xa = new double [n];
	double[] Xb = new double [n];
	for (int i=0; i<n; i++) {
	    Xa[i] = X[i][a];
	    Xb[i] = X[i][b];
	}
	double rho = spearman (Xa, Xb);
	return rho;
    }

    static double spearman (double[] a, double[] b)
    {
	if (a.length != b.length) {
	    System.out.println ("ERROR: spearman: not equal lengths");
	    System.exit (0);
	}

	int n = a.length;
	double[] rankA = computeRank (a);
	double[] rankB = computeRank (b);

	// Now compute correlation.
	double sumDiffSq = 0;
	for (int i=0; i<n; i++) {
	    double diff = rankA[i] - rankB[i];
	    sumDiffSq += (diff * diff);
	}	
	
	double rho = 1.0 - (6 * sumDiffSq) / (n * (n*n - 1));
	return rho;
    }

    static double[] computeRank (double[] x)
    {
	int n = x.length;
	double[] rank = new double [n];
	ValIndex[] data = new ValIndex [n];
	for (int i=0; i<n; i++) {
	    data[i] = new ValIndex (x[i], i);
	}
	Arrays.sort (data);
	for (int i=0; i<n; i++) {
	    data[i].rank = i;
	}
	spearmanAvg (data); 

	for (int i=0; i<n; i++) {
	    int index = data[i].index;
	    rank[index] = data[i].rank;
	}

	return rank;
    }

    static void spearmanAvg (ValIndex[] data)
    {
	int n = data.length;
	int i = 0;
	while (i < n) {
	    int j = i+1;
	    while ( (j < n) && (data[i].value == data[j].value) ) {
		j++;
	    }
	    // Now i,j is the range of equal values.
	    if (j > i+1) {
		averageRank (data, i, j-1);
	    }
	    // To the next range.
	    i = j;
	}
    }

    static void averageRank (ValIndex[] data, int i, int j)
    {
	// Set the new rank to be the average in the range i .. j.
	double s = 0;
	for (int k=i; k<=j; k++) {
	    s += data[k].rank;
	}
	double avg = s / (j-i+1);
	for (int k=i; k<=j; k++) {
	    data[k].rank = avg;
	}
    }


}



// For Spearman correlations

class ValIndex implements Comparable {
    double value;
    int index;
    double rank;
    public ValIndex (double v, int i) {value = v; index = i;}
    public int compareTo (Object obj)
    {
	ValIndex v = (ValIndex) obj;
	if (value < v.value) {
	    return -1;
	}
	else if (value > v.value) {
	    return 1;
	}
	else {
	    return 0;
	}
    }

    public boolean equals (Object obj)
    {
	ValIndex v = (ValIndex) obj;
	return (value == v.value);
    }
}
