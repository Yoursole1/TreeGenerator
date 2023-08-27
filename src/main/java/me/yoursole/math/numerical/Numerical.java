package me.yoursole.math.numerical;

import me.yoursole.math.MathObject;
import me.yoursole.math.numerical.complex.NumericalBase;


/**
 * Any mathematical object that does not have free variables
 */
public interface Numerical extends MathObject {
    default Numerical add(Numerical other) {
        AdditionStrategy strategy = AdditionStrategy.fetch(this, other);
        return strategy.getOperation().operate(this, other);
    }

    default Numerical multiply(Numerical other) {
        MultiplicationStrategy strategy = MultiplicationStrategy.fetch(this, other);
        return strategy.getOperation().operate(this, other);
    }

    default Numerical pow(int power) {
        if (power == 0) {
            return new NumericalBase(1, 0);
        }

        if (power < 0) {
            throw new IllegalArgumentException("power must be greater than 0");
        }

        Numerical base = this.add(new NumericalBase(0, 0)); //essentially a clone

        for (int i = 1; i < power; i++) { //starts at 1 because power 1 should not multiply
            base = base.multiply(base);
        }

        return base;
    }
}
