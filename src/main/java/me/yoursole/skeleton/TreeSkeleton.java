package me.yoursole.skeleton;

import me.yoursole.math.numerical.complex.NumericalBase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TreeSkeleton {

    private final List<Branch> branches;

    public TreeSkeleton(){
        this.branches = new ArrayList<>();
    }

    public void addBranch(Branch branch){
        this.branches.add(branch);
    }

    public int getSize(){
        return this.branches.size();
    }

    public BufferedImage render(){
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        int white = Color.WHITE.getRGB();
        int black = Color.BLACK.getRGB();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                if(this.isBlack(x, y)) {
                    image.setRGB(x, y, black);
                    continue;
                }
                image.setRGB(x, y, white);
            }
        }
        return image;
    }



    /**
     * All parameters MUST be between 0 and 1
     * @param spread height to width ratio of the tree
     * @param split rate that branches split into more branches at nodes
     * @param branch rate that branches connect to other branches in between nodes
     * @param pull pull > 0 means the tree mostly branches up, pull < 0 means it branches down
     * @param branchHeight ratio up the tree the branches start at
     * @return a TreeSkeleton with the desired parameters
     */
    public static TreeSkeleton generateTree(float spread, float split, float branch, float pull, float branchHeight){
        //todo validate that all params are between 0 and 1

        TreeSkeleton skeleton = new TreeSkeleton();

        /*
        Stages:
            - Generate trunk
            - Generate branches, decreasing size each iteration

        Rules:
            - trees generate in a 100 x 100 box, and should be either/both 100 wide or/and high (as large as it can be)
            - split branches are approx 'parent size / amount of split' sized and are facing in mostly the same direction as the parent
            - branching branches are not larger than the parent, and at least 1/2 of the parent size (bell curve)

        Notes:

        A higher spread should make a larger trunk, because it will need to spread out more
         */

        // trunk generator
        List<Node> tipNodes = new ArrayList<>(); //Nodes that can currently be appended to (removed after a connection is made)
        List<Float> tipAngles = new ArrayList<>(); //angles that each tip is at (associated by index)

        tipNodes.add(new Node(50, 100)); //initial node
        tipAngles.add(0f);

        int trunkSize = Math.abs((int) (TreeSkeleton.sampleNormal(15, 2, 15))); //height of trunk (# nodes from base to a tip)

        for (int i = 0; i < trunkSize; i++) { // height of tree
            for (int j = 0; j < tipNodes.size(); j++) { // grow each tip

                //TODO select if a split will happen here

                float addition = TreeSkeleton.sampleNormal(0, 10 * spread * TreeSkeleton.sigmoid(skeleton.getSize()) - 5, 30); //todo maybe bias mean to one side
                tipAngles.set(j, tipAngles.get(j) + addition); // increment the angle of the branch


                NumericalBase starting = new NumericalBase(0, -5);
                starting = starting.rotate(tipAngles.get(j) * Math.PI / 180); // convert to radians

                //----------
                starting = (NumericalBase) starting.add(new NumericalBase(tipNodes.get(0).x, tipNodes.get(0).y));

                Node newNode = new Node((float) starting.getReal(), (float) starting.getImaginary());
                skeleton.addBranch(new Branch(tipNodes.get(j), newNode, 1));
                tipNodes.set(j, newNode);


            }
        }

        // main tree structure is composed of Branches with length 5
        return skeleton;
    }

    private static float sampleNormal(float mean, float std, float max){ //max should be like 30 for splits (guess)
        float value = 0; // odds of 0 being selected are '0', and we don't want 0 anyways

        while (value == 0 || Math.abs(value) > max){
            value = (float) (new Random().nextGaussian() * std + mean);
        }

        return value;
    }


    private static float sigmoid(float input){
        double free = Math.pow(Math.E, -input);
        free += 1;
        free = 1 / free;
        return (float) free;
    }

    private boolean isBlack(int x, int y){

        for(Branch e : this.branches){
            if(this.isValid(new Node(x, y), e, e.getSize())){
                return true;
            }
        }
        return false;
    }

    private boolean isValid(Node p, Branch e, float edgeSize) {
        if(p.distance(e.getA()) < edgeSize || p.distance(e.getB()) < edgeSize){
            return true;
        }

        double distance = pointLineDistance(p, e.getA(), e.getB());
        if(distance > edgeSize) {
            return false;
        }

        Node start = e.getA();
        Node end = e.getB();
        return (p.x >= Math.min(start.x, end.x) - edgeSize && p.x <= Math.max(start.x, end.x) + edgeSize) &&
                (p.y >= Math.min(start.y, end.y) - edgeSize && p.y <= Math.max(start.y, end.y) + edgeSize);
    }

    private double pointLineDistance(Node p, Node a, Node b) {
        if (a.x == b.x) {
            return Math.abs(p.x - a.x);
        } else if (a.y == b.y) {
            return Math.abs(p.y - a.y);
        } else {
            double xDiff = b.x - a.x;
            double yDiff = b.y - a.y;
            double num = Math.abs(yDiff*p.x - xDiff*p.y + b.x*a.y - b.y*a.x);
            double den = Math.sqrt(yDiff*yDiff + xDiff*xDiff);
            return num/den;
        }
    }


}
