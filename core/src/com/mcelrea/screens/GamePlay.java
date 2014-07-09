package com.mcelrea.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mcelrea.contacts.MyContactFilter;
import com.mcelrea.gameWorld.Sector;
import com.mcelrea.player.Player;

public class GamePlay implements Screen{
	
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	SpriteBatch batch;
	
	private final float TIMESTEP = 1 / 60f; //1/60th of a second, 60 FPS
	private final int VELOCITYITERATIONS = 8; //pretty common, makes the world stable
	private final int POSITIONITERATIONS = 3; //pretty common, makes the world stable
	
	
	public static Player player;
	public static boolean hittingLeftWall, hittingRightWall, hittingTopWall, hittingBottomWall;
	Sector overWorld;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		camera.update();
		debugRenderer.render(world, camera.combined);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
		
		//has it's own batch begin/end
		player.getCurrentArea().draw(batch);
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		
		updatePlayer();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/25;
		camera.viewportHeight = height/25;
	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		world.setContactFilter(new MyContactFilter());
		
		overWorld = new Sector(10,10,2);
		overWorld.fillAreasWithTitles(world);
		player = new Player(world, overWorld);
	}
	
	public void updatePlayer()
	{
		movePlayer();
	}
	
	public void movePlayer()
	{
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			player.moveLeft();
		}
		else if(Gdx.input.isKeyPressed(Keys.D))
		{
			player.moveRight();
		}
		else if(Gdx.input.isKeyPressed(Keys.W))
		{
			player.moveUp();
		}
		else if(Gdx.input.isKeyPressed(Keys.S))
		{
			player.moveDown();
		}
		else
		{
			player.noMovement();
		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
