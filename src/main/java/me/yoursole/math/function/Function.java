package me.yoursole.math.function;

import me.yoursole.math.MathObject;
import me.yoursole.math.numerical.Numerical;

/**
 * A (1* -> 1) mathematical function
 */

@FunctionalInterface
public interface Function extends MathObject {

    /**
     * @param x numerical value to evaluate
     * @return a transformed value
     */
    Numerical f(Numerical... x);
}
