package com.mcelrea.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mcelrea.contacts.DPad;
import com.mcelrea.contacts.MyContactFilter;
import com.mcelrea.gameWorld.Sector;
import com.mcelrea.player.Player;

public class GamePlay implements Screen{
	
	World world;
	Box2DDebugRenderer debugRenderer;
	OrthographicCamera camera;
	SpriteBatch batch;
	public static float pitch;
	
	private final float TIMESTEP = 1 / 60f; //1/60th of a second, 60 FPS
	private final int VELOCITYITERATIONS = 8; //pretty common, makes the world stable
	private final int POSITIONITERATIONS = 3; //pretty common, makes the world stable
	float zoomlevel = 1;
	
	
	public static Player player;
	public static boolean hittingLeftWall, hittingRightWall, hittingTopWall, hittingBottomWall;
	Sector overWorld;
	DPad dpad;
	TiledMapRenderer tileMapRenderer;
	TiledMap map;
	Matrix4 copyCamera;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		camera.update();
		tileMapRenderer.setView(copyCamera, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		tileMapRenderer.render();
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
		//has it's own batch begin/end
		player.getCurrentArea().draw(batch);
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE))
		{
			Gdx.app.exit();
		}
		
		zoom();
		debugRenderer.render(world, camera.combined);
		updatePlayer();
	}

	@Override
	public void resize(int width, int height) {
		//camera.viewportWidth = width/25;
		//camera.viewportHeight = height/25;
		
		Vector3 temp = camera.position.cpy();
		camera.setToOrtho(false, width, height);
		camera.position.set(temp);
	}

	@Override
	public void show() {
		
		world = new World(new Vector2(0,0), true);
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		
		
		//for 1080p
		if(Gdx.graphics.getHeight() == 1080)
			camera.zoom = 0.04f;
		if(Gdx.graphics.getHeight() == 720)
			camera.zoom = 0.06f;
		
		
		batch = new SpriteBatch();
		world.setContactFilter(new MyContactFilter());
		
		overWorld = new Sector(10,10,2);
		overWorld.fillAreasWithTitles(world);
		player = new Player(world, overWorld);
		
		dpad = new DPad(world);
		map = new TmxMapLoader().load("data/testmap.tmx");
		tileMapRenderer = new OrthogonalTiledMapRenderer(map);
		camera.update();
		copyCamera = camera.combined.cpy().scl(camera.zoom).translate(-Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2, 0);
	}
	
	public void zoom()
	{
		if(Gdx.input.isKeyPressed(Keys.UP))
		{
			camera.zoom -= 0.01;
			System.out.println(camera.zoom);
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			camera.zoom += 0.01;
			System.out.println(camera.zoom);
		}
	}
	
	public void updatePlayer()
	{
		movePlayer();
		if(hittingBottomWall)
		{
			player.setCurrentLocation(player.getRow()+1, player.getCol(), player.getDepth(), player.getBody().getPosition().x, 20);
			hittingBottomWall = false;
		}
		if(hittingTopWall)
		{
			player.setCurrentLocation(player.getRow()-1, player.getCol(), player.getDepth(), player.getBody().getPosition().x, -20);
			hittingTopWall = false;
		}
		if(hittingRightWall)
		{
			player.setCurrentLocation(player.getRow(), player.getCol()+1, player.getDepth(), -36, player.getBody().getPosition().y);
			hittingRightWall = false;
		}
		if(hittingLeftWall)
		{
			player.setCurrentLocation(player.getRow(), player.getCol()-1, player.getDepth(), 36, player.getBody().getPosition().y);
			hittingLeftWall = false;
		}
		
	}
	
	public void movePlayer()
	{
		if(Gdx.input.isTouched() || Gdx.input.isButtonPressed(Buttons.LEFT))
		{
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			
			Vector3 loc = new Vector3(x,y,0);
			loc = camera.unproject(loc);
			
			if(loc.x < -5 && Gdx.input.justTouched())
			{
				dpad.adjustLocation(loc.x, loc.y);
			}
			
			if(dpad.leftPressed(loc.x, loc.y))
			{
				player.moveLeft();
			}
			else if(dpad.rightPressed(loc.x, loc.y))
			{
				player.moveRight();
			}
			else if(dpad.upPressed(loc.x, loc.y))
			{
				player.moveUp();
			}
			else if(dpad.downPressed(loc.x, loc.y))
			{
				player.moveDown();
			}
			else
			{
				player.noMovement();
			}
		}
		
		else
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
			else if(Gdx.input.isKeyPressed(Keys.S))// || Gdx.input.isTouched())
			{
				player.moveDown();
			}
			else
			{
				player.noMovement();
			}
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
		map.dispose();
	}

}
