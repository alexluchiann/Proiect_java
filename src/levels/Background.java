package levels;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Background {

    private BufferedImage image;
    private double x;//pozitia pe axa X a fundalului
    private double y;//pozitia pe axa Y a fundalului
    private double dx;
    private double dy;

    private double moveScale;

    public Background(String s, double ms) {
        try {
            moveScale = ms;
            image = ImageIO.read(new File(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % Game.GAME_WIDTH;
        this.y = (y * moveScale / 32) % Game.GAME_HEIGHT;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
        /*
        x += dx;
        while(x <= -Game.GAME_WIDTH) x += Game.GAME_WIDTH;
        while(x >= Game.GAME_WIDTH) x -= Game.GAME_WIDTH;
        y += dy;
        while(y <= -Game.GAME_HEIGHT) y += Game.GAME_HEIGHT;
        while(y >= Game.GAME_HEIGHT) y -= Game.GAME_HEIGHT;

         */
    }

    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        if (x < 0) {
            g.drawImage(image, (int)x + Game.GAME_WIDTH, (int) y,Game.GAME_WIDTH,Game.GAME_HEIGHT, null);
        }
        if (x > 0) {
            g.drawImage(image, (int)x - Game.GAME_HEIGHT, (int) y,Game.GAME_WIDTH,Game.GAME_HEIGHT, null);
        }
        /*if(y < 0) {
            g.drawImage(image, (int)x, (int)y + GamePanel.HEIGHT, null);
        }
        if(y > 0) {
            g.drawImage(image, (int)x, (int)y - GamePanel.HEIGHT, null);
        }
        */
    }
}
