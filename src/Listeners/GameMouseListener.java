package Listeners;

import MainGame.GameScene;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class GameMouseListener extends MouseInputAdapter {
    private GameScene window;

    public GameMouseListener(GameScene window) {
        this.window = window;
    }

    @Override
    public void mouseMoved(MouseEvent e) {//the mouse is moving on the panel
        super.mouseMoved(e);
        this.window.point.setLocation(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {//the mouse is dragging and moving and the panel
        super.mouseDragged(e);
        this.window.point.setLocation(e.getPoint());
    }

    @Override
    public void mouseClicked(MouseEvent e) {//the mouse was clicked
        this.window.player.fire(e.getPoint());//FIRE
    }
}
