package me.yoursole.math.numerical.matrix.square;

import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.matrix.Matrix;

public class SquareMatrix extends Matrix {

    public SquareMatrix(Numerical[][] values) {
        super(SquareMatrix.verifySquare(values));
    }

    private static Numerical[][] verifySquare(Numerical[][] values){
        if (values.length != values[0].length){
            throw new IllegalArgumentException("Matrix must be square");
        }
        return values;
    }

}
