/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazesolver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import mazesolver.MazeSolver;
import java.awt.event.*;

/**
 *
 * @author Himanshu
 */
//shows the path find by algorithm
public class Gui extends JPanel {

    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    MazeSolver Maze;

    private BufferedImage image, mouseL, mouseR;
    int x_part;
    int y_part;
    int x_step;
    int y_step;
    int path_color;
    int grid = 20;      //size of Maze
    int sourcex = 0;    //x coordinate of source
    int sourcey = 0;    //y coordinate of source
    int destinationx = 19;      //x coordinate of destination
    int destinationy = 19;      //y coordinate of destination
    int buffer;
    static int path_size = 0, wall_size = 0, x_move = 0, y_move = 0;

    //resize the image
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public Gui() {
        try {

            Maze = new MazeSolver(grid);
            Maze.intialize(destinationy, destinationx);
            image = ImageIO.read(new File(".\\Images\\maze-20x20.png"));
            if (image.getHeight() < 700 && image.getWidth() < 700) {
                image = resize(image, 700, 700);
            }
            mouseL = ImageIO.read(new File(".\\Images\\mouseL.jpg"));
            mouseR = ImageIO.read(new File(".\\Images\\mouseR.jpg"));
            x_part = image.getWidth();
            y_part = image.getHeight();
            x_step = x_part / grid;
            y_step = y_part / grid;
            mouseL = resize(mouseL, x_step - 15, y_step - 15);
            mouseR = resize(mouseR, x_step - 15, y_step - 15);
            //int j,max=0,index=0;
            buffer = (int) (x_step * 0.1);
            path_color = image.getRGB(x_step / 2, y_step / 2);
            System.out.println(path_color);
            findwall();
            Maze.path(Maze, sourcex, sourcey);

        } catch (IOException ex) {
            // handle exception...
            System.out.println("could not found image");
        }
    }

    //find the wall around every cell of grid
    public void findwall() {
        int i = 0;
        int y = 0;
        int wall_color, wall_color1, wall_color2;
        int a, b, k;
        for (k = 0; k < grid; k++) {
            for (b = 0; b < grid; b++) {

                wall_color = image.getRGB(i, y + y_step / 2);
                wall_color1 = image.getRGB(i + buffer, y + y_step / 2);
                if ((i - buffer) > 0) {
                    wall_color2 = image.getRGB(i - buffer, y + y_step / 2);
                } else {
                    wall_color2 = wall_color;
                }
                if (wall_color != path_color | wall_color1 != path_color | wall_color2 != path_color) {

                    Maze.MBlockLW[k][b] = true;
                }
                if (i + x_step < x_part) {
                    wall_color = image.getRGB(i + x_step, y + y_step / 2);
                    if ((i + x_step + buffer) < x_part) {
                        wall_color1 = image.getRGB(i + x_step + buffer, y + y_step / 2);
                    } else {
                        wall_color1 = wall_color;
                    }
                    wall_color2 = image.getRGB(i + x_step - buffer, y + y_step / 2);
                    if (wall_color != path_color | wall_color1 != path_color | wall_color2 != path_color) {

                        Maze.MBlockRW[k][b] = true;
                    }
                }
                wall_color = image.getRGB(i + x_step / 2, y);
                wall_color1 = image.getRGB(i + x_step / 2, y + buffer);
                if ((y - buffer) > 0) {
                    wall_color2 = image.getRGB(i + x_step / 2, y - buffer);
                } else {
                    wall_color2 = wall_color;
                }
                if (wall_color != path_color | wall_color1 != path_color | wall_color2 != path_color) {

                    Maze.MBlockUW[k][b] = true;
                }
                if (y + y_step < y_part) {
                    wall_color = image.getRGB(i + x_step / 2, y + y_step);
                    if ((y + y_step + buffer) < y_part) {
                        wall_color1 = image.getRGB(i + x_step / 2, y + y_step + buffer);
                    } else {
                        wall_color1 = wall_color;
                    }
                    wall_color2 = image.getRGB(i + x_step / 2, y + y_step - buffer);
                    if (wall_color != path_color | wall_color1 != path_color | wall_color2 != path_color) {

                        Maze.MBlockDW[k][b] = true;
                    }
                }
                i += x_step;
            }
            i = 0;
            y += y_step;
        }

        for (k = 0; k < x_step / 5; k++) {
            if (image.getRGB(k, y_part / 2) != path_color) {
                wall_size++;
            }
        }
        x_move = wall_size + x_step * sourcey;
        y_move = wall_size + y_step * sourcex;
    }
    
    //move jarry from source to destination
    public void paint(Graphics g) {

        int count = 2;
        g.drawImage(image, 0, 0, this);
        int x = image.getWidth();
        int y = image.getHeight();
        int i = 0, j = 0;
        g.setColor(Color.red);
        System.out.println(path_size + "  path_size");
        try {
            if (Maze.pathy.get(path_size + 1) > Maze.pathy.get(path_size)) {
                g.drawImage(mouseR, x_move, y_move, this);
            } else {
                g.drawImage(mouseL, x_move, y_move, this);
            }
        } catch (Exception e) {
            if (Maze.pathy.get(path_size - 1) > Maze.pathy.get(path_size - 2)) {
                g.drawImage(mouseR, x_move, y_move, this);
            } else {
                g.drawImage(mouseL, x_move, y_move, this);
            }
        }
        if (path_size < Maze.pathx.size() - 1) {

            if (Maze.pathy.get(path_size + 1) > Maze.pathy.get(path_size) && Maze.pathy.get(path_size + 1) * x_step + wall_size != x_move) {
                x_move++;
            } else if (Maze.pathy.get(path_size + 1) < Maze.pathy.get(path_size) && Maze.pathy.get(path_size + 1) * x_step + wall_size != x_move) {
                x_move--;
            }

            if (Maze.pathx.get(path_size + 1) < Maze.pathx.get(path_size) && Maze.pathx.get(path_size + 1) * x_step + wall_size != y_move) {
                y_move--;
            } else if (Maze.pathx.get(path_size + 1) > Maze.pathx.get(path_size) && Maze.pathx.get(path_size + 1) * x_step + wall_size != y_move) {
                y_move++;
            }
            if (Maze.pathy.get(path_size + 1) * (x_step) + wall_size == x_move && Maze.pathx.get(path_size + 1) * (x_step) + wall_size == y_move) {
                path_size++;
            }
            try {
                // to sleep 5 seconds
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            repaint();

        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Gui m = new Gui();
        JFrame f = new JFrame();

        f.add(m);
        f.setSize(800, 800);
        f.setVisible(true);

    }

}
