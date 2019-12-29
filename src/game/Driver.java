package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class Driver implements Runnable {

    int w = 640;
    int scl = 640 / 20;

    static int headX = 10;
    static int headY = 10;

    static int movement = 0;

    JFrame frame;
    static Canvas canvas;
    static BasicTimer timer  = new BasicTimer(10);;

    static Cell[][] cells = new Cell[20][19];
    static ArrayList<Cell> path = new ArrayList<>();
    private boolean hit = false;
    static boolean running = true;

    BufferedImage img = ImageIO.read(new File("src/asset/go.png"));

    public Driver() throws IOException {
        frame = new JFrame("SNAKE GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas = new Canvas());
        canvas.addKeyListener(new Keys());
        frame.setSize(w + 3, w + 7);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(this).start();

    }

    public static void getApple() {
        Random r = new Random();
        int x = (int) Math.floor(r.nextInt(19));
        int y = (int) Math.floor(r.nextInt(19));
        if (cells[x][y].snake) {
            getApple();
        } else {
            cells[x][y].apple = true;
        }
    }

    public static void addToPath(Cell c) {
        path.add(c);
        c.snake = true;
    }

    public void removeFromPath(Cell c) {
        path.remove(c);
        c.snake = false;
    }

    private void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(51, 51, 51));
        if (!running) {
            g.drawImage(img, 0, 0, null);
            g.dispose();
            bs.show();
        } else {
            g.fillRect(0, 0, w + 1, w);
            g.setColor(Color.WHITE);
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j].snake) {
                        g.setColor(Color.WHITE);
                        g.fillRect(cells[i][j].x * scl, cells[i][j].y * scl, scl, scl);
                    }
                    if (cells[i][j].apple) {
                        g.setColor(Color.RED);
                        g.fillRect(cells[i][j].x * scl, cells[i][j].y * scl, scl, scl);
                    }
                }
            }
            g.setColor(Color.WHITE);
            for (int i = 0; i < w / scl; i++) {
                g.drawLine(i * scl, 0, i * scl, w);
            }
            for (int j = 0; j < w / scl; j++) {
                g.drawLine(0, j * scl, w, j * scl);
            }
            g.dispose();
            bs.show();
        }
    }

    public void updare() {
        switch (movement) {
            case 1:
                headY -= 1;
                break;
            case 2:
                headX += 1;
                break;
            case 3:
                headY += 1;
                break;
            case 4:
                headX -= 1;
                break;
        }
        if (headX > 21 || headX < 0 || headY < 0 || headY > 20) {
            running = false;
        }
        for (int i = 0; i < path.size() - 1; i++) {
            if (path.get(i).x == headX && path.get(i).y == headY) {
                running = false;
            }
        }
        try {
            if (cells[headX][headY].apple) {
                hit = true;
                addToPath(cells[headX][headY]);
                cells[headX][headY].apple = false;
            }
            if (hit) {
                getApple();
                hit = false;
            }
            addToPath(cells[headX][headY]);
            removeFromPath(path.get(0));

        } catch (ArrayIndexOutOfBoundsException e) {
            running = false;
        }
    }

    public static void init() {
        cells = new Cell[20][19];
        path.clear();
        headX = 10;
        headY = 10;
        movement = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(false, false, i, j);
            }
        }
        addToPath(cells[headX - 2][headY]);
        addToPath(cells[headX - 1][headY]);
        addToPath(cells[headX][headY]);
        getApple();
    }

    @Override
    public void run() {
        init();
        while (true) {
            timer.sync();
            if (movement != 0) {
                updare();
            }
            render();
        }

    }

    public static void main(String[] args) throws IOException {
        new Driver();

    }
}