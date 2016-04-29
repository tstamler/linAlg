// Each DataPoint instance stores one m-dimensional point (vector)

public class DataPoint {

    double[] x;                 // x.length is the dimension.
    int trueCluster = -1;       // If the data is intended to test clustering.
    int cluster = -1;           // A clustering algorithm would use this.

    public DataPoint (double[] x, int trueCluster)
    {
	this.x = x;
	this.trueCluster = trueCluster;
    }

    public DataPoint (double[] x)
    {
	this (x, -1);
    }

    public String toString ()
    {
	String s = "Data Point: ";
	for (int i=0; i<x.length; i++) {
	    s += String.format (" %6.3f",x[i]);
	}
	return s;
    }

}
