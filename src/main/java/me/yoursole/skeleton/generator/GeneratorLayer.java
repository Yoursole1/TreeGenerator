package me.yoursole.skeleton.generator;

import me.yoursole.math.function.non_bijective.BivariateFunction;
import me.yoursole.math.function.non_bijective.NormalDist;
import me.yoursole.math.numerical.complex.NumericalBase;

public class GeneratorLayer {

    private final BivariateFunction meanCurve;
    private final BivariateFunction stdCurve;

    public GeneratorLayer(BivariateFunction meanCurve, BivariateFunction stdCurve){
        this.meanCurve = meanCurve;
        this.stdCurve = stdCurve;
    }

    /**
     * @param param the base tree parameter this layer works with
     * @param t how far through the tree generation process this is called at (basically # of current branches)
     * @return a normal distribution describing this layer that is used by the main generator
     */
    public NormalDist generateNormal(double param, int t){
        return new NormalDist(
                (NumericalBase) this.meanCurve.f(new NumericalBase(param), new NumericalBase(t)),
                (NumericalBase) this.stdCurve.f(new NumericalBase(param), new NumericalBase(t))
        );
    }
}
