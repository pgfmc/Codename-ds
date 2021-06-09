package net.pgfmc.prison;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin; // These are your imports

import net.pgfmc.prison.commands.Prison;

public class Main extends JavaPlugin { // exentends JavaPlugin from the Spigot library/API
	
	public static Main plugin; // Used for file saving
	
	@Override // Needed
	public void onEnable() // Runs when the plugin first starts
	{
		File file = new File(Main.plugin.getDataFolder() + File.separator + "logs");
		try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
		
		plugin = this; // represents the plugin
		getCommand("prison").setExecutor(new Prison()); // Registers Hi command class
		// getServer().getPluginManager().registerEvents(new OnJoin(), this);
	}
	
	@Override // Needed
	public void onDisable()
	{
		// You can leave this empty or just don't write the method
	}

}