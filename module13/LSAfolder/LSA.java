//
// LSA.java
// Author: Rahul Simha, 2015
//
// Demonstrates LSA (Latent Semantic Analysis)
// To execute, choose either the wikinews dataset or the example.
//
// The output has two types of correlations between text files.
// (1) Correlations of the word-doc vectors, normalized but without LSA.
// (2) The same with LSA.
//
// Note: we use spearman correlation because the word-doc vectors
// are binary (and thus, the raw numbers don't indicate anything).

import java.io.*;
import java.util.*;
import edu.gwu.lintool.*;


public class LSA {

    // Stop words are words like "an, the" etc
    static String stopWordFile = "StopWords.txt";

    // Test data: N=4
    //static int N=4;
    //static String dataDir = "exampleData";

    // Wikinews: N=7
    static int N = 7;
    static String dataDir = "wikinews";

    // M = length of each vector. This is calculated from the data. All
    // files are scanned and all unique words minus stopWords are used.
    static int M;

    // Each column is one document.
    static double[][] data;

    // Data structures for words.
    static TreeSet<String> stopWords = new TreeSet<String> ();
    static TreeSet<String>[] wordsInFile = new TreeSet [N];
    static TreeSet<String> allWords = new TreeSet<String> ();
    static HashMap<String, Integer> wordToNum = new HashMap<String, Integer> ();
    static HashMap<Integer, String> numToWord = new HashMap<Integer, String> ();


    public static void main (String[] argv)
    {
	// We'll print 2 types of correlations amongst columns (documents).
	// (1) Direct correlation from the data vector X.
	// (2) Correlation in a transformed coords space, through LSA.

	// 1. Process text samples once to get size and average.
	data = textToVectors ();
	printDocumentVectors (data);

	LSAStats.setSizes (M, N);

	// 3. Make normalized data matrix. 
	//    Note: X is M x N
	double[][] X = LSAStats.normalizeData (data);
	LinUtil.print ("X", X);

	// Part (1): standard covariance 
	double[][] covX = LSAStats.covariance (X);
	System.out.println ("Covariance matrix without SVD");
	LSAStats.printCovariance (covX);

	// Get coordinates using U as a basis.
	double[][] Y = latentSemanticCoords (X);

	// Part (2): Covariance in transformed coords.
	double[][] covY = LSAStats.covariance (Y);
	System.out.println ("Covariance matrix in new coords:");
	LSAStats.printCovariance (covY);
    }


    static double[][] latentSemanticCoords (double[][] X)
    {
	// Get SVD of the centered document vectors (columns of X)
	LinResult L = LinToolLibrary.computeSVDShortForm (X);

	int R = L.rank;

	// U is M x R
	double[][] U = L.U;

	// Sigma is R x R
	double[][] S = L.Sigma;

	// Reduce the Sigma matrix: set sigma_k = 0 for k >= p
	int p = 2;
	reducedSingular (S, p);

	// V is N x R
	double[][] V = L.V;

	// V_T is R x N
	double[][] V_T = MatrixTool.transpose (V);

	LinUtil.print ("U", U);
	LinUtil.print ("S", S);
	LinUtil.print ("VT", V_T);

	double[][] Y = MatrixTool.matrixMult (S, V_T);
	// Note: Y has the new coords since Y = U^-1 X = U_T * X
	// which equals S*V_T (because X = U * S * V_T by SVD).

	LinUtil.print ("Y", Y);

	return Y;
    }


    // This sets sigma_k=0 for all k >= p. We do this to use the sigma's
    // to sharpen significance.

    static void reducedSingular (double[][] S, int p)
    {
	for (int i=p; i<S.length; i++) {
	    S[i][i] = 0;
	}
    }


    static void printDocumentVectors (double[][] data)
    {
	for (int i=0; i<data.length; i++) {
	    for (int j=0; j<data[0].length; j++) {
		System.out.printf (" %3f", data[i][j]);
	    }
	    String word = numToWord.get (i);
	    System.out.println ("  " + word);
	}
    }

    //
    // No need to read any further
    ///////////////////////////////////////////////////////////////
    // Utility methods for handling text.

    static int numCommonWords (int m, int n)
    {
	int numCommon = 0;
	for (int j=0; j<M; j++) {
	    if ((data[j][m] > 0) && (data[j][n] > 0)) {
		numCommon ++;
	    }
	}
	return numCommon;
    }



    static int commonCount (double[] u, double[] v) 
    {
	int count = 0;
	for (int i=0; i<u.length; i++) {
	    if ((u[i] > 0) && (v[i] > 0)) {
		count ++;
	    }
	}
	return count;
    }


    static double[][] textToVectors ()
    {
	double[][] data = null;
	readStopWords ();
	//System.out.println ("Stop words read");

	// Now scan all files, and extract all unique non-stop words.
	readAllWords ();  
	// This is our vector length.
	M = allWords.size ();
	//System.out.println ("All words read: N=" + N);
	
	// Next, we create our mapping files.
	int wordCount = 0; 
	for (String s: allWords) {
	    wordToNum.put (s, wordCount);
	    numToWord.put (wordCount, s);
	    wordCount ++;
	}

	// Next, convert each to a vector.
	// Note: all vectors are already of length N.

	// Now place vectors in a N x M matrix.
	data = new double [M][N];
	for (int i=0; i<N; i++) {
	    double[] v = convertToWordVector (wordsInFile[i]);
	    for (int j=0; j<M; j++) {
		// Note the switch: there are M rows, N columns.
		data[j][i] = v[j];  
	    }
	}

	System.out.println ("Data matrix created");
	return data;
    }


    static double[] convertToWordVector (String text)
    {
	// First extract words.
	String[] words = text.split ("[^A-Za-z]+");
	// Build vector.
	double[] x = new double [M];
	for (int i=0; i<words.length; i++) {
	    if (wordToNum.containsKey (words[i])) {
		int index = wordToNum.get (words[i]);
		x[index] += 1;     // Frequency count.
	    }
	}
	return x;
    }

    static double[] convertToWordVector (TreeSet<String> words)
    {
	double[] x = new double [M];
	for (String s: words) {
	    if (wordToNum.containsKey (s)) {
		int index = wordToNum.get (s);
		x[index] += 1;     // Frequency count.
	    }
	}
	return x;
    }


    static void readAllWords ()
    {
	for (int i=0; i<N; i++) {
	    wordsInFile[i] = readWordsInFile (dataDir + "/" + "text" + i + ".txt");
	}

	// Now add all words that are in at least two files.
	for (int i=0; i<N; i++) {
	    for (String w: wordsInFile[i]) {
		// See if it exists elsewhere.
		for (int j=i+1; j<N; j++) {
		    if ( wordsInFile[j].contains(w) ) {
			//System.out.println ("readAllWords: w=" + w + " is in both i=" + i + " and j=" + j);
			allWords.add (w);
		    }
		}
	    }
	}
    }

    static TreeSet<String> readWordsInFile (String fileName)
    {
	TreeSet<String> wordsInFile = new TreeSet<String> ();
	try {
	    LineNumberReader lnr = new LineNumberReader (new FileReader (fileName));
	    String line = lnr.readLine ();
	    while (line != null) {
		String[] words = line.split ("[^A-Za-z]+");
		for (int i=0; i<words.length; i++) {
		    boolean isStopWord = stopWords.contains(words[i].toLowerCase());
		    //System.out.println ("i=" + i + "[" + words[i] + "], isStop=" + isStopWord);
		    if ((words[i].length() > 0) && (! isStopWord)) {
			wordsInFile.add (words[i]);
		    }
		}
		line = lnr.readLine ();
	    }
	}
	catch (IOException e) {
	    System.out.println (e);
	}
	return wordsInFile;
    }

    static void readStopWords ()
    {
	try {
	    LineNumberReader lnr = new LineNumberReader (new FileReader (stopWordFile));
	    String line = lnr.readLine ();
	    while (line != null) {
		stopWords.add (line);
		line = lnr.readLine ();
	    }
	}
	catch (IOException e) {
	    System.out.println (e);
	}
    }

}





