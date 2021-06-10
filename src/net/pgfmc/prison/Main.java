package net.pgfmc.prison;

import org.bukkit.plugin.java.JavaPlugin; // These are your imports

import net.pgfmc.prison.commands.FirstJoin;
import net.pgfmc.prison.events.OnBlockBreakInMine;

public class Main extends JavaPlugin { // exentends JavaPlugin from the Spigot library/API
	
	public static Main plugin; // Used for file saving
	
	@Override // Needed
	public void onEnable() // Runs when the plugin first starts
	{
		
		plugin = this; // represents the plugin
		getCommand("firstjoin").setExecutor(new FirstJoin()); // Registers Hi command class
		getServer().getPluginManager().registerEvents(new OnBlockBreakInMine(), this);
	}
	
	@Override // Needed
	public void onDisable()
	{
		// You can leave this empty or just don't write the method
	}
	

}