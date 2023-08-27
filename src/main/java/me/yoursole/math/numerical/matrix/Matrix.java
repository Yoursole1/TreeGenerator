package me.yoursole.math.numerical.matrix;

import me.yoursole.math.numerical.Numerical;

import java.util.Arrays;


public class Matrix implements Numerical {

    private final Numerical[][] values;

    public Matrix(Numerical[][] values) {
        this.values = values;
    }

    /**
     * Performs the dot product of the two Numerical arrays.
     * //TODO move to a utility class
     *
     * @param a
     * @param b
     * @return
     */
    public static Numerical productSum(Numerical[] a, Numerical[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Mismatched vector dimensions");
        }

        Numerical sum = a[0].multiply(b[0]);

        for (int i = 1; i < a.length; i++) {
            sum = sum.add(a[i].multiply(b[i]));
        }

        return sum;
    }

    public int[] getDimension() {
        return new int[]{this.values.length, this.values[0].length};
    }

    /**
     * Given a location of a row or column (top left being 0,0 and bottom right being max, max)
     * v means "vertical", so if v is true then the group is a vertical row within the matrix,
     * otherwise it is horizontal.  This function returns an array representing the entire group.
     * If the group was vertical, the output array is still horizontal.
     *
     * @param location
     * @param v
     * @return
     */
    public Numerical[] getGroup(int location, boolean v) {
        int dim1 = this.getDimension()[0];
        int dim2 = this.getDimension()[1];
        Numerical[] group = new Numerical[v ? dim1 : dim2];

        for (int i = 0; i < group.length; i++) {
            int row = v ? i : location;
            int col = v ? location : i;
            group[i] = this.values[row][col];
        }

        return group;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.values);
    }

    public Numerical[][] getValues() {
        return values;
    }
}
