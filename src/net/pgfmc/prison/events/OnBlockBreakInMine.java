package net.pgfmc.prison.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.prison.BlockInMine;
import net.pgfmc.prison.LevelSystem;

public class OnBlockBreakInMine implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		// if (!(e.getBlock().getLocation().getWorld().getName() == "prison")) { return; }
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		
		if (BlockInMine.isBlockInMine(p, b) == false)
		{
			p.sendMessage("§cYou cannot break this block.");
			e.setCancelled(true);
			return;
		}
		
		List<Material> breakables = new ArrayList<>(Arrays.asList(Material.STONE, Material.COAL_ORE));
		
		for (Material mat : breakables)
		{
			if (b.getType().equals(mat))
			{
				p.playSound(b.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				p.getInventory().addItem(new ItemStack(b.getType()));
				LevelSystem.xp(p, b);
				b.setType(Material.AIR);
				e.setCancelled(true);
				return;
			}
		}
		e.setCancelled(true);
		return;
	}

}
