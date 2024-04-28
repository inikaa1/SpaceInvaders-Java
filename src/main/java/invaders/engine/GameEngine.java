package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.ConfigReader;
import invaders.Singleton.DifficultyLevel;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.prototype.Prototype;
import invaders.rendering.Renderable;
import org.json.simple.JSONObject;

import javax.management.relation.RelationNotFoundException;

/**
 * This class manages the main loop and logic of the game
 *
 * the only changes made to this class is the following:
 * new constructor for game engine
 * new clone method added for prototype function
 * original constructor now takes in difficulty level
 * parse statement changed for game engine to parse the correct config file based on difficulty level.
 */

public class GameEngine implements Prototype{
	public GameEngine(List<GameObject> gameObjects, List<GameObject> pendingToAddGameObject, List<GameObject> pendingToRemoveGameObject, List<Renderable> pendingToAddRenderable, List<Renderable> pendingToRemoveRenderable, List<Renderable> renderables, Player player, boolean left, boolean right, int gameWidth, int gameHeight) {
		this.gameObjects = gameObjects;
		this.pendingToAddGameObject = pendingToAddGameObject;
		this.pendingToRemoveGameObject = pendingToRemoveGameObject;
		this.pendingToAddRenderable = pendingToAddRenderable;
		this.pendingToRemoveRenderable = pendingToRemoveRenderable;
		this.renderables = renderables;
		this.player = player;
		this.left = left;
		this.right = right;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}

	private List<GameObject> gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject = new ArrayList<>();
	private List<GameObject> pendingToRemoveGameObject = new ArrayList<>();

	private List<Renderable> pendingToAddRenderable = new ArrayList<>();
	private List<Renderable> pendingToRemoveRenderable = new ArrayList<>();

	private List<Renderable> renderables =  new ArrayList<>();

	private Player player;

	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;

	public Prototype clone(){
		List<GameObject> gameObjects = new ArrayList<>();
		for(GameObject go : this.gameObjects) {
			gameObjects.add((GameObject) go.clone());
		}

		List<GameObject> pendingToAddGameObject = new ArrayList<>();
		for(GameObject ptago : this.pendingToAddGameObject) {
			pendingToAddGameObject.add((GameObject) ptago.clone());
		}

		List<GameObject> pendingToRemoveGameObject = new ArrayList<>();
		for(GameObject ptrgo : this.pendingToRemoveGameObject) {
			pendingToRemoveGameObject.add((GameObject) ptrgo.clone());
		}

		List<Renderable> pendingToAddRenderable = new ArrayList<>();
		for(Renderable ptar : this.pendingToAddRenderable) {
			pendingToAddRenderable.add((Renderable) ptar.clone());
		}

		List<Renderable> pendingToRemoveRenderable = new ArrayList<>();
		for(Renderable ptrr : this.pendingToRemoveRenderable) {
			pendingToRemoveRenderable.add((Renderable) ptrr.clone());
		}

		List<Renderable> renderables = new ArrayList<>();
		for(Renderable r : this.renderables){
			renderables.add((Renderable) r.clone());
		}

		Player player = (Player) this.player.clone();

		boolean left = this.left;
		boolean right = this.right;
		int gameWidth = this.gameWidth;
		int gameHeight = this.gameHeight;

		return new GameEngine(gameObjects, pendingToAddGameObject, pendingToRemoveGameObject, pendingToAddRenderable, pendingToRemoveRenderable, renderables, player, left, right, gameWidth, gameHeight);
	}

	private DifficultyLevel difficultyLevel;

	public GameEngine(DifficultyLevel difficultyLevel){
		// Read the config here
		this.difficultyLevel = difficultyLevel;
		ConfigReader.parse(difficultyLevel.getConfigFileName());

		// Get game width and height
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);


		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}


		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			gameObjects.add(enemy);
			renderables.add(enemy);
		}

	}
	/**
	 * Updates the game/simulation
	 */

	public void update(){
		timer+=1;

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}

		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
					}
				}
			}
		}


		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}

	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}
}
