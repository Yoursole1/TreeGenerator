package me.yoursole.skeleton;

public class Branch {

    //the branch grows from 'a' to 'b'
    private final Node a;
    private final Node b;
    private final float size;

    private final float angle; //in degrees


    public Branch(Node a, Node b, float size, float angle) {
        this.a = a;
        this.b = b;
        this.size = size;
        this.angle = angle;
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

    public float getAngle() {
        return this.angle;
    }
}
