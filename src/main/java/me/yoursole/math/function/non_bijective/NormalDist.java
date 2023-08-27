package me.yoursole.math.function.non_bijective;

import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.complex.NumericalBase;

import java.util.Random;

public class NormalDist implements NonBijective {

    private static final Random RANDOM = new Random();

    private final double mean;
    private final double std;

    public NormalDist(double mean, double std) {
        if (std <= 0) {
            throw new IllegalArgumentException("standard deviation must be greater than 0");
        }

        this.mean = mean;
        this.std = std;
    }

    public NormalDist(NumericalBase mean, NumericalBase std) {
        this(mean.getReal(), std.getReal());
    }

    @Override
    public Numerical f(Numerical... x) {
        return new NumericalBase(NormalDist.RANDOM.nextGaussian() * this.std + this.mean);
    }

    public NormalDist convolve(NormalDist other) {
        double mean = this.mean + other.mean;
        double std = Math.sqrt(Math.pow(this.std, 2) + Math.pow(other.std, 2));
        return new NormalDist(mean, std);
    }


    @Override
    public NonBijective pseudoInverse() {
        return null;
    }

    public double getMean() {
        return mean;
    }

    public double getStd() {
        return std;
    }
}
