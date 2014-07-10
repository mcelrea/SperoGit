package com.mcelrea.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mcelrea.gameWorld.Area;
import com.mcelrea.gameWorld.Sector;

public class Player 
{
	private Body body;
	private float xvel, yvel;
	private float speed;
	private Sector currentSector;
	private int row, col, depth;
	
	public Player(World world, Sector sector)
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0,0);
		PolygonShape box = new PolygonShape();
		box.setAsBox(1f, 1f);
		fixDef.shape = box;
		body = world.createBody(bodyDef);
		body.createFixture(fixDef);
		body.getFixtureList().get(0).setUserData(this);
		
		speed = 1000;
		row = 0;
		col = 0;
		depth = 0;
		currentSector = sector;
	}
	
	public Area getCurrentArea()
	{
		return currentSector.getArea(row, col, depth);
	}
	
	public Sector getCurrentSector() {
		return currentSector;
	}

	public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}
	
	public void setCurrentLocation(int row, int col, int depth, float x, float y)
	{
		this.row = row;
		this.col = col;
		this.depth = depth;
		body.setTransform(x, y, 0);
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void moveUp()
	{
		yvel = Gdx.app.getGraphics().getDeltaTime() * speed;
		body.setLinearVelocity(0, yvel);
	}
	
	public void moveDown()
	{
		yvel = Gdx.app.getGraphics().getDeltaTime() * -speed;
		body.setLinearVelocity(0, yvel);
	}
	
	public void moveLeft()
	{
		xvel = Gdx.app.getGraphics().getDeltaTime() * -speed;
		body.setLinearVelocity(xvel, 0);
	}
	
	public void moveRight()
	{
		xvel = Gdx.app.getGraphics().getDeltaTime() * speed;
		body.setLinearVelocity(xvel, 0);
	}
	
	public void noMovement()
	{
		body.setLinearVelocity(0, 0);
	}
}
