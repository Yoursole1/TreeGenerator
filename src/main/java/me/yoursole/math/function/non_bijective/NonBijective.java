package me.yoursole.math.function.non_bijective;

import me.yoursole.math.function.Function;

public interface NonBijective extends Function {
    /**
     * @return inverse applicable on restricted domains if one exists
     */
    NonBijective pseudoInverse();
}
