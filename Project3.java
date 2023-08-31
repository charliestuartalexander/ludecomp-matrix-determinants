/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented and they do not have placeholder return statements. Make sure 
 * you have carefully read the project formulation before starting to work 
 * on this file. You will also need to have completed the Matrix class, as 
 * well as GeneralMatrix and TriMatrix.
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

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param matrix      The matrix object that will be filled with random
     *                    samples.
     * @param nSamp       The number of samples to generate when calculating 
     *                    the variance. 
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix matrix, int nSamp) {
        //initialise values 
        double Sum = 0 ;
        double squaredSum = 0 ;
        double det = 0 ;

        //loop through nSamp matrices, fill them with random values, calculate their determinants the store
        //the sum of each determinent squared as squaredSum
        for(int i=0; i<nSamp; i++){
          matrix.random();
          det = matrix.determinant();
          Sum = Sum + det;
          squaredSum = squaredSum + Math.pow(det, 2);
        }
        //we then use this squaredSum to calculate the variance of the determinants of all of these matrices
        double variance = squaredSum/nSamp - Math.pow(Sum/nSamp, 2);
        return variance;
    }
    
    
    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50 and print the results to the output. See the 
     * formulation for more detail.
     */
    public static void main(String[] args) {
      //loop through each size of square matrix
      for(int n = 2; n<=50; n++){
        //we then create both a general and trimatrix with the dimension matching the index in the loop
        GeneralMatrix gen = new GeneralMatrix(n,n);
        TriMatrix tri = new TriMatrix(n);
        //we then compute the variance of 20000 general and 200000 tri random matrices of each dimension matrix
        double varGen = matVariance(gen, 20000);
        double varTri = matVariance(tri, 200000);
        //then these are outputted in standard form
        System.out.println( n + " " + String.format("%.15e",varGen) + " " + String.format("%.15e",varTri));
      }        
    }
}