package net.pgfmc.dwarfsim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.dwarfsim.events.OnFirstJoin;

public class FirstJoin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player)) { return false; }
		
		if (!label.equals("fj")) { return true; }
		
		OnFirstJoin.onDwarfSimJoin((Player) sender);
		return true;
	}

}
