package me.yoursole.skeleton.generator;


import me.yoursole.math.numerical.complex.NumericalBase;

public class Generator {

    // x = {param, t}
    private static final GeneratorLayer SPREAD_GENERATOR = new GeneratorLayer(
            x -> { // mean
                NumericalBase param = (NumericalBase) x[0]; // I think we need to include location data for the generators (maybe)
                NumericalBase t = (NumericalBase) x[1];
                return new NumericalBase(0);
            },
            x -> { // std
                NumericalBase param = (NumericalBase) x[0];
                NumericalBase t = (NumericalBase) x[1];
                return null;
            }
    );

    private static final GeneratorLayer PULL_GENERATOR = new GeneratorLayer(
            x -> {
                NumericalBase param = (NumericalBase) x[0];
                NumericalBase t = (NumericalBase) x[1];
                return null;
            },
            x -> {
                NumericalBase param = (NumericalBase) x[0];
                NumericalBase t = (NumericalBase) x[1];
                return null;
            }
    );
}
