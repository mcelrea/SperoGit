package com.mcelrea.contacts;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mcelrea.player.Player;
import com.mcelrea.screens.GamePlay;

public class MyContactFilter implements ContactFilter{

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		
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
		
		return true;
	}

}
