package com.example;
//a = 4 x 3  matrix. It means a has 4 row and 3 columns. There are 12 = 3 x 4 numbers (elements)
//within the matrix A   a[4][3]

class ColumnCalculator implements Runnable{

    Matrix m1;
    Matrix m2;
    Matrix result;
    int col_idx; //specify which column of the result is going to be calculated

ColumnCalculator(Matrix m_1, Matrix m_2, Matrix r, int col) {
    m1 = m_1;
    m2 = m_2;
    result = r;
    col_idx = col;
}

 //calculating all the elements in the column of index (specified by "col_idx") of the result matrix
@Override
public void run(){
	//Implementation here...
    for(int i = 0; i < m1.rows; i++) {
    	double sum = 0;
    	
    	for(int j = 0; j < m1.cols; j++) {
    		sum += m1.values[i][j] * m2.values[j][col_idx];
    	}
    	result.values[i][col_idx] = sum;
    }
}
}

