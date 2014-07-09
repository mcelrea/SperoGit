package com.mcelrea.gameWorld;

import com.badlogic.gdx.physics.box2d.World;

public class Sector 
{
	Area areas[][][];
	
	public Sector(int numRows, int numCols, int depth)
	{
		areas = new Area[numRows][numCols][depth];
	}
	
	public void fillAreasWithTitles(World world)
	{
		for(int row=0; row < areas.length; row++)
		{
			for(int col=0; col < areas[row].length; col++)
			{
				for(int depth=0; depth < areas[row][col].length; depth++)
				{
					areas[row][col][depth] = new Area("Room [" + row + "][" + col + "][" + depth + "]", world);
				}
			}
		}
	}
	
	public Area getArea(int row, int col, int depth)
	{
		if (row < areas.length && col < areas[row].length && depth < areas[row][col].length)
		{
			return areas[row][col][depth];
		}
		
		return null;
	}
}
