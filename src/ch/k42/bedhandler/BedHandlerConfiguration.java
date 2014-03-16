package ch.k42.bedhandler;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class BedHandlerConfiguration
{
	private final Configuration config;
	
	private static final String SECTION_COOLDOWNS = "cooldowns";
	private static final String SECTION_MESSAGES = "messages";
	private static final char COLORCODE = '&';
	
	private static final String SPAWNSET_KEY = "spawnSetMessage";
	private static final String BEDNOTREADY_KEY = "bedNotReadyMessage";
	private static final String BEDCLICK_KEY = "bedClickMessage";
	private static final String BEDCLICKREP_KEY = "bedClickMessageRep";
	private static final String BEDRESPAWN_KEY = "spawnSetMessage";
	
	private static final String DEATHCOOLDOWN_KEY = "deathcooldown";
    private static final int DEATHCOOLDOWN_DEFAULT = 120000;
    private static final String FDEATHCOOLDOWN_KEY = "initialcooldown";

	public BedHandlerConfiguration(Configuration config) 
	{
		this.config = config;
	}
	


	/**
	 * Retrieve the Message.
	 * @return Message including Colorcode
	 */
	public String getSpawnSetMessage()
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(SPAWNSET_KEY);
		if(value == null)
		{
			return "";
		}
		else
		{
			return ChatColor.translateAlternateColorCodes(COLORCODE, value);
		}
	}
	/**
	 * Retrieve the Message.
	 * @return Message including Colorcode
	 */
	public String getBedNotReadyMessage()
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDNOTREADY_KEY);
		if(value == null)
		{
			return "";
		}
		else
		{
			return ChatColor.translateAlternateColorCodes(COLORCODE, value);
		}
	}
	/**
	 * Retrieve the Message.
	 * @return Message including Colorcode
	 */
	public String getBedClickMessage()
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDCLICK_KEY);
		if(value == null)
		{
			return "";
		}
		else
		{
			return ChatColor.translateAlternateColorCodes(COLORCODE, value);
		}
	}
	/**
	 * Retrieve the Message.
	 * @return Message including Colorcode
	 */
	public String getBedClickRepMessage()
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDCLICKREP_KEY);
		if(value == null)
		{
			return "";
		}
		else
		{
			return ChatColor.translateAlternateColorCodes(COLORCODE, value);
		}
	}
	/**
	 * Retrieve the Message.
	 * @return Message including Colorcode
	 */
	public String getBedRespawnMessage()
	{
		String value = getSectionOrDefault(SECTION_MESSAGES).getString(BEDRESPAWN_KEY);
		if(value == null)
		{
			return "";
		}
		else
		{
			return ChatColor.translateAlternateColorCodes(COLORCODE, value);
		}
	}
	
	/**
	 * Retrieve the Deathcooldown in milliseconds.
	 * @return Deathcooldown in milliseconds.
	 */
	public int getDeathcooldown()
	{
		int value = getSectionOrDefault(SECTION_COOLDOWNS).getInt(DEATHCOOLDOWN_KEY);
		return value < 0 ? DEATHCOOLDOWN_DEFAULT : value;
	}

    /**
     * Retrieve the Deathcooldown in milliseconds.
     * @return Deathcooldown in milliseconds.
     */
    public int getFirstDeathcooldown()
    {
        int value = getSectionOrDefault(SECTION_COOLDOWNS).getInt(FDEATHCOOLDOWN_KEY);
        return value < 0 ? DEATHCOOLDOWN_DEFAULT : value;
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
