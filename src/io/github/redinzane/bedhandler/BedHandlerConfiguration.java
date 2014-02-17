package io.github.redinzane.bedhandler;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;


public class BedHandlerConfiguration
{
	private final Configuration config;
	
	private static final String SECTION_WAITINGTIMES = "waitingtimes";
	private static final String SECTION_FEATURES = "features";
	private static final String SECTION_MESSAGES = "messages";
	
	private static final String SETDOWN_KEY = "setdown";
	private static final String BEDDESTROYER_KEY = "beddestroyer";
	private static final String SPAWNSET_KEY = "spawnset";
	private static final String SPAWNRESET_KEY = "spawnreset";
	private static final String BEDCLICK1_KEY = "bedclick1";
	private static final String BEDCLICK2_KEY = "bedclick2";
	
	private static final String spawnSetMessage_default = "Your spawn was set to your bed... If it still exists.";
	private static final String spawnResetMessage_default = "Your spawn has been reset.";
	private static final String bedClickMessage1_default = "Your spawn will be set to this bed in ";
	private static final String bedClickMessage2_default = "... If it still exists then.";
	
	private static final int DEFAULT_SETDOWN = 1800000;
	
	
	public BedHandlerConfiguration(Configuration config) 
	{
		this.config = config;
	}
	
	/**
	 * Retrieve the spawn set message.
	 * @return spawn set message.
	 */
	public String getSpawnSetMessage() 
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(SPAWNSET_KEY);
		if(value != null)
			return value;
		else
			return spawnSetMessage_default;
	}
	/**
	 * Set the spawn set message.
	 * @param value - new spawn set message.
	 */
	public void setSpawnSetMessage(String value) 
	{
		getSectionOrDefault(SECTION_MESSAGES).set(SPAWNSET_KEY, value);
	}
	
	/**
	 * Retrieve the spawn reset message.
	 * @return spawn reset message.
	 */
	public String getSpawnResetMessage() 
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(SPAWNRESET_KEY);
		if(value != null)
			return value;
		else
			return spawnResetMessage_default;
	}
	/**
	 * Set the spawn reset message.
	 * @param value - new spawn reset message.
	 */
	public void setSpawnResetMessage(String value) 
	{
		getSectionOrDefault(SECTION_MESSAGES).set(SPAWNRESET_KEY, value);
	}
	
	/**
	 * Retrieve the bedclick1 message.
	 * @return bedclick1 message.
	 */
	public String getBedClick1Message() 
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDCLICK1_KEY);
		if(value != null)
			return value;
		else
			return bedClickMessage1_default;
	}
	/**
	 * Set the bedclick1 message.
	 * @param value - new bedclick1 message.
	 */
	public void setBedClick1Message(String value) 
	{
		getSectionOrDefault(SECTION_MESSAGES).set(BEDCLICK1_KEY, value);
	}
	
	/**
	 * Retrieve the bedclick2 message.
	 * @return bedclick2 message.
	 */
	public String getBedClick2Message() 
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDCLICK2_KEY);
		if(value != null)
			return value;
		else
			return bedClickMessage2_default;
	}
	/**
	 * Set the bedclick2 message.
	 * @param value - new bedclick2 message.
	 */
	public void setBedClick2Message(String value) 
	{
		getSectionOrDefault(SECTION_MESSAGES).set(BEDCLICK2_KEY, value);
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
