package entities;

import gamestates.Playing;
import main.Game;
import utilz.Constants;
import utilz.HelpMethods;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;
import static utilz.Constants.MonsterConstants.*;

public class Enemy extends Entity{
    private BufferedImage[][] animations;
    public int monsterAction = Constants.MonsterConstants.IDLE;
    private int aniTick, aniIndex, aniSpeed = 25;
    private boolean moving = false, attacking = false;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 30 * Game.SCALE;
    private float speed = 0.7f * Game.SCALE;




    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
        initHitbox(x, y, (int) (20 * Game.SCALE), (int) (22 * Game.SCALE));
        loadAnimations();
    }

    private void loadAnimations() {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MONSTER);

        animations = new BufferedImage[5][12];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 64, 64, 64);

    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmountMonster(monsterAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = monsterAction;

        if (moving)
            monsterAction = RUNNING;
        else
            monsterAction = IDLE;

        if (attacking)
            monsterAction = ATTACKING;

        if (startAni != monsterAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void ChasePlayer(Player p)
    {
        if(p.getHitbox().x < this.getHitbox().x)
            this.getHitbox().x -= 0.01;
        else this.getHitbox().x += 0.01;
    }

    public boolean isPlayerNearby(Player p){
        if(abs(p.x - this.x) <288 && abs(p.y - this.y) <50){
            monsterAction = ATTACKING;
            return true;
        }
        else {
            monsterAction = IDLE;
            return false;
        }
    }


    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void update() {
//        updatePos();
        updateAnimationTick();
        setAnimation();
    }

//	public void render(Graphics g) {
//		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
////		drawHitbox(g);
//	}

    public void render(Graphics g,int x, int y,int tx, int ty) {
//        if(left || lastDirLeft)
//            g.drawImage(HelpMethods.mirrorImage(animations[playerAction][aniIndex]), (int)(Game.GAME_WIDTH/2 - this.xDrawOffset), (int)(Game.GAME_HEIGHT/2 - this.yDrawOffset), width, height, null);
//        else
            g.drawImage(animations[monsterAction][aniIndex], calculateOffsetX((int)this.getHitbox().x,tx), calculateOffsetY((int)this.getHitbox().y,ty)+Game.TILES_SIZE/4, width, height, null);

//		drawHitbox(g);
    }

    private int calculateOffsetX(int x, int tx) {
        return (int) (x+((Game.GAME_WIDTH / 2) - tx));
    }

    private int calculateOffsetY(int y, int ty) {
        return (int) (y+((Game.GAME_HEIGHT / 2) - ty));
    }


}
