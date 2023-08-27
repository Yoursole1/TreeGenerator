package me.yoursole.math.numerical.complex;

import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.matrix.Matrix;


/**
 * Since matrices can contain other matrices, this is the
 * "base case" in that recursion.  NumericalBase acts as a
 * complex number but from the perspective of the algorithms operating
 * on the matrix it is within this is simply another term within
 * the nested matrix
 */

public class NumericalBase implements Numerical {

    private final double real;
    private final double imaginary;

    public NumericalBase(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public NumericalBase(double real) {
        this(real, 0);
    }

    public NumericalBase complexConjugate() {
        return new NumericalBase(this.real, -this.imaginary);
    }

    public double arg() {
        return Math.atan(imaginary / real);
    }

    public double modulo() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }


    /**
     * Generates a rotation matrix with the same rotational properties as
     * the complex number when multiplied by a 2D vector
     * <p>
     * This works using the fact that complex number rotate by adding arguments when multiplied, and they
     * scale the modulus through multiplication.  This means that the behavior of
     * any complex number when multiplied can be represented as a 2x2 matrix.
     *
     * @return rotation matrix
     */
    public Matrix getMatrixForm() {
        double theta = this.arg();

        Matrix rotation = new Matrix(new Numerical[][]{
                {new NumericalBase(Math.cos(theta), 0), new NumericalBase(-Math.sin(theta), 0)},
                {new NumericalBase(Math.sin(theta), 0), new NumericalBase(Math.cos(theta), 0)}
        });
        rotation = (Matrix) rotation.multiply(new NumericalBase(this.modulo(), 0));
        return rotation;
    }

    public NumericalBase rotate(double angle) {
        NumericalBase rotation = new NumericalBase(Math.cos(angle), Math.sin(angle));

        return (NumericalBase) rotation.multiply(this);
    }

    @Override
    public String toString() {
        return "(" + this.real + (this.imaginary >= 0 ? "+" : "") + this.imaginary + "i)";
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }
}

