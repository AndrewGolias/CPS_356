import java.util.Random;

public class Matrix {
	int rows; // Define the number of rows
 	int cols; // Define the number of columns
	double values[][];

	Random rand = new Random();

/*First constructor: with row and column as the input that creates a matrix with the specified size and 
  * assign each elements with randomly generated number*/
Matrix(int r, int c) {
	rows = r;
	cols = c;
	values = new double[r][c];

	for(int y = 0; y < rows; y++) {
		for(int x = 0; x < cols; x++) {
    		values[y][x] = rand.nextDouble() * 10.0; //generating a double number between 0 and 10
		}
	}

}

/*Second constructor: with row, column, and a 2D array as the input. Similar to the first constructor
  * above, but instead of randomly generating, it assigns the elements with the third argument double 2D array.  */
Matrix(int r, int c, double v[][]) {

  //Implementation here...
	rows = r;
	cols = c;
	values = new double[r][c];	// automatically init to 0.0 2D arr - takes care of extra elements

	int r0 = Math.min(r, v.length);
	for(int i = 0; i < r0; i++) {

	int c0 = Math.min(c, v[i].length);
	for(int j = 0; j < c0; j++) {
		values[i][j] = v[i][j];
	}
}
}


/*Output the matrix to the console*/
void print() {
	for(int y = 0; y < rows; y++) {
		for(int x = 0; x < cols; x++) {
		    System.out.print(values[y][x] + ", ");
		}
		System.out.println();
	}
	System.out.println();
}




/*Matrix product without thread: let the current matrix times the input argument matrix m
  * and return the result matrix
  * Below the multiplication is already provided but you need to add a few lines of code to 
  * do the dimension check. If the input matrix does not satisfy the dimension requirement for
  * matrix product, you can print out a message and return a null matrix*/
Matrix multiplyBy(Matrix m) {
  //Implementation here...
	Matrix result = null; //You need to initialize the result Matrix properly
	if(this.cols != m.rows) {
	System.err.println("Matrix multiplication operation not possible"
						+ "\n\tA*B must be A (n x m) & B (m x w)");
	return result;
}

	result = new Matrix(this.rows, m.cols); // generate n x w matrix

	for(int i = 0; i < result.rows; i++) {
		for(int j = 0; j < result.cols; j++) {
			double sum = 0;
			for(int k = 0; k < this.cols; k++) {
				sum += this.values[i][k] * m.values[k][j];
			}
			result.values[i][j] = sum;
		}
	}
	return result;
}


/*Implementation: instead using loops above to calculate each elements, 
  * here you will use threads to accomplish the matrix product task.
  * Similar to the "multiplyBy()" above, the input matrix m represents
  * the second matrix that you will use the current matrix to times. The
  * returned Matrix will be the product result matrix.  
  * The code below is just an example, which is not the real solution. 
  * You need to create multiple threads to do the multiplication with each thread
  * computing one column within the result matrix*/
Matrix multiplyByThreads(Matrix m) throws InterruptedException {
  //Implementation here...
	Matrix result = null;//You need to initialize the result Matrix properly
	if(this.cols != m.rows) {
		System.err.println("Matrix multiplication operation not possible"
						+ "\n\tA*B must be A (n x m) & B (m x w)");
		return result;
	}

	result = new Matrix(this.rows, m.cols);

  // Create Java Threads
	int numThreads = result.cols;
	Thread[] threads = new Thread[numThreads];

  // Start threads using the helper class ColumnCalculator for rows of the 'A' & columns of the 'B' matrices
	for(int i = 0; i < numThreads; i++) {
		ColumnCalculator calc = new ColumnCalculator(this, m, result, i);
		threads[i] = new Thread(calc);
		threads[i].start();
	}

  // Join threads and build result matrix
	for(int i = 0; i < numThreads; i++) {
		try {
			threads[i].join();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}

	return result;
}


/* The main function for evaluation purpose*/
public static void main(String[] args) {
  //Implementation here...
	try {
		// Test and verification of implementation with small matrices
		double[][] a = {{1, 2}, {2, 3}};
		double[][] b = {{2, 3}, {3, 4}};
		Matrix small1 = new Matrix(2, 2, a);
		Matrix small2 = new Matrix(2, 2, b);
		System.out.printf("Matrix A (%d x %d):\n", small1.rows, small1.cols);
		small1.print();
		System.out.printf("Matrix B (%d x %d):\n", small2.rows, small2.cols);
		small2.print();

		Matrix smallResult = small1.multiplyByThreads(small2);
		System.out.printf("Result A*B (%d x %d):\n", smallResult.rows, smallResult.cols);
		smallResult.print();

		// Timed comparison between functions with large matrices
		Matrix big1 = new Matrix(3000, 1000);
		Matrix big2 = new Matrix(1000, 2000);

		 // multiplyBy() call
		long start = System.nanoTime();
		big1.multiplyBy(big2);
	    long end = System.nanoTime();
	    double singleTime = (end - start) / 1000000000.;

	     // multiplyByThreads() call
	    start = System.nanoTime();
		big1.multiplyByThreads(big2);
		end = System.nanoTime();
	    double threadedTime = (end - start) / 1000000000.;

	     // Display output
	    System.out.println("\nPerformance Results:");
	    System.out.printf("multiplyBy(): %.3f seconds%n", singleTime);
	    System.out.printf("multiplyByThreads(): %.3f seconds%n", threadedTime);

		} catch (InterruptedException e) {
			System.err.println("\n\tError: Threads have been interrupted... exiting program");
		} catch (NullPointerException e) {
			System.err.println("\n\tError: null Matrix");
	}
}
}
