package io.github.redinzane.bedhandler;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;


public class BedHandlerConfiguration
{
	private final Configuration config;
	private static final String DEATHCOOLDOWN_KEY = "deathcooldown";
    private static final int DEATHCOOLDOWN_DEFAULT = 120000;
    private static final String FDEATHCOOLDOWN_KEY = "initialcooldown";

	public BedHandlerConfiguration(Configuration config) 
	{
		this.config = config;
	}
	

	/**
	 * Retrieve the Deathcooldown in milliseconds.
	 * @return Deathcooldown in milliseconds.
	 */
	public int getDeathcooldown()
	{
		int value = config.getInt(DEATHCOOLDOWN_KEY);
		return value < 0 ? DEATHCOOLDOWN_DEFAULT : value;
	}

    /**
     * Retrieve the Deathcooldown in milliseconds.
     * @return Deathcooldown in milliseconds.
     */
    public int getFirstDeathcooldown()
    {
        int value = config.getInt(FDEATHCOOLDOWN_KEY);
        return value < 0 ? DEATHCOOLDOWN_DEFAULT : value;
    }
}
