package me.yoursole.math.function.non_bijective;

import me.yoursole.math.numerical.Numerical;
import me.yoursole.math.numerical.complex.NumericalBase;

import java.util.Random;

public class NormalDist implements NonBijective {

    private final double mean;
    private final double std;

    public NormalDist(double mean, double std){
        this.mean = mean;
        this.std = std;
    }

    @Override
    public Numerical f(Numerical... x) {
        if(std == 0){
            throw new IllegalArgumentException("standard deviation can not be 0");
        }

        return new NumericalBase(new Random().nextGaussian() * this.std + this.mean);
    }

    public NormalDist convolve(NormalDist other){
        return null;
    }



    @Override
    public NonBijective pseudoInverse() {
        return null;
    }
}
