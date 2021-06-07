package net.pgfmc.prison.events.player;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.prison.Main;

public class OnJoin implements Listener {
	
	private ItemStack[] newPlayerItems = { new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.COOKED_BEEF) }; // Items new players should receive
	
	@EventHandler
	public void onJoin(PlayerChangedWorldEvent e)
	{
		if (e.getPlayer().getWorld().getName() != "Prison") { return; } // Return if the world isn't Prison
		
		File file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + "join.yml"); // Creates a File object
		FileConfiguration db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		if (!file.exists()) { try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } } // Create the file if it doesn't exist
		
		try {
			db.load(file); // loads file
			if (db.get("uuids") == null)
			{
				newPlayerItems(e.getPlayer());
			}
			
		} finally
		{
			return;
		}
				

	}

}
