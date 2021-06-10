package net.pgfmc.prison.events;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.prison.Main;

public class OnFirstJoin {
	
	private static ItemStack[] newPlayerItems = { new ItemStack(Material.GOLDEN_PICKAXE), new ItemStack(Material.COOKED_BEEF, 16) }; // Items new players should receive
	
	public static void onPrisonJoin(Player p)
	{
		if (p.getWorld().getName() != "prison") { return; } // Return if the world isn't Prison
		File file = new File(Main.plugin.getDataFolder() + File.separator + "logs"); // Creates a File object
		file.mkdirs();
		file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + "join.yml");
		FileConfiguration db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } // Create the file if it doesn't exist
		
		try { db.load(file); } catch (FileNotFoundException except) { except.printStackTrace(); } catch (IOException except) { except.printStackTrace(); } catch (InvalidConfigurationException except) { except.printStackTrace(); }
				
		if (db.get(p.getUniqueId().toString()) == null) // If no source of player (first time joining)
		{
			p.getInventory().addItem(newPlayerItems);
			db.set(p.getUniqueId().toString(), true);
			
			try { db.save(file); } catch (IOException except) { except.printStackTrace(); }
			
			return;
		}
	}

}
