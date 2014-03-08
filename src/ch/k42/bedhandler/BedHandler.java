/*
 * Copyright (c) 2014. Thomas Richner, Moritz Schwab
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.k42.bedhandler;

import ch.k42.bedhandler.Metrics;
import java.io.IOException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BedHandler extends JavaPlugin
{

	private BedHandlerConfiguration config;
	Listener listener;
	private Metrics metrics;

	@Override
	public void onEnable()
	{
        this.saveDefaultConfig();
		config = new BedHandlerConfiguration(getConfig());
		listener = new SpawnListener(config.getDeathcooldown(), config.getFirstDeathcooldown(), this); //TODO second option
		getServer().getPluginManager().registerEvents(listener, this);		
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
		getServer().getScheduler().cancelAllTasks();
    }
}
