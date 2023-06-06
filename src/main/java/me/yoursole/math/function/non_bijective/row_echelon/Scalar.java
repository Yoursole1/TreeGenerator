package me.yoursole.math.function.non_bijective.row_echelon;

import me.yoursole.math.function.non_bijective.NonBijective;
import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.matrix.Matrix;

public class Scalar implements NonBijective {

    public Numerical scale;

    public Scalar(Numerical scale){
        this.scale = scale;
    }

    @Override
    public Numerical f(Numerical... x) {
        if(x.length != 1){
            throw new IllegalArgumentException("Only one row may be input");
        }

        Numerical rowA = x[0];

        if(!(rowA instanceof Matrix)){
            throw new IllegalArgumentException("Both terms must be a matrix");
        }

        Numerical[] a =  ((Matrix) rowA).getGroup(0, false);


        for (int i = 0; i < a.length; i++) {
            a[i] = a[i].multiply(this.scale);
        }

        return new Matrix(new Numerical[][]{
                a
        });
    }

    @Override
    public NonBijective pseudoInverse() {
        return null;
    }
}
