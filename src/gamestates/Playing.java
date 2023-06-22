package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.Enemy;
import entities.Player;
import levels.LevelManager;
import main.Game;
import utilz.LoadSave;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	public static Enemy[] enemy = null;

	public Playing(Game game, int level) {
		super(game);
		initClasses(level);
	}

	public void changeLevel(int level)
	{
		game.setPlaying(level);
	}

	private void initClasses(int Level) {
		try {
			if(Level == 1) {
				Statement st = Game.c.createStatement();
				String sql = "SELECT * FROM JOC;";
				ResultSet rs = st.executeQuery(sql);
				int level = rs.getInt("LEVEL");
				if (level == 0) {
					level = 1;
				}
				int x = rs.getInt("X");
				int y = rs.getInt("y");
				levelManager = new LevelManager(game, level);
				player = new Player(x, y, (int) (56 * Game.SCALE), (int) (56 * Game.SCALE));

				LevelManager.Level = Level;
				player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
				st.close();
			}
			else if (Level == 2)
			{
				levelManager = new LevelManager(game,Level);
				player = new Player(200, 200, (int) (56 * Game.SCALE), (int) (56 * Game.SCALE));
				player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
				LevelManager.Level = Level;

			}

		} catch (SQLException e) {
			levelManager = new LevelManager(game,Level);
			player = new Player(2*Game.TILES_SIZE, 2*Game.TILES_SIZE, (int) (56 * Game.SCALE), (int) (56 * Game.SCALE));
			player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
			LevelManager.Level = Level;
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		if(Playing.enemy != null) {
			for (Enemy ee : Playing.enemy) {
				if(ee != null)
					ee.update();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g,player,enemy);
		player.render(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state = Gamestate.MENU;
			break;
		case KeyEvent.VK_ENTER:
			player.debugPos();
			break;
		case KeyEvent.VK_F2:
			LoadSave.SaveGame((int)player.getHitbox().x,(int)player.getHitbox().y, LevelManager.Level);
			break;
		case KeyEvent.VK_R:
			player.setAttacking(true);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}
