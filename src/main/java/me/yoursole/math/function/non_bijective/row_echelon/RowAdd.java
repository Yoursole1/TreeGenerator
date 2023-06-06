package me.yoursole.math.function.non_bijective.row_echelon;


import me.yoursole.math.function.non_bijective.NonBijective;
import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.matrix.Matrix;

public class RowAdd implements NonBijective {
    @Override
    public Matrix f(Numerical... x) {
        if(x.length != 2){
            throw new IllegalArgumentException("Must be adding two rows");
        }

        Numerical rowA = x[0];
        Numerical rowB = x[1];

        if(!(rowA instanceof Matrix && rowB instanceof Matrix)){
            throw new IllegalArgumentException("Both terms must be a matrix");
        }

        Numerical[] a =  ((Matrix) rowA).getGroup(0, false);
        Numerical[] b =  ((Matrix) rowB).getGroup(0, false);

        if(a.length != b.length){
            throw new IllegalArgumentException("Lengths of rows must be equal to perform row operation");
        }

        Numerical[] result = new Numerical[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = a[i].add(b[i]);
        }

        return new Matrix(new Numerical[][]{
                result
        });
    }

    @Override
    public NonBijective pseudoInverse() {
        return null;
    }
}
