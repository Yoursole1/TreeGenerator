package me.yoursole.skeleton.generator;

import me.yoursole.math.numerical.complex.NumericalBase;
import me.yoursole.skeleton.Branch;
import me.yoursole.skeleton.Node;
import me.yoursole.skeleton.TreeSkeleton;

public abstract class GeneratorLayer {

    private final TreeSkeleton input;

    public GeneratorLayer(TreeSkeleton input) {
        this.input = input;
    }

    abstract TreeSkeleton generate(float spread, float split, float branch, float pull, float branchHeight);

    public TreeSkeleton getInput() {
        return input;
    }

    protected Branch createBranch(Node base, float angle, float size) { // utility function, prob should be moved elsewhere

        NumericalBase starting = new NumericalBase(0, -5);
        starting = starting.rotate(angle * Math.PI / 180);

        starting = (NumericalBase) starting.add(new NumericalBase(base.x, base.y));
        Node newNode = new Node((float) starting.getReal(), (float) starting.getImaginary());
        return new Branch(base, newNode, size);
    }
}
