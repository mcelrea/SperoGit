package com.mcelrea.contacts;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class DPad 
{
	Body bodyLeft, bodyRight, bodyUp, bodyDown;
	Fixture left, right, up, down;
	
	public DPad(World world)
	{
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixDef = new FixtureDef();
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(-24,-4);
		PolygonShape box = new PolygonShape();
		box.setAsBox(4f, 4f);
		fixDef.shape = box;
		bodyUp = world.createBody(bodyDef);
		up = bodyUp.createFixture(fixDef);
		up.setUserData(this);
		
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(-32,-12);
		box = new PolygonShape();
		box.setAsBox(4f, 4f);
		fixDef.shape = box;
		bodyLeft = world.createBody(bodyDef);
		left = bodyLeft.createFixture(fixDef);
		left.setUserData(this);
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(-16,-12);
		box = new PolygonShape();
		box.setAsBox(4f, 4f);
		fixDef.shape = box;
		bodyRight = world.createBody(bodyDef);
		right = bodyRight.createFixture(fixDef);
		right.setUserData(this);
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(-24,-20);
		box = new PolygonShape();
		box.setAsBox(4f, 4f);
		fixDef.shape = box;
		bodyDown = world.createBody(bodyDef);
		down = bodyDown.createFixture(fixDef);
		down.setUserData(this);
		
	}
	
	public boolean upPressed(float x, float y)
	{
		return up.testPoint(x, y);
	}
	
	public boolean downPressed(float x, float y)
	{
		return down.testPoint(x, y);
	}
	
	public boolean leftPressed(float x, float y)
	{
		return left.testPoint(x, y);
	}
	
	public boolean rightPressed(float x, float y)
	{
		return right.testPoint(x, y);
	}
	
	public void adjustLocation(float x, float y)
	{
		bodyUp.setTransform(x, y+8, 0);
		bodyDown.setTransform(x, y-8, 0);
		bodyLeft.setTransform(x-8, y, 0);
		bodyRight.setTransform(x+8, y, 0);
	}
}
