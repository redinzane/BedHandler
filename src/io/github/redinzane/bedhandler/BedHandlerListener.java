package io.github.redinzane.bedhandler;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BedHandlerListener implements Listener
{	
	boolean bedDestroyer = false;
	int setDownWaitingTime = 1800000;
	private long lastCall = 0;
	private LinkedList<PlayerBed> playerList = new LinkedList<PlayerBed>();
	private static class PlayerBed 
	{
		Player player;
		Location location;
		int countdown;
		
		PlayerBed(Player player, Location bed, int timeLeft)
		{
			this.player = player;
			this.location = bed;
			this.countdown = timeLeft;
		}
	}
	
	
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		Player playerRespawning = event.getPlayer();
		if(event.isBedSpawn())
		{
			if(bedDestroyer)
			{
				Location location = playerRespawning.getBedSpawnLocation();
				if(location != null)
				{
					location.getWorld().getBlockAt(location).breakNaturally();
				}
				playerRespawning.setBedSpawnLocation(null);
			}
		}
	}
	
	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event)
	{
		Player playerEntering = event.getPlayer();
		playerEntering.setBedSpawnLocation(null);
		for(PlayerBed item: playerList)
		{
			if(item.player.equals(playerEntering))
			{
				playerList.remove(item);
			}
		}
		playerList.add(new PlayerBed(playerEntering, event.getBed().getLocation(), setDownWaitingTime));
		playerEntering.sendMessage(ChatColor.GRAY + "Your spawn will be set to this bed in " + setDownWaitingTime/60000 + " minutes... If it still exists then.");
		event.setCancelled(true);
	}
	
	public void updatePlayerList()
	{
		long currentTime = System.currentTimeMillis();
		int deltaTime = (int) (currentTime-lastCall);
		lastCall = currentTime;
		for(PlayerBed item: playerList)
		{
			item.countdown -= deltaTime;
			if(item.countdown <= 0)
			{
				item.player.setBedSpawnLocation(item.location);
				item.player.sendMessage(ChatColor.GRAY + "Your spawn was set to your bed... If it still exists.");
			}
		}
	}
	
}
