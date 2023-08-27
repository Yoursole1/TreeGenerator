package me.yoursole.skeleton.generator;

import me.yoursole.math.function.Function;
import me.yoursole.math.function.non_bijective.NormalDist;
import me.yoursole.math.numerical.complex.NumericalBase;
import me.yoursole.skeleton.Branch;
import me.yoursole.skeleton.Node;
import me.yoursole.skeleton.TreeSkeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private final float spread;
    private final float split;
    private final float branch;
    private final float pull;
    private final float branchHeight;

    public Generator(float spread, float split, float branch, float pull, float branchHeight) {
        // TODO verify all values are 0 < x < 1

        this.spread = spread;
        this.split = split;
        this.branch = branch;
        this.pull = pull;
        this.branchHeight = branchHeight;
    }

    public TreeSkeleton generate() {
        TreeSkeleton skeleton = new TreeSkeleton();
        skeleton = new TrunkGenerator(skeleton).generate(this.spread, this.split, this.branch, this.pull, this.branchHeight);
        skeleton = new BranchGenerator(skeleton).generate(this.spread, this.split, this.branch, this.pull, this.branchHeight);
        skeleton = new GravityGenerator(skeleton).generate(this.spread, this.split, this.branch, this.pull, this.branchHeight);
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

        tipNodes.add(new Node(TreeSkeleton.WIDTH / 2.0f, TreeSkeleton.HEIGHT)); //initial node
        tipAngles.add(0f);

        int height = (int) ((NumericalBase) new NormalDist(50, 3).f()).getReal();

        Function splitRate = x -> { // x is number of branches formed
            double n = ((NumericalBase) x[0]).getReal();

            return new NumericalBase(split)
                    .multiply(new NumericalBase(
                            Math.pow(Math.E, -Math.pow(n - 5, 2))
                    ));
        };

        Function branchSize = x -> new NumericalBase(20 / (((NumericalBase) x[0]).getReal() + 5));

        for (int i = 0; i < height; i++) {
            boolean isSplit = Math.random() < ((NumericalBase) splitRate.f(new NumericalBase(i))).getReal();
            int splitIndex = new Random().nextInt(tipNodes.size()); // used if we are splitting (only one split per "height layer")

            List<Node> tipNodesNew = new ArrayList<>();
            List<Float> tipAnglesNew = new ArrayList<>();

            for (int j = 0; j < tipNodes.size(); j++) {
                Node currentNode = tipNodes.get(j);
                NumericalBase currentAngle = new NumericalBase(tipAngles.get(j));

                if (isSplit && j == splitIndex) {
                    // perform split

                    NumericalBase angleA = (NumericalBase) new NormalDist(-10, spread * 10).f().add(currentAngle); // angle of branches from a split
                    NumericalBase angleB = (NumericalBase) new NormalDist(10, spread * 10).f().add(currentAngle);

                    Branch branchA = super.createBranch(
                            currentNode,
                            (float) angleA.getReal(),
                            (float) ((NumericalBase) branchSize.f(new NumericalBase(i))).getReal()
                    );

                    Branch branchB = super.createBranch(
                            currentNode,
                            (float) angleB.getReal(),
                            (float) ((NumericalBase) branchSize.f(new NumericalBase(i))).getReal()
                    );

                    tipNodesNew.add(branchA.getB());
                    tipNodesNew.add(branchB.getB());

                    tipAnglesNew.add((float) angleA.getReal());
                    tipAnglesNew.add((float) angleB.getReal());

                    super.getInput().addBranch(branchA);
                    super.getInput().addBranch(branchB);

                    continue;
                }
                // grow tip node

                NumericalBase angle = (NumericalBase) new NormalDist(0, spread * 10).f().add(currentAngle);

                Branch newBranch = super.createBranch(
                        currentNode,
                        (float) angle.getReal(),
                        (float) ((NumericalBase) branchSize.f(new NumericalBase(i))).getReal()
                );

                tipNodesNew.add(newBranch.getB());

                tipAnglesNew.add((float) angle.getReal());

                super.getInput().addBranch(newBranch);
            }

            tipNodes = tipNodesNew;
            tipAngles = tipAnglesNew;
        }

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
        return super.getInput();
    }
}

class GravityGenerator extends GeneratorLayer {

    public GravityGenerator(TreeSkeleton input) {
        super(input);
    }

    @Override
    TreeSkeleton generate(float spread, float split, float branch, float pull, float branchHeight) {
        return super.getInput();
    }
}
