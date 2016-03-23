import edu.gwu.lintool.*;
import java.lang.Math;

public class TimLinTool extends LinToolEmpty {

	// Vector operations:
    public double[] add (double[] u, double[] v)
	{
		if(u == null || v == null) return null;
		if(u.length != v.length) return null;
		
		double[] sum = new double[u.length];
		for(int i = 0; i<u.length; i++) 
			sum[i] = u[i] + v[i];
		return sum;
	}
    public double norm (double[] v)
	{
		if(v == null) return -1;
		
		double norm = 0;
		for(int i = 0; i<v.length; i++)
			norm += v[i]*v[i];
		norm = Math.sqrt(norm);
		return norm;
	}
    public double dotProduct (double[] u, double[] v)  
	{
		if(u == null || v == null) return -1;
		if(u.length != v.length) return -1;
		
		double dp = 0;
		for(int i = 0; i<u.length; i++)
			dp += u[i]*v[i];
		return dp;
	}	
	public double[] scalarProduct (double alpha, double[] v)
	{
		if(v==null) return null;
		
		double[] sp = new double[v.length];
		for(int i = 0; i<v.length; i++)
			sp[i] = alpha * v[i];
		return sp;
	}
    public boolean approxEquals (double[] u, double[] v, double errorTolerance)
	{
		if(u == null || v == null) return false;
		
		for(int i = 0; i<u.length; i++)
		{
			double diff = u[i] - v[i];
			if(Math.abs(diff) > errorTolerance) return false;
		}
		return true;			
	}
    public double cosine (double[] u, double[] v)
	{
		if(u == null || v == null) return -1;
		
		return dotProduct(u, v) / norm(u) * norm(v);
	}

    // Matrix operations:
    public double[][] add (double[][] A, double[][] B)
	{
		if(A == null || B == null) return null;
		if(A.length != B.length || A[0].length != B[0].length) return null;
		
		double[][] sum = new double[A.length][A[0].length];
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[0].length; j++)
			{
				sum[i][j] = A[i][j] + B[i][j];
			}
		}
		return sum;
	}
	
    public double[][] scalarProduct (double alpha, double[][] A)
	{
		if(A == null) return null;

		double[][] sp = new double[A.length][A[0].length];
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[0].length; j++)
			{
				sp[i][j] = A[i][j] * alpha;
			}
		}
		return sp;
	}
    public double[][] mult (double[][] A, double[][] B)
	{
		if(A == null || B == null) return null;
		if(A[0].length != B.length) return null;
		
		double[][] C = new double[A.length][B[0].length];
    
		for(int i=0; i < C.length; i++){ 
			for(int j=0; j < C[0].length; j++){
				for(int k=0; k < A[0].length; k++){
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}
    public double[] matrixVectorMult (double[][] A, double[] v)
	{
		if(A == null || v == null) return null;
		if(A[0].length != v.length) return null;
		
		double[] y = new double[A.length];
		
		for(int i = 0; i<y.length; i++){
			for(int j = 0; j<A[0].length; j++){
				y[i] += A[i][j] * v[j];
			}
		}
		return y;
	}
    public double[] vectorMatrixMult (double[] v, double[][] A)
	{
		if(A == null || v == null) return null;
		if(A.length != v.length) return null;
		
		double[] y = new double[A[0].length];
		
		for(int i = 0; i<y.length; i++){
			for(int j = 0; j<A.length; j++){
				y[i] += A[j][i] * v[j];
			}
		}
		return y;
	}
	
    public double[][] vectorLeftMult (double[] u, double[] v)
	{
		if(u == null || v == null) return null;
		
		double[][] A = new double[u.length][v.length];
		
		for(int i = 0; i<u.length; i++)
		{
			for(int j = 0; j<v.length; j++)
			{
				A[i][j] = u[i] * v[j];
			}
		}
		return A;
	}
    public double[][] transpose (double[][] A)
	{
		if(A == null) return null;
		
		double[][] transposed = new double[A[0].length][A.length];
		
		for(int i = 0; i<A[0].length; i++)
		{
			for(int j = 0; j<A.length; j++)
			{
				transposed[i][j] = A[j][i];
			}
		}
		return transposed;
	}
    public double frobeniusNorm (double[][] A)
	{
		if(A == null) return -1;
		
		double norm = 0;
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[0].length; j++)
			{
				norm += A[i][j] * A[i][j];
			}
		}
		norm = Math.sqrt(norm);
		return norm;
	}
	
    public boolean approxEquals (double[][] A, double[][] B, double errorTolerance)
	{
		if(A == null || B == null) return false;
		if(A.length != B.length || A[0].length != B[0].length) return false;
		
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[0].length; j++)
			{
				double diff = A[i][j] - B[i][j];
				if(Math.abs(diff) > errorTolerance) return false;
			}
		}
		return true;
	}
	
    public double[] getColumnAsVector (double[][] A, int col)
	{
		if(A == null) return null;
		if(col >= A[0].length) return null;
		
		double[] v = new double[A[0].length];
		for(int i = 0; i<v.length; i++)
			v[i] = A[i][col];
		return v;
	}
    public double[] getRowAsVector (double[][] A, int row)
	{
		if(A == null) return null;
		if(row >= A.length) return null;
		
		double[] v = new double[A.length];
		for(int i = 0; i<v.length; i++)
			v[i] = A[row][i];
		return v;
	}

	// Solving equations:
    public LinResult computeREF (double[][] A, double[] b)
	{
		return doEverything(A, b);
	}
    public LinResult computeRREF (double[][] A, double[] b)
	{
		return doEverything(A, b);
	}
    public LinResult solveFromREF (double[][] A, double[] b)
	{
		return doEverything(A, b);
	}
    public LinResult solveFromRREF (double[][] A, double[] b)
	{
		return doEverything(A, b);
	}
    public LinResult inverse (double[][] A)
	{
		double[] b = new double[A.length];
		return doEverything(A, b);
	}
	public boolean existsAllZeroRows(double[][] A)
	{
		for(int i = 0; i<A.length; i++)
		{
			int j;
			for(j = 0; j<A[0].length; j++)
			{
				if(A[i][j] != 0) break;
			}
			if(j==A[0].length) return true;
		}
		return false;
	}
	public boolean existsContradictionRow(double[][] A)
	{
		for(int i = 0; i<A.length; i++)
		{
			int j;
			for(j = 0; j<A[0].length; j++)
			{
				if(A[i][j] != 0) break;
			}
			if(j==(A[0].length-1)) return true;
		}
		return false;
	}
	public int[] findPivot(double[][] Ag, int k)
	{
		for(int i = k; i<Ag.length; i++)
		{
			for(int j = 0; j<Ag[0].length; j++)
			{
				if(Ag[i][j] != 0){
					int[] res = {i, j};
					return res;
				}
			}
		}
		int[] res = {-1, -1};
		return res;
	}
	public void multAndSub(double[][] A, int r1, int r2, double alpha)
	{
		for(int i = 0; i<A[0].length; i++)
		{
			A[r2][i] -= A[r1][i]*alpha;
		}
	}
	
	public void swapRow(double[][] A, int r1, int r2)
	{
		for(int i = 0; i<A.length; i++)
		{
			double temp = A[r1][i];
			A[r1][i] = A[r2][i];
			A[r2][i] = temp;
		}
	}
	public int[] findNextPivot(double[][] A, int r, int c)
	{
		for(int i = c; i<A.length; i++)
		{
			for(int j = r; j<A[0].length; j++)
			{
				if(A[j][i] != 0)
				{
					int[] res = {j, i};
					return res;
				}
			}
		}
		int[] res = {-1, -1};
		return res;
	}
	public double[][] getIdentityMatrix(int n)
	{
		double[][] result = new double[n][n];
		for(int i = 0; i<n; i++)
			result[i][i] = 1;
		return result;
	}
	public double[][] computeAugmentedMatrix(double[][] A, double[] b)
	{
		double[][] Ag = new double[A.length][A[0].length + 1];
		
		for(int i = 0; i<A.length; i++)
		{
			for(int j = 0; j<A[0].length; j++)
			{
				Ag[i][j] = A[i][j];
			}
		}
		
		for(int k = 0; k<b.length; k++)
			Ag[k][A[0].length] = b[k];
		
		return Ag;
	}
	public LinResult doEverything(double[][] A, double[] b)
	{
		LinResult L = new LinResult();
		double[][] Ag = computeAugmentedMatrix(A, b);
		double[][] Ainv = getIdentityMatrix(A[0].length);
		
		int currentRow = 0, currentCol = 0;
		
		int[] rc = findNextPivot(A, currentRow, currentCol);
		while(rc[0] != -1)
		{
			if(rc[0] > currentRow) swapRow(A, rc[0], currentRow);
			
			currentCol = rc[1];
			
			L.isPivotColumn[currentCol] = true;
			L.rank++;
			
			double alpha = Ag[currentRow][currentCol];
			for(int j = 0; j<Ag[0].length; j++)
			{
				Ag[currentRow][j] /= alpha;
				if(j != Ag.length - 1) Ainv[currentRow][j] /= alpha;
			}
			
			for(int r = currentRow + 1; r<Ag.length; r++)
			{
				multAndSub(Ag, currentRow, r, A[r][currentCol]);
				multAndSub(Ainv, currentRow, r, A[r][currentCol]);
			}
			currentRow++;
			currentCol++;
			
			rc = findNextPivot(Ag, currentRow, currentCol);
		}
		
		for(int k = 0; k<L.rank; k++)
		{
			int[] rcp = findPivot(Ag, k);
			for(int r = rcp[0]-1; r>=0; r++)
			{
				multAndSub(Ag, rcp[0], r, A[r][rcp[1]]);
				multAndSub(Ainv, rcp[0], r, A[r][rcp[1]]);
			}
		}
		
		if(existsContradictionRow(Ag))
		{
			L.solutionExists = false;
			L.x = null;
			L.Ainv = null;
		}
		else if(existsAllZeroRows(Ag)){
			L.solutionExists = true;
			L.isUniqueSolution = false;
			L.Ainv = null;
		} 
		else
		{
			L.solutionExists = true;
			L.isUniqueSolution = true;
			L.Ainv = Ainv;
			L.x = new double[Ag.length];
			for(int i = 0; i<L.x.length; i++)
				L.x[i] = Ag[i][Ag[0].length-1];
		}
		return L;
	}

    // Complex vector operations:
    public ComplexNumber[] add (ComplexNumber[] u, ComplexNumber[] v)
	{
		if(u==null || v==null) return null;
		if(u.length != v.length) return null;
		
		ComplexNumber[] result = new ComplexNumber[u.length];
		
		for(int i=0; i<u.length; i++)
			result[i] = u[i].add(v[i]);
		
		return result;
	}
    public double norm (ComplexNumber[] v)
	{
		if(v==null) return -1;
		
		ComplexNumber result = v[0].mult(v[0]);
		for(int i = 1; i<v.length; i++)
			result = result.add(v[i].mult(v[i]));
		
		return Math.sqrt(result.magnitude());
	}
    public ComplexNumber[] scalarProduct (ComplexNumber alpha, ComplexNumber[] v)
	{
		if(v==null) return null;
		
		ComplexNumber[] result = new ComplexNumber[v.length];
		
		for(int i = 0;i<v.length;i++)
			result[i] = alpha.mult(v[i]);
		
		return result;		
	}
    public ComplexNumber dotProduct (ComplexNumber[] u, ComplexNumber[] v)
	{
		if(u==null || v==null) return null;
		if(u.length != v.length) return null;
		
		ComplexNumber result = u[0].mult(v[0].conjugate());
		
		for(int i=1;i<u.length;i++)
			result = result.add(u[i].mult(v[i].conjugate()));
		
		return result;
	}
    // Note: dot product is the Hermitian dot product:
}