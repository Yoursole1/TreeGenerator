package me.yoursole;

import me.yoursole.skeleton.TreeGUI;
import me.yoursole.skeleton.TreeSkeleton;
import me.yoursole.skeleton.generator.Generator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TreeGUI.open();
//        TreeSkeleton skeleton = new Generator(1.5f, 1f, 2, 3, 2).generate();
//        BufferedImage i = skeleton.render();
//        ImageIO.write(i, "jpg", new File("tree.jpg"));
    }
}
