package net.pgfmc.dwarfsim;

import org.bukkit.plugin.java.JavaPlugin; // These are your imports

import net.pgfmc.dwarfsim.commands.DwarfSim;
import net.pgfmc.dwarfsim.commands.FirstJoin;
import net.pgfmc.dwarfsim.events.OnBlockBreakInMine;

public class Main extends JavaPlugin { // exentends JavaPlugin from the Spigot library/API
	
	public static Main plugin; // Used for file saving
	
	@Override // Needed
	public void onEnable() // Runs when the plugin first starts
	{
		
		plugin = this; // represents the plugin
		getCommand("dwarfsim").setExecutor(new FirstJoin()); // Registers Hi command class
		getCommand("dwarfsim").setExecutor(new DwarfSim()); // Registers Hi command class
		getServer().getPluginManager().registerEvents(new OnBlockBreakInMine(), this);
		getServer().getPluginManager().registerEvents(new CreateMine(), this);
	}
	
	@Override // Needed
	public void onDisable()
	{
		// You can leave this empty or just don't write the method
	}
	

}