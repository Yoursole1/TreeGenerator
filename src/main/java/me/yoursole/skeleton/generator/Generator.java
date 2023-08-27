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

import static me.yoursole.skeleton.generator.Generator.*;

public class Generator {

    // Class-level constants
    protected static final float DEFAULT_HEIGHT_MEAN = 15;
    protected static final float DEFAULT_HEIGHT_VARIABILITY = 2;
    protected static final float DEFAULT_ANGLE_SPREAD_NEGATIVE = -10;
    protected static final float DEFAULT_ANGLE_SPREAD_POSITIVE = 10;
    protected static final float DEFAULT_ANGLE_VARIABILITY_MULTIPLIER = 10;
    protected static final float DEFAULT_BRANCH_SIZE = 20;

    private final float spread;
    private final float split;
    private final float branch;
    private final float variability;
    private final float branchHeight;

    public Generator(float spread, float split, float branch, float variability, float branchHeight) {
        if (spread < 0 || spread > 1){
            throw new IllegalArgumentException("spread must be between 0 and 1");
        }

        if (split < 0 || split > 1){
            throw new IllegalArgumentException("split must be between 0 and 1");
        }

        if (branch < 0 || branch > 1){
            throw new IllegalArgumentException("branch must be between 0 and 1");
        }

        if (variability < 0 || variability > 1){
            throw new IllegalArgumentException("variability must be between 0 and 1");
        }

        if (branchHeight < 0 || branchHeight > 1){
            throw new IllegalArgumentException("branchHeight must be between 0 and 1");
        }

        this.spread = spread;
        this.split = split;
        this.branch = branch;
        this.variability = variability;
        this.branchHeight = branchHeight;
    }

    public TreeSkeleton generate() {
        TreeSkeleton skeleton = new TreeSkeleton();
        skeleton = new TrunkGenerator(skeleton).generate(this.spread, this.split, this.branch, this.variability, this.branchHeight);
        skeleton = new BranchGenerator(skeleton).generate(this.spread, this.split, this.branch, this.variability, this.branchHeight);
        return skeleton;
    }
}

/**
 * Generates the trunk section of the tree
 */
class TrunkGenerator extends GeneratorLayer {
    // Class-level constants
    private static final int DEFAULT_SPLIT_INDEX = 5;

    public TrunkGenerator(TreeSkeleton input) {
        super(input);
    }

    @Override
    TreeSkeleton generate(float spread, float split, float branch, float variability, float branchHeight) {
        List<Node> tipNodes = new ArrayList<>(); //Nodes that can currently be appended to (removed after a connection is made)
        List<Float> tipAngles = new ArrayList<>(); //angles that each tip is at (associated by index)

        tipNodes.add(new Node(TreeSkeleton.WIDTH / 2.0f, TreeSkeleton.HEIGHT)); //initial node
        tipAngles.add(0f);

        int height = (int) ((NumericalBase) new NormalDist(DEFAULT_HEIGHT_MEAN, DEFAULT_HEIGHT_VARIABILITY  * variability).f()).getReal();

        Function splitRate = x -> { // x is number of branches formed
            double n = ((NumericalBase) x[0]).getReal();

            return new NumericalBase(split)
                    .multiply(new NumericalBase(
                            Math.pow(Math.E, -Math.pow(n - DEFAULT_SPLIT_INDEX, 2))
                    ));
        };

        Function branchSize = x -> new NumericalBase(DEFAULT_BRANCH_SIZE  / (((NumericalBase) x[0]).getReal() + 5));

        for (int i = 0; i < height; i++) {
            boolean isSplit = Math.random() < ((NumericalBase) splitRate.f(new NumericalBase(i))).getReal();
            int splitIndex = new Random().nextInt(tipNodes.size()); // used if we are splitting (only one split per "height layer")

            List<Node> tipNodesNew = new ArrayList<>();
            List<Float> tipAnglesNew = new ArrayList<>();

            for (int j = 0; j < tipNodes.size(); j++) {
                Node currentNode = tipNodes.get(j);
                NumericalBase currentAngle = new NumericalBase(tipAngles.get(j));

                float size = (float) ((NumericalBase) branchSize.f(new NumericalBase(i))).getReal();

                if (isSplit && j == splitIndex) {
                    // perform split

                    NumericalBase angleA = (NumericalBase) new NormalDist(spread * DEFAULT_ANGLE_SPREAD_NEGATIVE, variability * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle); // angle of branches from a split
                    NumericalBase angleB = (NumericalBase) new NormalDist(spread * DEFAULT_ANGLE_SPREAD_POSITIVE, variability * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle);

                    Branch branchA = super.createBranch(
                            currentNode,
                            (float) angleA.getReal(),
                            size
                    );

                    Branch branchB = super.createBranch(
                            currentNode,
                            (float) angleB.getReal(),
                            size
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

                NumericalBase angle = (NumericalBase) new NormalDist(0, spread * variability * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle);

                Branch newBranch = super.createBranch(
                        currentNode,
                        (float) angle.getReal(),
                        size
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
    private static final float BASE_SIZE_REDUCTION = 0.1f;
    private static final float BASE_SIZE_CUTOFF = 0.6f;
    private static final float BASE_ANGLE_VARIABILITY = 5;

    public BranchGenerator(TreeSkeleton input) {
        super(input);
    }

    @Override
    TreeSkeleton generate(float spread, float split, float branch, float variability, float branchHeight) {
        this.generate(spread, split, branch, variability, branchHeight, 0.9f);
        return super.getInput();
    }

    private void generate(float spread, float split, float branch, float variability, float branchHeight, float size){
        if (size <= BASE_SIZE_CUTOFF){
            return;
        }

        Random r = new Random();

        List<TreeSkeleton> branches = new ArrayList<>();

        // size 1 -> 2/3 -> 4/9 -> 8/27
        for (Branch b : super.getInput().getBranches()){

            if (!(b.getSize() > size) || Math.abs(b.getSize() - size) > 1){
                continue;
            }

            boolean shouldBranch = Math.random() < branch;

            if (!shouldBranch){
                continue;
            }

            float baseAngle = Math.random() > 0.5 ? 90 : -90;
            baseAngle += b.getAngle(); // shift the angle to be relative to the angle of the branch its coming from
            baseAngle += (float) ((NumericalBase)new NormalDist(0, BASE_ANGLE_VARIABILITY).f()).getReal();

            float high = Math.max(b.getA().x, b.getB().x);
            float low = Math.min(b.getA().x, b.getB().x);

            float x = r.nextFloat() * (high - low) + low;
            float m = (b.getA().y - b.getB().y) / (b.getA().x - b.getB().x);
            float y = m * x - m * b.getA().x + b.getA().y;

            branches.add(this.generateSubTree(x, y, baseAngle, size, variability, split, spread));
        }

        for (TreeSkeleton skeleton : branches){
            super.getInput().merge(skeleton);
        }

        // generate and add stuff to the skeleton

        this.generate(spread, split, branch, variability, branchHeight, size - BASE_SIZE_REDUCTION);
    }

    private TreeSkeleton generateSubTree(float x, float y, float angle, float size, float variability, float split, float spread){
        TreeSkeleton subtree = new TreeSkeleton();

        List<Node> tipNodes = new ArrayList<>(); //Nodes that can currently be appended to (removed after a connection is made)
        List<Float> tipAngles = new ArrayList<>(); //angles that each tip is at (associated by index)

        tipNodes.add(new Node(x, y)); //initial node
        tipAngles.add(angle);

        int height = (int) ((NumericalBase)new NormalDist(2, 1).f()).getReal();

        Function splitRate = t -> { // x is number of branches formed
            double n = ((NumericalBase)t[0]).getReal();

            return new NumericalBase(split)
                    .multiply(new NumericalBase(
                            Math.pow(Math.E, -Math.pow(n - 5, 2))
                    ));
        };

        for (int i = 0; i < height; i++) {
            boolean isSplit = Math.random() < ((NumericalBase)splitRate.f(new NumericalBase(i))).getReal();
            int splitIndex = new Random().nextInt(tipNodes.size()); // used if we are splitting (only one split per "height layer")

            List<Node> tipNodesNew = new ArrayList<>();
            List<Float> tipAnglesNew = new ArrayList<>();

            for (int j = 0; j < tipNodes.size(); j++){
                Node currentNode = tipNodes.get(j);
                NumericalBase currentAngle = new NumericalBase(tipAngles.get(j));

                if (isSplit && j == splitIndex){
                    // perform split

                    NumericalBase angleA = (NumericalBase) new NormalDist(spread * 2 * DEFAULT_ANGLE_SPREAD_NEGATIVE,  variability * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle); // angle of branches from a split
                    NumericalBase angleB = (NumericalBase) new NormalDist(spread * 2 * DEFAULT_ANGLE_SPREAD_POSITIVE,  variability * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle);

                    Branch branchA = super.createBranch(
                            currentNode,
                            (float) angleA.getReal(),
                            size
                    );

                    Branch branchB = super.createBranch(
                            currentNode,
                            (float) angleB.getReal(),
                            size
                    );

                    tipNodesNew.add(branchA.getB());
                    tipNodesNew.add(branchB.getB());

                    tipAnglesNew.add((float) angleA.getReal());
                    tipAnglesNew.add((float) angleB.getReal());

                    subtree.addBranch(branchA);
                    subtree.addBranch(branchB);

                    continue;
                }
                // grow tip node

                NumericalBase a = (NumericalBase) new NormalDist(0, variability * spread * DEFAULT_ANGLE_VARIABILITY_MULTIPLIER).f().add(currentAngle);

                Branch newBranch = super.createBranch(
                        currentNode,
                        (float) a.getReal(),
                        size
                );

                tipNodesNew.add(newBranch.getB());

                tipAnglesNew.add((float) a.getReal());

                subtree.addBranch(newBranch);
            }

            tipNodes = tipNodesNew;
            tipAngles = tipAnglesNew;
        }


        return subtree;
    }
}
