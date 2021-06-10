package net.pgfmc.prison;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockInMine {
	
	public static boolean isBlockInMine(Player p, Block b)
	{
		Location loc = b.getLocation();
		double locX = loc.getBlockX();
		double locY = loc.getBlockY();
		double locZ = loc.getBlockZ();
		
		
		Location pos1 = new Location(Bukkit.getWorld("prison"), -25.5, 74.0, 28.5);
		Location pos2 = new Location(Bukkit.getWorld("prison"), -11.5, 62, 15.5);
		
		double maxX = 0.0;
		double minX = 0.0;
		double maxY = 0.0;
		double minY = 0.0;
		double maxZ = 0.0;
		double minZ = 0.0;
		
		if (pos1.getX() >= pos2.getX())
		{
			maxX = pos1.getX();
			minX = pos2.getX();
		} else
		{
			maxX = pos2.getX();
			minX = pos1.getX();
		}
		
		if (pos1.getY() >= pos2.getY())
		{
			maxY = pos1.getY();
			minY = pos2.getY();
		} else
		{
			maxY = pos2.getY();
			minY = pos1.getY();
		}
		
		if (pos1.getZ() >= pos2.getZ())
		{
			maxZ = pos1.getZ();
			minZ = pos2.getZ();
		} else
		{
			maxZ = pos2.getZ();
			minZ = pos1.getZ();
		}
		
		// Checking to see if the block is within a mine region
		if (minX < locX && locX < maxX) // minX < locX < maxX
		{
			if (minY < locY && locY < maxY) // minY < locY < maxY
			{
				if (minZ < locZ && locZ < maxZ) // minZ < locZ < maxZ
				{
					return true;
				}
			}
		}
		return false;
	}

}
