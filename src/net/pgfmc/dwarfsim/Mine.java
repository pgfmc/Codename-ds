package net.pgfmc.dwarfsim;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Mine {
	
	// Class variables
	String name = "";
	int level = -1;
	int totalBlocksBroken = 0;
	HashMap<String, Integer> allBlocksBroken = new HashMap<>();
	Location pos1 = new Location(Bukkit.getWorld("prison"), 0, 0, 0);
	Location pos2 = pos1;
	
	public Mine(String name, int level, int totalBlocksBroken, HashMap<String, Integer> allBlocksBroken, Location pos1, Location pos2) // Constructor to create class object Mine
	{
		this.name = name;
		this.level = level;
		this.totalBlocksBroken = totalBlocksBroken;
		this.allBlocksBroken = allBlocksBroken;
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

}
