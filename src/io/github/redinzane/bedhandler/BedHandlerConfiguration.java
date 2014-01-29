package io.github.redinzane.bedhandler;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;


public class BedHandlerConfiguration
{
	private final Configuration config;
	
	private static final String SECTION_WAITINGTIMES = "waitingtimes";
	private static final String SECTION_FEATURES = "features";
	
	private static final String SETDOWN_KEY = "setdown";
	private static final String BEDDESTROYER_KEY = "beddestroyer";
	
	private static final int DEFAULT_SETDOWN = 1800000;
	
	
	public BedHandlerConfiguration(Configuration config) 
	{
		this.config = config;
	}
	
	/**
	 * Retrieve the setdown waitingtime in milliseconds.
	 * @return waitingtime in milliseconds.
	 */
	public int getSetdown() 
	{
		Object value = getSectionOrDefault(SECTION_WAITINGTIMES).get(SETDOWN_KEY);
		
		if (value == null)
			return DEFAULT_SETDOWN;
		else
			return ((Number) value).intValue();
	}
	/**
	 * Set the setdown waitingtime in milliseconds.
	 * @param value - new setdown waitingtime in milliseconds.
	 */
	public void setSetdown(int value) 
	{
		getSectionOrDefault(SECTION_WAITINGTIMES).set(SETDOWN_KEY, value);
	}
	
	/**
	 * Retrieve the bed destroyer on/off boolean
	 * @return value - on/off boolean
	 */
	public boolean getBeddestroyer() {
		boolean value = getSectionOrDefault(SECTION_FEATURES).getBoolean(BEDDESTROYER_KEY);
		return value;
	}
	/**
	 * Set the bed destroyer on/off boolean.
	 * @param value - new boolean
	 */
	public void setBeddestroyer(boolean value) {
		getSectionOrDefault(SECTION_FEATURES).set(BEDDESTROYER_KEY, value);
	}
	
	
	private ConfigurationSection getSectionOrDefault(String name) 
	{
		ConfigurationSection section = config.getConfigurationSection(name);
		
		if (section != null)
			return section;
		else
			return config.createSection(name);
	}
}
