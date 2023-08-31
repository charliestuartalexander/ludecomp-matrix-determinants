/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
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

public class TriMatrix extends Matrix {
    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diagonal;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upperDiagonal;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lowerDiagonal;
    
    /**
     * Constructor function: should initialise iDim and jDim through the Matrix
     * constructor and set up the values array.
     *
     * @param dimension  The dimension of the array.
     */
    public TriMatrix(int dimension) {
        super(dimension, dimension);
        if(dimension<1){
            throw new MatrixException("matrix can't have less than 1 row/column") ;
        }

        //exception for matrix with less than 1 row/column

        diagonal = new double[dimension];
        upperDiagonal = new double[dimension-1];
        lowerDiagonal = new double[dimension-1];
    }
    
    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {
        //exceptions for attempting to get values from invalid dimensions
        if(i>iDim-1||i<0){
            throw new MatrixException("There isn't such a row") ;
        }
        if(j>jDim-1||j<0){
            throw new MatrixException("There isn't such a column") ;
        }
        //initialises ij and gets the value from whichever one of the diagonals required
        double ij=0 ;

        if(i==j){
          ij=diagonal[i];
        }
        else if(j-i==1){
          ij=upperDiagonal[i];
        }
        else if(i-j==1){
          ij=lowerDiagonal[j];
        }
        return ij;
    }
    
    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i      The location in the first co-ordinate.
     * @param j      The location in the second co-ordinate.
     * @param value  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double value) {
        //exception for trying to set values in an invalid dimension
        if(i>iDim-1||i<0){
          throw new MatrixException("There isn't such a row");
        }
        if(j>jDim-1||j<0){
          throw new MatrixException("There isn't such a column");
        }
        //exception for trying to set outside of the 3 central diagonals
        if (Math.abs(i-j)>1){
          throw new MatrixException("Can't set values outside of the central 3 diagonals");
        }
        //depending on which diagonal set the required position in matrix to the necessary value
        if(i==j){
          diagonal[i] = value;
        }
        else if(j-i==1){
          upperDiagonal[i] = value;
        }
        else if(i-j==1){
          lowerDiagonal[j] = value;
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {
        //call LUdecomp method to create a decomposed version of the current trimatrix
        TriMatrix decompMatrix = LUdecomp();
          //intialise the determinant's value as 1
          double det = 1;
          //loop through each value of the decomposed matrix and multiply each value along the main diagonal to calculate the determinant
          for(int i=0; i<iDim; i++){
              det = det*decompMatrix.getIJ(i, i);
            }
        return det;
    }
    
    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     * 
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix LUdecomp() {
        TriMatrix decompMatrix = new TriMatrix(this.iDim) ;
          // initialising diagonal/lower to our d1 and l1 (using difference Eqns)
          double diag = getIJ(0,0);
          double lower = getIJ(1,0)/diag;
          // set d1, l1 and u1 in the new matrix
          decompMatrix.setIJ(0, 0, diag);
          decompMatrix.setIJ(1, 0, lower);
          decompMatrix.setIJ(0, 1, getIJ(0,1));
          //loop through each eelement in the 3 main diagonals except the last value
          for(int k=1; k<(this.iDim)-1; k++){
              //set each value according to the calculated difference equations
              diag = getIJ(k, k) - lower * getIJ(k-1, k); 
              decompMatrix.setIJ(k, k, diag);

              lower = getIJ(k+1, k)/diag;
              decompMatrix.setIJ(k+1, k, lower);

              decompMatrix.setIJ(k, k+1, getIJ(k, k+1));
            }
          //loop through the final values
          decompMatrix.setIJ(iDim-1, iDim-1, getIJ(iDim-1, iDim-1) - lower * getIJ(iDim-2 ,iDim-1));
       
        return decompMatrix;
    }

    /**
     * Add the matrix to another second matrix.
     *
     * @param second  The Matrix to add to this matrix.
     * @return        The sum of this matrix with the second matrix.
     */
    public Matrix add(Matrix second){
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
              newMatrix.setIJ(i, j, getIJ(i,j) + second.getIJ(i,j));
            }
        }
        //loop through and add corresponding elements in second matrix to each element of original matrix

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

        if(jDim != A.iDim){
          throw new MatrixException("Number of columns of first matrix aren't equal to number of rows of second");
        }

        //exception as dimensions aren't compatible for matrix multiplication

        //create new general matrix to store elements of the product in
        GeneralMatrix triProduct = new GeneralMatrix(iDim, A.jDim);

        //creates new general matrix of dimension rows of this matrix by columns of matrix A
          
          //loop through each element of the first matrix and multiply by the corresponding value of the second matrix
          for(int i=0; i<iDim; i++){

            for(int j=0; j<A.jDim; j++){
            
                double count = 0;
                for(int k=0; k<jDim; k++){
                  count = count + (getIJ(i, k) * A.getIJ(k, j));
                }
                //store each calculated element in the newly initialised matrix to fill it
                triProduct.setIJ(i, j, count);
              }
            }

        return triProduct;
    }
    
    /**
     * Multiply the matrix by a scalar.
     *
     * @param scalar  The scalar to multiply the matrix by.
     * @return        The product of this matrix with the scalar.
     */
    public Matrix multiply(double scalar) {
        TriMatrix scaledMatrix = new TriMatrix(iDim);
        // make matrix dimensions equal to number of rows or columns
          for(int i=0; i<iDim; i++){
            //loops through each (non-zero) value of the matrix and multiplies it by a scalar of the corresponding i,j values
            for(int j=0; j<jDim; j++){
              if (Math.abs(i-j)<=1){
                scaledMatrix.setIJ(i, j, scalar*getIJ(i, j));
                }
              }
            }

        return scaledMatrix ;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {
      //loops through each (non-zero) value of the matrix and assigns a random value to it (along the 3 leading diagonals)
        for(int i=0; i<iDim; i++){

            for(int j=0; j<jDim; j++){

              if (Math.abs(i-j)<=1){
                setIJ(i, j, Math.random());
                }
            }
        }
    }
    
    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {
        //initialise some values
        double a = 3;
        //creates the first 5x5 Trimatrix and sets its values
        TriMatrix matrix = new TriMatrix(5);
        matrix.setIJ(0,0,2);
        matrix.setIJ(0,1,1);
        matrix.setIJ(1,0,9);
        matrix.setIJ(1,1,4);
        matrix.setIJ(1,2,-2);
        matrix.setIJ(2,1,0.3);
        matrix.setIJ(2,2,7.2);
        matrix.setIJ(2,3,0);
        matrix.setIJ(3,2,0.1);
        matrix.setIJ(3,3,-6);
        matrix.setIJ(3,4,9);
        matrix.setIJ(4,3,1);
        matrix.setIJ(4,4,3.14);
        //creates the second 5x5 Trimatrix and sets its values
        TriMatrix matrix2 = new TriMatrix(5);
        matrix2.setIJ(0,0,4);
        matrix2.setIJ(0,1,3);
        matrix2.setIJ(1,0,1.2);
        matrix2.setIJ(1,1,4);
        matrix2.setIJ(1,2,-3);
        matrix2.setIJ(2,1,0.1);
        matrix2.setIJ(2,2,6.4);
        matrix2.setIJ(2,3,2);
        matrix2.setIJ(3,2,-4);
        matrix2.setIJ(3,3,1);
        matrix2.setIJ(3,4,8);
        matrix2.setIJ(4,3,1);
        matrix2.setIJ(4,4,9);
        //creates a general 5x5 matrix and sets its values
        GeneralMatrix g1 = new GeneralMatrix(5,5);
        g1.setIJ(0,0,5);
        g1.setIJ(0,1,4);
        g1.setIJ(0,2,2);
        g1.setIJ(0,3,7);
        g1.setIJ(0,4,0);
        g1.setIJ(1,0,-1);
        g1.setIJ(1,1,-4);
        g1.setIJ(1,2,-2);
        g1.setIJ(1,3,9);
        g1.setIJ(1,4,0.5);
        g1.setIJ(2,0,0);
        g1.setIJ(2,1,5);
        g1.setIJ(2,2,5);
        g1.setIJ(2,3,5);
        g1.setIJ(2,4,6);
        g1.setIJ(3,0,-1);
        g1.setIJ(3,1,-5);
        g1.setIJ(3,2,0);
        g1.setIJ(3,3,1);
        g1.setIJ(3,4,1);
        g1.setIJ(4,0,0);
        g1.setIJ(4,1,5);
        g1.setIJ(4,2,2);
        g1.setIJ(4,3,2);
        g1.setIJ(4,4,10);
        // Tests for most of the methods on trimatrices
        System.out.println(matrix.getIJ(1,1));
        System.out.println(matrix.toString());
        System.out.println(matrix2.toString());
        System.out.println(matrix.determinant());
        System.out.println(matrix2.determinant());
        System.out.println(matrix.add(matrix2).toString());
        System.out.println(matrix.multiply(matrix2).toString());
        System.out.println(matrix.multiply(a).toString());

        // More tests for methods on general matrices
        System.out.println(g1.toString());
        System.out.println(matrix.add(g1).toString());
        System.out.println(matrix.multiply(g1).toString());

        // Test creating a random trimatrix through the 'tritestObj' object
        TriMatrix tritestObj = new TriMatrix(6);
        tritestObj.random();
        System.out.println(tritestObj.toString());
    }
}