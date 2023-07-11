package me.yoursole;

import me.yoursole.skeleton.TreeSkeleton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        TreeSkeleton skeleton = TreeSkeleton.generateTree(0.1f, 0.6f, 1, 1, 1);
        BufferedImage i = skeleton.render();
        ImageIO.write(i, "jpg", new File("tree.jpg"));
    }
}
