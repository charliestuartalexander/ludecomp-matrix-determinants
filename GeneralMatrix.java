/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 * 
 * Tasks:
 *
 * 1) Complete this class with the indicated methods and instance variables.
 *
 * 2) Fill in the following fields:
 *
 * NAME: Charlie Alexander
 * UNIVERSITY ID: 2109968
 * DEPARTMENT: Maths
 */

import java.util.Arrays;

public class GeneralMatrix extends Matrix {
    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] values;

    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the data array.
     *
     * @param firstDim   The first dimension of the array.
     * @param secondDim  The second dimension of the array.
     */
    public GeneralMatrix(int firstDim, int secondDim) {
        super(firstDim,secondDim);
        //initialise dimensions from superclass
        if(jDim<1||iDim<1){
            throw new MatrixException("Can't make matrix with less than 1 columns/rows") ;
        }
        //exception for matrices with less than 1 column or row       
        this.values=new double[firstDim][secondDim];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the second matrix.
     *
     * @param second  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix second) {
        super(second.iDim,second.jDim);
        //initialise dimensions then set 'values' to these
        values = new double[second.iDim][second.jDim];
        // loop through each value and copy it into a second variable
        for(int i=0; i<iDim; i++){
              for(int j=0; j<jDim; j++){
                values[i][j] = second.values[i][j];
              }
    }
    }
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        //exceptions for invalid dimensions trying to be accessed
        if (j<0||j>=jDim){
            throw new MatrixException("There isn't this column") ;
        }
        if (i<0||i>=iDim){
            throw new MatrixException("There isn't this row") ;
        }
        //returns wanted IJ values for each position in a matrix
        double IJval = values[i][j];
        return IJval;
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the values array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        //exceptions for invalid dimensions trying to be accessed
        if (j<0||j>jDim-1){
            throw new MatrixException("There isn't such a column");
        }
        if (i<0||i>iDim-1){
            throw new MatrixException("There isn't such a row");
        }
        //sets wanted valaue for provided position in matrix
        else{
          this.values[i][j]=value;
        }
    }
    
    
    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        // initialise a sign variable, determinant and a decomposed matrix
        double[] sign = new double[1];
        double determinant = 1;
        GeneralMatrix decompMat = LUdecomp(sign);
        // loop through each position along the main diagonal of the decomposed matrix
        // and multiply these until reaching the end to calculate determinant
        for(int i=0;i<jDim;i++){
            determinant = determinant*decompMat.values[i][i] ;
        }
        // return determinant with the correct sign
        return sign[0]*determinant ;
    }
    

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second) {
        GeneralMatrix newMatrix = new GeneralMatrix(iDim,jDim);
        //create newmatrix with dimensions of original
        if(iDim!=second.iDim){
              throw new MatrixException("Number of rows aren't equal, can't add matrices");
        }
        //exception for unequal row dimensions    

        if(jDim!=second.jDim){
              throw new MatrixException("Numbers of columns aren't equal, can't add matrices");
        }
        //exception for unequal column dimensions

        for(int i=0; i<iDim; i++){
            
            for(int j=0; j<jDim; j++){
              newMatrix.values[i][j] = values[i][j]+second.getIJ(i,j);
            }
        }
        //add corresponding elements in second matrix to each element of original matrix

        return newMatrix;
    }
    
    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {
        GeneralMatrix BA = new GeneralMatrix(iDim,A.jDim);

        //makes new matrix with num rows equal to that of this matrix and num columns equal to that of matrix A        
        if(jDim != A.iDim){
            throw new MatrixException("Number of columns of first matrix aren't equal to number of rows of second") ;
        }
        //exception as dimensions aren't compatible for matrix multiplication
        
        //we then loop through each position in matrix and multiply it by the corresponding values from the second matrix
        //these are then stored and returned in a matrix we previously initialised called BA
        for (int i=0; i<iDim; i++){
            for (int j=0; j<A.jDim; j++){
                double count = 0;
                for (int k=0; k<jDim; k++){
                  count = count + values[i][k]*A.getIJ(k,j);
                }
                BA.values[i][j] = count;
            }
        }
        return BA;
    }
    

    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {
        GeneralMatrix scaledMatrix = new GeneralMatrix(this);
        //we loop through each position in matrix and simply multiply it by the provied scaled and 
        //store it in a new scledmatrix which we then return
        for(int i=0; i<iDim; i++){

            for(int j=0; j<jDim; j++){
              scaledMatrix.values[i][j] = scalar * scaledMatrix.values[i][j];
            }
        }
        return scaledMatrix;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
        //loop through each position in matrix and insert a random value from 0 to 1
        for(int i=0; i<iDim; i++){

            for(int j=0; j<jDim; j++){
              values[i][j] = Math.random();
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     * 
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 u_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * sign[0] calculated by the function.
     * 
     * If the matrix is singular, then the routine throws a MatrixException.
     * In this case the string from the exception's getMessage() will contain
     * "singular"
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     * 
     * @param sign  An array of length 1. On exit, the value contained in here
     *              will either be 1 or -1, which you can use to calculate the
     *              correct sign on the determinant.
     * @return      The LU decomposition of the matrix.
     */
    public GeneralMatrix LUdecomp(double[] sign) {
        // This method is complete. You should not even attempt to change it!!
        if (jDim != iDim)
            throw new MatrixException("Matrix is not square");
        if (sign.length != 1)
            throw new MatrixException("d should be of length 1");
        
        int           i, imax = -10, j, k; 
        double        big, dum, sum, temp;
        double[]      vv   = new double[jDim];
        GeneralMatrix a    = new GeneralMatrix(this);
        
        sign[0] = 1.0;
        
        for (i = 1; i <= jDim; i++) {
            big = 0.0;
            for (j = 1; j <= jDim; j++)
                if ((temp = Math.abs(a.values[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }
        
        for (j = 1; j <= jDim; j++) {
            for (i = 1; i < j; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= jDim; i++) {
                sum = a.values[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.values[i-1][k-1]*a.values[k-1][j-1];
                a.values[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= jDim; k++) {
                    dum = a.values[imax-1][k-1];
                    a.values[imax-1][k-1] = a.values[j-1][k-1];
                    a.values[j-1][k-1] = dum;
                }
                sign[0] = -sign[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.values[j-1][j-1] == 0.0)
                a.values[j-1][j-1] = 1.0e-20;
            if (j != jDim) {
                dum = 1.0/a.values[j-1][j-1];
                for (i = j+1; i <= jDim; i++)
                    a.values[i-1][j-1] *= dum;
            }
        }
        
        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        double constant = 7 ;

        GeneralMatrix matrix1 = new GeneralMatrix(2,2);
        matrix1.setIJ(0,0,5);
        matrix1.setIJ(0,1,62);
        matrix1.setIJ(1,0,-4);
        matrix1.setIJ(1,1,0);

        GeneralMatrix matrix2 = new GeneralMatrix(2,2);
        matrix2.setIJ(0,0,-2);
        matrix2.setIJ(0,1,6);
        matrix2.setIJ(1,0,3);
        matrix2.setIJ(1,1,3);

        System.out.println(matrix1.getIJ(1,1));
        System.out.println(matrix1.toString());
        System.out.println(matrix2.toString());
        System.out.println(matrix1.determinant());
        System.out.println(matrix2.determinant());
        System.out.println(matrix1.add(matrix2).toString());
        System.out.println(matrix1.multiply(matrix2).toString());
        System.out.println(matrix1.multiply(constant).toString());

        GeneralMatrix testObj = new GeneralMatrix(3,4);
        testObj.random();
        System.out.println(testObj.toString());        

    }
}