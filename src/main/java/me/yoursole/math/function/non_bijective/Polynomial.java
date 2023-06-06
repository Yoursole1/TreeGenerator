package me.yoursole.math.function.non_bijective;

import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.complex.NumericalBase;

public class Polynomial implements NonBijective {

    /**
     * Coefficients of polynomial in descending order
     * (index 0 is the highest power, the last index is power 0)
     */
    private final Numerical[] coefficients;

    public Polynomial(Numerical[] coefficients){
        this.coefficients = coefficients;
    }

    /**
     * @throws IllegalArgumentException if the input is not a single numerical
     * @param x numerical value to evaluate
     * @return f(x)
     */
    @Override
    public Numerical f(Numerical... x) {
        if(x.length != 1){
            throw new IllegalArgumentException("Multivariable input on 1 -> 1 function");
        }

        Numerical output = new NumericalBase(0, 0);

        for (int i = 0; i < this.coefficients.length; i++) {
            Numerical freeTerm = x[0].pow(this.coefficients.length - i - 1);
            Numerical coefficient = this.coefficients[i];
            Numerical term = coefficient.multiply(freeTerm); //since we are potentially dealing with matrices the order matters here
            output = output.add(term);
        }

        return output;
    }

    @Override
    public NonBijective pseudoInverse() {
        return null;
    }
}
