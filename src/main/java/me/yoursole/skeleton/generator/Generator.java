package me.yoursole.skeleton.generator;



public class Generator {

    private static final GeneratorLayer SPREAD_GENERATOR = new GeneratorLayer(
            x -> null, // x must be a 2 element array of numerical objects
            x -> null
    );

    private static final GeneratorLayer PULL_GENERATOR = new GeneratorLayer(
            x -> null,
            x -> null
    );
}
