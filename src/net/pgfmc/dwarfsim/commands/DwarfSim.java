package net.pgfmc.dwarfsim.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.dwarfsim.Main;

public class DwarfSim implements CommandExecutor {
	
	public static boolean creatingMine = false;
	public static String creatingMineName = "";
	public static int creatingMineLevel = -1;
	public static UUID creatingMineUUID = UUID.randomUUID();
	public static List<Location> creatingMineLocs = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (!(sender instanceof Player)) { return true; }
		if (args == null) { return true; }
		if (!label.equals("ds") && args[0].equals("create")) { return true; }
		
		
		Player p = (Player) sender;
		
		if (!(args.length == 3)) // must have arguments
		{
			p.sendMessage("§cError: wrong usage");
			p.sendMessage("§cUsage: /ds create <name> <level>");
			return true;
		}
		
		try { Integer.parseInt(args[2]); } catch (NumberFormatException except) // argument "level" must be type int
		{
			p.sendMessage("§cError: wrong usage (level must be type int)");
			p.sendMessage("§cUsage: /ds create <String:name> <int:level>");
			return true;
		}
		
		List<String> validChars = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0")); // Tried to use char but yeah
		for (char letter : args[1].toCharArray())
		{
			if (!validChars.contains(String.valueOf(letter).toLowerCase())) // If the entered mine name doesn't fit the file format
			{
				p.sendMessage("§cYou have entered an incorrect character");
				p.sendMessage("§cLetters and numbers only, please try again");
				return true;
			}
		}
		
		File file = new File(Main.plugin.getDataFolder() + File.separator + "mines" + File.separator + args[0].toLowerCase() + File.separator + "info.yml"); // Create new file object
		if (file.exists()) // Checking if the mine name exists already
		{
			p.sendMessage("§cThis mine name already exists, please try again");
			return true;
		}
		
		
		
		p.sendMessage("§aCreating mine \"" + args[1] + "\" as level " + Integer.parseInt(args[2]));
		p.sendMessage("§aPlease select position 1");
		creatingMine = true;
		creatingMineName = args[1];
		creatingMineLevel = Integer.parseInt(args[2]);
		creatingMineUUID = p.getUniqueId();
		creatingMineLocs.clear();
		
		return true;
	}

}
