package net.pgfmc.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.prison.events.player.OnJoin;

public class Prison implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player)) { return false; }
		OnJoin.onPrisonJoin((Player) sender);
		
		return true;
	}

}
