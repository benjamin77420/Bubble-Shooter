package MainGame;

import Consts.Consts;
import Listeners.GameMouseListener;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame{
    /*
        implementing the double buffer design
     */

    public static void main(String[] args) {
        new Game();
    }



    public Game() {
        this.setTitle("Daniel's && Benjamin's game");
        this.setVisible(true);
        this.setSize(Consts.WINDOW_WIDTH,Consts.WINDOW_HIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);



        GameScene gameScene = new GameScene();

        gameScene.setBounds(-(int)(Consts.BORDER_WIDTH*2),-Consts.WINDOW_HEADER, Consts.WINDOW_WIDTH, Consts.WINDOW_HIGHT);//-(int)(Consts.BORDER_WIDTH*2),-Consts.WINDOW_HEADER is to compensate for the passing of the panel

        GameMouseListener mouseListener = new GameMouseListener(gameScene);

        this.add(gameScene);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

}
