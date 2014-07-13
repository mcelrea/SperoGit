package 
com.mcelrea.gameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Area 
{
	private String title;
	BitmapFont font;
	private Body topWall, bottomWall, leftWall, rightWall;
	TiledMap map;

	public Area(String title, World world)
	{
		this.title = title;
		font = new BitmapFont();

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();


		//top wall - bounding box of area
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0,0);
		ChainShape shape = new ChainShape();
		shape.createChain(new Vector2[]{new Vector2(-38,22), new Vector2(38,22)});
		fixDef.shape = shape;
		topWall = world.createBody(bodyDef);
		topWall.createFixture(fixDef);
		topWall.getFixtureList().get(0).setUserData("top wall");

		//bottom wall - bounding box of area
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0,0);
		shape = new ChainShape();
		shape.createChain(new Vector2[]{new Vector2(-38,-22), new Vector2(38,-22)});
		fixDef.shape = shape;
		bottomWall = world.createBody(bodyDef);
		bottomWall.createFixture(fixDef);
		bottomWall.getFixtureList().get(0).setUserData("bottom wall");

		//left wall - bounding box of area
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0,0);
		shape = new ChainShape();
		shape.createChain(new Vector2[]{new Vector2(-38.5f,22), new Vector2(-38.5f,-22)});
		fixDef.shape = shape;
		leftWall = world.createBody(bodyDef);
		leftWall.createFixture(fixDef);
		leftWall.getFixtureList().get(0).setUserData("left wall");

		//right wall - bounding box of area
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0,0);
		shape = new ChainShape();
		shape.createChain(new Vector2[]{new Vector2(38.5f,22), new Vector2(38.5f,-22)});
		fixDef.shape = shape;
		rightWall = world.createBody(bodyDef);
		rightWall.createFixture(fixDef);
		rightWall.getFixtureList().get(0).setUserData("right wall");
		
		map = new TmxMapLoader().load("data/testmap.tmx");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public TiledMap getTiledMap()
	{
		return map;
	}
	
	public void setTiledMap(String file)
	{
		map = new TmxMapLoader().load(file);
	}

	public void draw(SpriteBatch batch)
	{	
		Matrix4 normalProjection = new Matrix4().setToOrtho2D(0, 0, 1920,1080);
		batch.setProjectionMatrix(normalProjection);
		batch.begin();
		font.draw(batch, title, 100, 1000);
		font.draw(batch, "(" + Gdx.input.getX() + "," + Gdx.input.getY() + ")", 100, 900);
		batch.end();
	}
}
