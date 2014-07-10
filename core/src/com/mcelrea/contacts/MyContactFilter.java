package com.mcelrea.contacts;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mcelrea.player.Player;
import com.mcelrea.screens.GamePlay;

public class MyContactFilter implements ContactFilter{

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////Movement Code, Collision with Bounding walls/////////////////////////
		//If a player hits one of the bounding box boundary lines set a boolean variable/////////////
		//in the GamePlay class that the wall is being hit. The value of these boolean variables/////
		//is being checked at the end of the render method.  The player is then moved to the/////////
		//next proper area.  It is important to note that movement needs to be done at the end///////
		//of the render method otherwise an error will occur.  I believe that box2D and rendering////
		//both need to be done otherwise you get a memory lock type error.///////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////
		if(fixtureA.getUserData() instanceof Player && fixtureB.getUserData().equals("bottom wall"))
		{
			GamePlay.hittingBottomWall = true;
			return true;
		}
		if(fixtureB.getUserData() instanceof Player && fixtureA.getUserData().equals("bottom wall"))
		{
			GamePlay.hittingBottomWall = true;
			return true;
		}
		if(fixtureA.getUserData() instanceof Player && fixtureB.getUserData().equals("top wall"))
		{
			GamePlay.hittingTopWall = true;
			return true;
		}
		if(fixtureB.getUserData() instanceof Player && fixtureA.getUserData().equals("top wall"))
		{
			GamePlay.hittingTopWall = true;
			return true;
		}
		if(fixtureA.getUserData() instanceof Player && fixtureB.getUserData().equals("left wall"))
		{
			GamePlay.hittingLeftWall = true;
			return true;
		}
		if(fixtureB.getUserData() instanceof Player && fixtureA.getUserData().equals("left wall"))
		{
			GamePlay.hittingLeftWall = true;
			return true;
		}
		if(fixtureA.getUserData() instanceof Player && fixtureB.getUserData().equals("right wall"))
		{
			GamePlay.hittingBottomWall = true;
			return true;
		}
		if(fixtureB.getUserData() instanceof Player && fixtureA.getUserData().equals("right wall"))
		{
			GamePlay.hittingRightWall = true;
			return true;
		}
		/////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////End Movement Code, Collision with Bounding walls/////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		if(fixtureA.getUserData() instanceof DPad || fixtureB.getUserData() instanceof DPad)
		{
			return false;
		}
		
		return true;
	}

}
