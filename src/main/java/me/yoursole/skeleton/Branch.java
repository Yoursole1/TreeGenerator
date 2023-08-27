package me.yoursole.skeleton;

public class Branch {

    //the branch grows from 'a' to 'b'
    private final Node a;
    private final Node b;
    private final float size;


    public Branch(Node a, Node b, float size) {
        this.a = a;
        this.b = b;
        this.size = size;
    }


    public float getSize() {
        return size;
    }

    public Node getB() {
        return b;
    }

    public Node getA() {
        return a;
    }
}
