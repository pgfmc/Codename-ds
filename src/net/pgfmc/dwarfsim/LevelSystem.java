package net.pgfmc.dwarfsim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LevelSystem {
	
	private static List<Integer> numBlocksForLevels = new ArrayList<>(Arrays.asList(64, 256, 640, 1280, 2048));
	
	private static boolean levelUp(Player p, Block b, int blocks) // true if leveled up, false if not
	{
		if (blocks == 1)
		{
			b.getWorld().spawnParticle(Particle.CRIT, b.getLocation(), 1);
			b.getWorld().playSound(b.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 1, 1);
			return true;
		}
		
		return false;
	}
	
	public static void xp(Player p, Block b)
	{
		File file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + p.getName()); // Creates a File object
		file.mkdirs();
		file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + p.getName() + File.separator + p.getUniqueId().toString() + ".yml");
		FileConfiguration db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
		
		try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } // Create the file if it doesn't exist
			
		try { db.load(file); } catch (FileNotFoundException except) { except.printStackTrace(); } catch (IOException except) { except.printStackTrace(); } catch (InvalidConfigurationException except) { except.printStackTrace(); }
		
		
		if (file.length() == 0) // If the file is empty
		{
			db.set("name", p.getName());
			db.set("uuid", p.getUniqueId().toString());
			db.set("total_blocks_mined", 1);
			db.set("blocks_until_next_level", 63);
			db.set("xp", p.getExp());
			db.set("xp_level", 0);
			
			List<String> allBlocksMined = new ArrayList<>();
			allBlocksMined.add(b.getType().toString());
			db.set("all_blocks_mined", allBlocksMined);
		} else
		{
			if(levelUp(p, b, db.getInt("blocks_until_next_level")))
			{
				db.set("time_since_last_levelup", (new Date().toString()));
				p.giveExpLevels(1);
			}
			
			db.set("total_blocks_mined", db.getInt("total_blocks_mined") + 1);			
			db.set("xp", p.getExp());
			db.set("xp_level", p.getLevel());
			
			int totalLevelBlocks = 0;
			for (int i = db.getInt("xp_level"); i >= 0; i--)
			{
				p.sendMessage(String.valueOf(i));
				totalLevelBlocks += numBlocksForLevels.get(i);
				p.sendMessage(String.valueOf(totalLevelBlocks));
			}
			db.set("blocks_until_next_level", totalLevelBlocks - db.getInt("total_blocks_mined"));
			
			// null check (notice the !(not)) -- Basically just logs how many of each block was mined
			if (!(db.get("all_blocks_mined." + b.getType().toString()) == null)) { db.set("all_blocks_mined." + b.getType().toString(), db.getInt("all_blocks_mined." + b.getType().toString()) + 1); } else { db.set("all_blocks_mined." + b.getType().toString(), 1); }
			
			
			// Need to make float before because of weird casting error, literally don't know
			float bunl = db.getInt("blocks_until_next_level");
			float nbfl = numBlocksForLevels.get(db.getInt("xp_level"));
			
			float nextLevelPercent = 1 - (bunl / nbfl);
			p.sendMessage(String.valueOf(nextLevelPercent) + " " + String.valueOf(db.getInt("blocks_until_next_level")) + " " + String.valueOf(numBlocksForLevels.get(db.getInt("xp_level"))));
			p.setExp(nextLevelPercent);
		}
		
		try { db.save(file); } catch (IOException except) { except.printStackTrace(); }
	}

}
