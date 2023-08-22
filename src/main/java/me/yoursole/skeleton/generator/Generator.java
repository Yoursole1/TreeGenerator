package me.yoursole.skeleton.generator;


import me.yoursole.math.numerical.complex.NumericalBase;
import me.yoursole.skeleton.Branch;
import me.yoursole.skeleton.Node;
import me.yoursole.skeleton.TreeSkeleton;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private final float spread;
    private final float split;
    private final float branch;
    private final float pull;
    private final float branchHeight;

    public Generator(float spread, float split, float branch, float pull, float branchHeight){
        // TODO verify all values are 0 < x < 1

        this.spread = spread;
        this.split = split;
        this.branch = branch;
        this.pull = pull;
        this.branchHeight = branchHeight;
    }

    public TreeSkeleton generate(){
        TreeSkeleton skeleton = new TreeSkeleton();
        skeleton = new TrunkGenerator(skeleton).generate(this.spread, this.split, this.branch, this.pull, this.branchHeight);
        skeleton = new BranchGenerator(skeleton).generate(this.spread, this.split, this.branch, this.pull, this.branchHeight);
        return skeleton;
    }



    public float getSpread() {
        return spread;
    }

    public float getSplit() {
        return split;
    }

    public float getBranch() {
        return branch;
    }

    public float getPull() {
        return pull;
    }

    public float getBranchHeight() {
        return branchHeight;
    }
}

/**
 * Generates the trunk section of the tree
 */
class TrunkGenerator extends GeneratorLayer {

    public TrunkGenerator(TreeSkeleton input) {
        super(input);
    }

    @Override
    TreeSkeleton generate(float spread, float split, float branch, float pull, float branchHeight) {
        List<Node> tipNodes = new ArrayList<>(); //Nodes that can currently be appended to (removed after a connection is made)
        List<Float> tipAngles = new ArrayList<>(); //angles that each tip is at (associated by index)

        tipNodes.add(new Node(50, 100)); //initial node
        tipAngles.add(0f);



        // main tree structure is composed of Branches with length 5
        return super.getInput();
    }
}

/**
 * Generates the branches coming off the trunk
 */
class BranchGenerator extends GeneratorLayer {

    public BranchGenerator(TreeSkeleton input) {
        super(input);
    }

    @Override
    TreeSkeleton generate(float spread, float split, float branch, float pull, float branchHeight) {
        return null;
    }
}
