package io.github.redinzane.bedhandler;

import java.io.File;
import java.io.IOException;

import ch.k42.bedhandler.SpawnListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BedHandler extends JavaPlugin
{

	private BedHandlerConfiguration config;     // Configuration
	private Listener listener;                  // The listener

	@Override
	public void onEnable()
	{
		//Creates a Config
        this.saveDefaultConfig();
		config = new BedHandlerConfiguration(getConfig());
				
		if (!hasConfig()) 
		{
			getConfig().options().copyDefaults(true);
			saveConfig();
					
			// Load it again
			config = new BedHandlerConfiguration(getConfig());
			getLogger().info("Creating default configuration.");
		}
		
		listener = new SpawnListener(config.getDeathcooldown(),this);
		getServer().getPluginManager().registerEvents(listener, this);
	}
	
	@Override
    public void onDisable()
    {
		getServer().getScheduler().cancelAllTasks();
    }
    
	
	//Checks if a config file exists
    private boolean hasConfig() {
		File file = new File(getDataFolder(), "config.yml");
		return file.exists();
	}
}
