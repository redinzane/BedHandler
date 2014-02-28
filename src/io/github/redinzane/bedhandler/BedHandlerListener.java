package io.github.redinzane.bedhandler;

import java.util.LinkedList;
import java.util.Stack;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BedHandlerListener implements Listener
{	
	boolean bedDestroyer = false;
	int setDownWaitingTime = 1800000;
	int deathCooldown = 300000;
	String spawnSetMessage = "Your spawn was set to your bed... If it still exists.";
	String spawnResetMessage = "Your spawn has been reset.";
	String bedClickMessage1 = "Your spawn will be set to this bed in ";
	String bedClickMessage2 = "... If it still exists then.";
	String warningOnFirstDeathMessage1 = "You have spawned here now. If you die within the next ";
	String warningOnFirstDeathMessage2 = " Minutes, this bed will reset.";
	Stack<PlayerBed> deleteStack = new Stack<PlayerBed>();
	boolean cooldownExtender = true;
	
	private long lastCall = 0;
	private LinkedList<PlayerBed> playerList = new LinkedList<PlayerBed>();
	private static class PlayerBed 
	{
		Player player;
		Location location;
		int countdown;
		int deathCooldown;
		long lastDeath;
		boolean firstDeath;
		boolean bedSet;
		
		PlayerBed(Player player, Location bed, int timeLeft, int deathCooldown)
		{
			this.player = player;
			this.location = bed;
			this.countdown = timeLeft;
			this.lastDeath = 0;
			this.firstDeath = true;
			this.bedSet = false;
			this.deathCooldown = deathCooldown;
		}
	}
	
	
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		/*Player playerRespawning = event.getPlayer();
		if(event.isBedSpawn())
		{
			if(bedDestroyer)
			{
				playerRespawning.setBedSpawnLocation(null);
				playerRespawning.sendMessage(ChatColor.GRAY + spawnResetMessage);
			}
		}*/
		Player deadPlayer = event.getPlayer();
		for(PlayerBed item: playerList)
		{
			if(item.player == deadPlayer)
			{
				if(item.countdown > 0 && !item.bedSet)
				{
					deleteStack.push(item);
					deadPlayer.sendMessage(ChatColor.GRAY + spawnResetMessage);
				}
				else if(item.firstDeath && item.bedSet)
				{
					if(event.isBedSpawn())
					{
						deadPlayer.sendMessage(ChatColor.GRAY + warningOnFirstDeathMessage1 + item.deathCooldown/60000 + warningOnFirstDeathMessage2);
						item.lastDeath = System.currentTimeMillis();
						item.firstDeath = false;
					}
				}
				else if(!item.firstDeath && item.bedSet)
				{
					if((System.currentTimeMillis() - item.lastDeath) < item.deathCooldown)
					{
						if(event.isBedSpawn())
						{
							deadPlayer.setBedSpawnLocation(null);
							deleteStack.push(item);
							deadPlayer.sendMessage(ChatColor.GRAY + spawnResetMessage);
						}
					}
					else
					{
						deadPlayer.sendMessage(ChatColor.GRAY + warningOnFirstDeathMessage1 + item.deathCooldown/60000 + warningOnFirstDeathMessage2);
						item.lastDeath = System.currentTimeMillis();
					}
				}
			}
		}
		useDeleteStack();
	}
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.hasBlock())
		{
			if((event.getClickedBlock().getType().equals(Material.BED) || event.getClickedBlock().getType().equals(Material.BED_BLOCK)) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				Player playerEntering = event.getPlayer();
				playerEntering.setBedSpawnLocation(null);
				for(PlayerBed item: playerList)
				{
					if(item.player.getName().equals(playerEntering.getName()))
					{
						playerList.remove(item);
					}
				}
				playerList.add(new PlayerBed(playerEntering, event.getClickedBlock().getLocation(), setDownWaitingTime, deathCooldown));
				playerEntering.sendMessage(ChatColor.GRAY + bedClickMessage1 + setDownWaitingTime/60000 + " minutes" + bedClickMessage2);
				event.setUseInteractedBlock(Result.DENY);
			}
		}
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
				item.bedSet = true;
				item.player.sendMessage(ChatColor.GRAY + spawnSetMessage);
			}
		}
	}
	
	private void useDeleteStack()
	{
		while(!deleteStack.isEmpty())
		{
			playerList.remove(deleteStack.pop());
		}
		deleteStack.clear();
	}
	
}
