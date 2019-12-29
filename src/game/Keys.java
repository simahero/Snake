package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class Keys implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == VK_UP){
            if (Driver.movement != 1 && Driver.movement != 3) {
                Driver.movement = 1;
                System.out.println("UP");
            }
        }
        if (e.getKeyCode() == VK_RIGHT){
            if (Driver.movement != 2 && Driver.movement != 4) {
                Driver.movement = 2;
                System.out.println("RIGHT");
            }

        }
        if (e.getKeyCode() == VK_DOWN){
            if (Driver.movement != 1 && Driver.movement != 3) {
                Driver.movement = 3;
                System.out.println("DOWN");
            }

        }
        if (e.getKeyCode() == VK_LEFT){
            if (Driver.movement != 2 && Driver.movement != 4) {
                Driver.movement = 4;
                System.out.println("LEFT");
            }

        }
        if (e.getKeyCode() == VK_ENTER){
            Driver.init();
            Driver.running = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}