package me.yoursole;

import me.yoursole.skeleton.TreeSkeleton;
import me.yoursole.skeleton.generator.Generator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        TreeSkeleton skeleton = new Generator(1f, 1f, 1, 1, 1).generate();
        BufferedImage i = skeleton.render();
        ImageIO.write(i, "jpg", new File("tree.jpg"));
    }
}
