package net.pgfmc.dwarfsim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.pgfmc.dwarfsim.commands.DwarfSim;

public class CreateMine implements Listener {
	
	@EventHandler
	public void onCreatorLeave(PlayerQuitEvent e)
	{
		if (!DwarfSim.creatingMine) { return; } // If a mine isn't being created
		if (!DwarfSim.creatingMineUUID.equals(e.getPlayer().getUniqueId())) { return; } // If the player isn't the creator of the mine
		
		DwarfSim.creatingMine = false; // If the person who is trying to create the mine leaves, cancel the creation of the mine
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onCreatorInteract(PlayerInteractEvent e)
	{
		if (!DwarfSim.creatingMine) { return; } // If a mine isn't being created
		if (!DwarfSim.creatingMineUUID.equals(e.getPlayer().getUniqueId())) { return; } // If the player isn't the creator of the mine
		if (e.getClickedBlock() == null) { return; }
		
		
		Player p = e.getPlayer();
		
		if (DwarfSim.creatingMineLocs.size() <= 0) // pos1
		{
			DwarfSim.creatingMineLocs.add(e.getClickedBlock().getLocation());
			p.sendMessage("§aPosition 1 selected");
			p.sendMessage("§aPlease select position 2");
			return;
		}
		
		if (DwarfSim.creatingMineLocs.size() == 1) // pos2 and rest of code for creating mine
		{
			File file = new File(Main.plugin.getDataFolder() + File.separator + "mines" + File.separator + DwarfSim.creatingMineName.toLowerCase()); // Creates a File object
			file.mkdirs();
			file = new File(Main.plugin.getDataFolder() + File.separator + "mines" + File.separator + DwarfSim.creatingMineName.toLowerCase() + File.separator + "info.yml");
			FileConfiguration db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML
			
			try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } // Create the file if it doesn't exist
			
			try { db.load(file); } catch (FileNotFoundException except) { except.printStackTrace(); } catch (IOException except) { except.printStackTrace(); } catch (InvalidConfigurationException except) { except.printStackTrace(); }
			
			DwarfSim.creatingMineLocs.add(e.getClickedBlock().getLocation());
			p.sendMessage("§aPosition 2 selected");
			
			ArrayList<Mine> mine = new ArrayList<Mine>();
			mine.add(new Mine(DwarfSim.creatingMineName, DwarfSim.creatingMineLevel, 0, new HashMap<String, Integer>(), DwarfSim.creatingMineLocs.get(0), DwarfSim.creatingMineLocs.get(1)));
			db.set("mine", mine);
			
			
			try
			{
				db.save(file);
				
				file = new File(Main.plugin.getDataFolder() + File.separator + "logs"); // Creates a File object
				file.mkdirs();
				file = new File(Main.plugin.getDataFolder() + File.separator + "logs" + File.separator + "mines.yml");
				db = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML
				try { file.createNewFile(); } catch (IOException except) { except.printStackTrace(); } // Create the file if it doesn't exist
				try { db.load(file); } catch (FileNotFoundException except) { except.printStackTrace(); } catch (IOException except) { except.printStackTrace(); } catch (InvalidConfigurationException except) { except.printStackTrace(); }
				
				ArrayList<Mine> mines = new ArrayList<Mine>();
				Mine m = new Mine(DwarfSim.creatingMineName, DwarfSim.creatingMineLevel, 0, new HashMap<String, Integer>(), DwarfSim.creatingMineLocs.get(0), DwarfSim.creatingMineLocs.get(1));
				
				if (db.get("mines") != null) { mines = (ArrayList<Mine>) db.get("mines"); }
				
				mines.add(m);
				db.set("mines", mines);
				
				try { db.save(file); } catch (IOException except)
				{
					except.printStackTrace();
					p.sendMessage("§cSomething went wrong, please try again");
					p.sendMessage("§cIO Exception on saving mine state");
					DwarfSim.creatingMineLocs.clear();
					DwarfSim.creatingMine = false;
					return;
				}

				p.sendMessage("§aMine successfully created");
				return;
				
			} catch (IOException except)
			{
				except.printStackTrace();
				p.sendMessage("§cSomething went wrong, please try again");
				p.sendMessage("§cIO Exception on saving mine");
				DwarfSim.creatingMineLocs.clear();
				DwarfSim.creatingMine = false;
				return;
			}
		}
		
		if (DwarfSim.creatingMineLocs.size() > 1) // This really shouldn't ever happen
		{
			p.sendMessage("§cSomething went wrong, please try again");
			p.sendMessage("§cSelected more than 2 positions");
			DwarfSim.creatingMineLocs.clear();
			DwarfSim.creatingMine = false;
			return;
		}
		
	}

}
