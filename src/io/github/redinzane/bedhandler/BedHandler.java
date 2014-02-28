package io.github.redinzane.bedhandler;

import java.io.File;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class BedHandler extends JavaPlugin
{
	// Configuration
	private BedHandlerConfiguration config;
	// Minecraft packet handling
	private BedHandlerListener listener;
	//ID of the runnable task
	private int taskID = -1;
	//Metrics
	private Metrics metrics;
	
	@Override
	public void onEnable()
	{
		//Creates a Config
		config = new BedHandlerConfiguration(getConfig());
				
		if (!hasConfig()) 
		{
			getConfig().options().copyDefaults(true);
			saveConfig();
					
			// Load it again
			config = new BedHandlerConfiguration(getConfig());
			getLogger().info("Creating default configuration.");
		}
		
		listener = new BedHandlerListener();
		getServer().getPluginManager().registerEvents(listener, this);
		listener.cooldownExtender = config.getCooldownextender();
		listener.setDownWaitingTime = config.getSetdown();
		listener.spawnSetMessage = config.getSpawnSetMessage();
		listener.spawnResetMessage = config.getSpawnResetMessage();
		listener.bedClickMessage1 = config.getBedClick1Message();
		
		
		try
		{
			taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){public void run(){listener.updatePlayerList();} }, 0, 1);
			if(taskID == -1)
			{
				throw new Exception("BedHandler Update Task could not be scheduled");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try 
		{
			metrics = new Metrics(this);
			metrics.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
    public void onDisable()
    {
		getServer().getScheduler().cancelTask(taskID);
    }
    
	
	//Checks if a config file exists
    private boolean hasConfig() {
		File file = new File(getDataFolder(), "config.yml");
		return file.exists();
	}
}
