package me.yoursole.skeleton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class TreeSkeleton {

    private final List<Branch> branches;

    public TreeSkeleton(){
        this.branches = new ArrayList<>();
    }

    public void addBranch(Branch branch){
        this.branches.add(branch);
    }

    public List<Branch> getBranches(){
        return this.branches;
    }

    public int getSize(){
        return this.branches.size();
    }

    public void merge(TreeSkeleton other){
        this.branches.addAll(other.branches);
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
