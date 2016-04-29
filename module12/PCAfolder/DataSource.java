// DataSource.java
//
// Author: Rahul Simha
// Spring 2016
//
// To generate different data sets for demonstrate PCA and kernel-PCA.
// See below for description of data sets.

import java.util.*;

public class DataSource {

    static ArrayList<DataPoint> getData (int whichSet, int numPoints)
    {
	if (whichSet == 1) {
	    return getData1 (numPoints);
	}
	else if (whichSet == 2) {
	    return getData2 (numPoints);
	}
	else if (whichSet == 3) {
	    return getData3 (numPoints);
	}
	else if (whichSet == 4) {
	    return getData4 (numPoints);
	}
	else if (whichSet == 5) {
	    return getData5 (numPoints);
	}
	else if (whichSet == 6) {
	    return getData6 (numPoints);
	}
	else {
	    System.out.println ("No such data set: " + whichSet);
	    System.exit (0);
	    return null;
	}
    }

    /////////////////////////////////////////////////////////////////
    // Data set 1
    // 
    // This data set produces two sine waves in the (y,z) plane,
    // one shifted from the other, with a little noise. 
    // The view is offset with a camera at (2,10,2).

    // Eye coords: where the eye is in 3D.
    static double xE, yE, zE;

    // The combined transform.
    static double[][] Trans;

    static ArrayList<DataPoint> getData1 (int numPoints)
    {
	Random rand = new Random (123);

	// Compute first transform. This will be a 4D affine.
	xE = 2;  yE = 10;  zE = 2;
	computeTransform ();
	double[][] T = Trans;

	// sin(2*pi*x) has range [0,1]:
	double delX = (1.0 - 0) / numPoints;


	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();

	for (int n=0; n<numPoints; n++) {

	    // The sine wave is in the (z,y) plane.
	    double noise = 0.2 * rand.nextDouble();
	    double x = 0;
	    double z = n * delX;
	    double y = Math.sin (2*Math.PI*z) + noise;

	    int clusterNum = rand.nextInt (2);
	    if (clusterNum == 1) {
		// Second cluster is shifted.
		double theta = Math.PI/4;
		y = Math.sin (2*Math.PI*z + theta) + noise;
	    }

	    // Set up the view transform.
	    double[] u = new double [4];
	    u[0] = x;  u[1] = y;  u[2] = z;  u[3] = 1;  // Affine
	    double[] v = MatrixTool.matrixVectorMult (T, u);

	    double[] w = new double [3];
	    w[0] = v[0];  w[1] = v[1];  w[2] = v[2];
	    // Shift up so that it does not clash with the label of z-axis.
	    w[2] = w[2] + 10;

	    // Add to our set.
	    DataPoint c = new DataPoint (w, clusterNum);
	    points.add (c);
	}

	return points;
    }

    static void computeTransform ()
    {
	// Polar:
	double rho = Math.sqrt (xE*xE + yE*yE + zE*zE);
	double theta = Math.atan2 (yE, xE);
	double phi = Math.acos (zE/rho);

	// Step 1. Write the affine matrix that reverse-moves the origin from 
	// (0,0,0) to (xE,yE,zE), i.e., undoes that:
	double[][] T_E = {
	    {1, 0, 0, -xE},
	    {0, 1, 0, -yE},
	    {0, 0, 1, -zE},
	    {0, 0, 0, 1}
	};

	// Step 2: the matrix for a rotation around the z-axis by
	// (90-theta) = (pi/2-theta)
	double rotAngle = (Math.PI/2 - theta);
	double b11 = Math.cos (rotAngle);
	double b12 = -Math.sin (rotAngle);
	double b21 = Math.sin (rotAngle);
	double b22 = Math.cos (rotAngle);
	double[][] R_z1 = {
	    {b11, b12, 0, 0},
	    {b21, b22, 0, 0},
	    {0,   0,   1, 0},
	    {0,   0,   0, 1}
	};

	// Step 3: the matrix for a rotation around the z-axis by 180.
	rotAngle = Math.PI;
	b11 = Math.cos (rotAngle);
	b12 = -Math.sin (rotAngle);
	b21 = Math.sin (rotAngle);
	b22 = Math.cos (rotAngle);
	double[][] R_z2 = {
	    {b11, b12, 0, 0},
	    {b21, b22, 0, 0},
	    {0,   0,   1, 0},
	    {0,   0,   0, 1}
	};

	// Step 4: rotation about x by -90
	rotAngle = -Math.PI/2;
	double c22 = Math.cos (rotAngle);
	double c23 = -Math.sin (rotAngle);
	double c32 = Math.sin (rotAngle);
	double c33 = Math.cos (rotAngle);
	double[][] R_x1 = {
	    {1,   0,   0, 0},
	    {0, c22, c23, 0},
	    {0, c32, c33, 0},
	    {0,   0,   0, 1}
	};

	// Step 5: rotation about x by 90-phi
	rotAngle = Math.PI/2 - phi;
	c22 = Math.cos (rotAngle);
	c23 = -Math.sin (rotAngle);
	c32 = Math.sin (rotAngle);
	c33 = Math.cos (rotAngle);
	double[][] R_x2 = {
	    {1,   0,   0, 0},
	    {0, c22, c23, 0},
	    {0, c32, c33, 0},
	    {0,   0,   0, 1}
	};

	// Now multiply in sequence: Rx2 * Rx1 * Rz2 * Rz1 * TE
	double[][] S1 = MatrixTool.matrixMult (R_z1, T_E);
	double[][] S2 = MatrixTool.matrixMult (R_z2, S1);
	double[][] S3 = MatrixTool.matrixMult (R_x1, S2);
	double[][] S4 = MatrixTool.matrixMult (R_x2, S3);

	Trans = S4;

    }


    /////////////////////////////////////////////////////////////////
    // Data set 2
    //
    // Single cluster around 0 that's diagonal

    static ArrayList<DataPoint> getData2 (int numPoints)
    {
	double stdDev1 = 4, stdDev2 = 1;
	int k = 2;
	Random rand = new Random (1234);
	double theta = Math.PI/3;
	double[][] A = {
	    {Math.cos(theta), -Math.sin(theta)},
	    {Math.sin(theta), Math.cos(theta)}
	};

	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();
	for (int n=0; n<numPoints; n++) {
	    double x = k + stdDev1 * rand.nextGaussian ();
	    double y = k + stdDev2 * rand.nextGaussian ();
	    double[] u = new double [2];
	    u[0] = x;  u[1] = y;
	    double[] v = MatrixTool.matrixVectorMult (A, u);
	    DataPoint c = new DataPoint(v, 1);
	    points.add (c);
	}	

	return points;
    }

    /////////////////////////////////////////////////////////////////
    // Data set 3
    //
    // 4 Gaussians centered at (k,k), (k,-k), (-k,k), (-k,-k)

    static ArrayList<DataPoint> getData3 (int numPoints)
    {
	double stdDev = 1;
	int k = 3;
	// 4 Gaussians centered at (k,k), (k,-k), (-k,k), (-k,-k)
	Random rand = new Random (1234);

	int clusterNum = 0;

	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();
	for (int n=0; n<numPoints; n++) {
	    double a = k,  b = k;
	    if (clusterNum == 1) {
		a = -k;  b = k;
	    }
	    else if (clusterNum == 2) {
		a = -k;  b = -k;
	    }
	    else if (clusterNum == 3) {
		a = k;  b = -k;
	    }
	    // Now generate a*k + randX, b*k + randY
	    double x = a + stdDev * rand.nextGaussian();
	    double y = b + stdDev * rand.nextGaussian();
	    double[] z = new double [2];
	    z[0] = x;  z[1] = y;
	    DataPoint c = new DataPoint(z, clusterNum);
	    points.add (c);
	    clusterNum = ((clusterNum+1) % 4);
	}
	return points;
    }

    /////////////////////////////////////////////////////////////////
    // Data set 4
    // 
    // Two rings with width. This is notoriously hard to cluster
    // with standard K-means. 

    static ArrayList<DataPoint> getData4 (int numPoints)
    {
	double radius1 = 3, radius2 = 7;
	double a = 1; // half-annulus or half ring width.
	Random rand = new Random (123);

	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();
	for (int n=0; n<numPoints; n++) {
	    // Pick one of the rings.
	    int clusterNum = rand.nextInt (2);
	    // Random angle.
	    double theta = 2.0 * Math.PI * rand.nextDouble ();
	    double r = radius1;
	    if (clusterNum == 1) {
		r = radius2;
	    }
	    // We now have one of the rings chosen. Add some noise.
	    double noise = -0.5 + rand.nextDouble ();
	    r = r + noise;
	    double x = r * Math.cos (theta);
	    double y = r * Math.sin (theta);
	    double[] z = new double [2];
	    z[0] = x;  z[1] = y;
	    DataPoint c = new DataPoint(z, clusterNum);
	    points.add (c);
	}

	return points;
    }

    /////////////////////////////////////////////////////////////////
    // Data set 5
    // 
    // Four quadrants, but serially numbered. This is for testing
    // clustering algorithms.

    static ArrayList<DataPoint> getData5 (int numPoints)
    {
	// Four quadrants, but serially numbered points.

	double stdDev = 1;
	int k = 5;
	// 4 Gaussians centered at (k,k), (k,-k), (-k,k), (-k,-k)

	Random rand = new Random (1234);
	//Random rand = new Random ();

	int pointsPerCluster = numPoints / 4;
	int numPointsDone = 0;
	int clusterNum = 0;

	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();
	for (int n=0; n<numPoints; n++) {
	    double a = k,  b = k;
	    if (numPointsDone == pointsPerCluster) {
		clusterNum ++;
		numPointsDone = 0;
	    }
	    if (clusterNum > 3) {
		break;
	    }
	    if (clusterNum == 1) {
		a = -k;  b = k;
	    }
	    else if (clusterNum == 2) {
		a = -k;  b = -k;
	    }
	    else if (clusterNum == 3) {
		a = k;  b = -k;
	    }
	    // Now generate a*k + randX, b*k + randY
	    double x = a + stdDev * rand.nextGaussian();
	    double y = b + stdDev * rand.nextGaussian();
	    double[] z = new double [2];
	    z[0] = x;  z[1] = y;
	    DataPoint c = new DataPoint(z, clusterNum);
	    points.add (c);
	    numPointsDone ++;
	    System.out.println (c);
	}
	return points;
    }


    /////////////////////////////////////////////////////////////////
    // Data set 6
    // 
    // Data around two lines. The idea was to see if PCA would 
    // further separate them, but the additional gap was not compelling.

    static ArrayList<DataPoint> getData6 (int numPoints)
    {
	// Two lines, two slopes.
	double m1=1, m2=4;
	double stdDev = 1;

	Random rand = new Random (123);

	ArrayList<DataPoint> points = new ArrayList<DataPoint> ();
	for (int n=0; n<numPoints; n++) {
	    int clusterNum = rand.nextInt (2);
	    double x = 0, m = 0;
	    if (clusterNum == 0) {
		m = m1;
		x = (10/m) * rand.nextDouble ();
	    }
	    else if (clusterNum == 1) {
		m = m2;
		x = (10/m) * rand.nextDouble ();
	    }
	    double y = m * x + rand.nextGaussian() * stdDev;
	    double[] z = new double [2];
	    z[0] = x;  z[1] = y;
	    DataPoint c = new DataPoint(z, clusterNum);
	    points.add (c);
	    //System.out.println ("x=" + x + " y=" + y + " c=" + clusterNum);
	}

	return points;
    }




}
