package fr.etrenak.jumpcreator.config;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class LevelsManager
{
	private HashMap<String, JumpLevel> configs;

	public LevelsManager(ConfigurationSection config)
	{
		configs = new HashMap<String, JumpLevel>();
		for(String key : config.getKeys(false))
			configs.put(key.toLowerCase(), new JumpLevel(key.toLowerCase(), config.getConfigurationSection(key)));
	}

	public JumpLevel getJumpLevel(String level)
	{
		return configs.containsKey(level.toLowerCase()) ? configs.get(level.toLowerCase()) : null;
	}

	public Set<String> getLevels()
	{
		return configs.keySet();
	}
}
