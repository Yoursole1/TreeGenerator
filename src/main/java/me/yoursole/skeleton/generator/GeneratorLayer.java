package me.yoursole.skeleton.generator;

import me.yoursole.skeleton.TreeSkeleton;

public abstract class GeneratorLayer {

    private final TreeSkeleton input;

    public GeneratorLayer(TreeSkeleton input){
        this.input = input;
    }

    abstract TreeSkeleton generate(float spread, float split, float branch, float pull, float branchHeight);

    public TreeSkeleton getInput() {
        return input;
    }
}
