package me.yoursole.skeleton;


public final class Node {

    public final float x;
    public final float y;

    Node(float x, float y) {
//        if(x > 100 || y > 100 || x < 0 || y < 0){
//            throw new IllegalArgumentException("Nodes must be between 0 and 100");
//        }
        this.x = x;
        this.y = y;
    }

    public double distance(Node other){
        return Math.sqrt(Math.pow((double)x - (double)other.x, 2) + Math.pow((double)y - (double)other.y, 2));
    }
}
