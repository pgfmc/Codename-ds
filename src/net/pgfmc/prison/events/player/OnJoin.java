package net.pgfmc.prison.events.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.prison.Main;

public class OnJoin implements Listener {
	
	private ItemStack[] newPlayerItems = { new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.COOKED_BEEF, 16) }; // Items new players should receive
	
	@EventHandler
	public void onJoin(PlayerChangedWorldEvent e)
	{
		Player p = e.getPlayer();
		if (p.getWorld().getName() != "Prison") { return; } // Return if the world isn't Prison
		
		File file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + "join.yml"); // Creates a File object
		FileConfiguration db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		if (!file.exists()) { try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } } // Create the file if it doesn't exist
		
		try {
			db.load(file); // loads file
			if (db.get("uuids.") == null) // If the file is empty
			{
				p.getInventory().addItem(newPlayerItems);
				db.set("uuids.", p.getUniqueId().toString());
				
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<String> uuids = (List<String>) db.get("uuids."); // Gets the list of players who have joined Prison
			if (!uuids.contains(p.getUniqueId().toString())) // If the player has not previously joiend
			{
				p.getInventory().addItem(newPlayerItems);
				db.set("uuids.", p.getUniqueId().toString());
				return;
			}
			
			return;
			
		} catch (FileNotFoundException except) {
			// TODO Auto-generated catch block
			except.printStackTrace();
		} catch (IOException except) {
			// TODO Auto-generated catch block
			except.printStackTrace();
		} catch (InvalidConfigurationException except) {
			// TODO Auto-generated catch block
			except.printStackTrace();
		}
				

	}

}
