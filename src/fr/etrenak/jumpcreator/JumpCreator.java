package fr.etrenak.jumpcreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.etrenak.jumpcreator.commands.JDebug;
import fr.etrenak.jumpcreator.commands.JumpCreate;
import fr.etrenak.jumpcreator.config.LevelsManager;

public class JumpCreator extends JavaPlugin
{
	private static JumpCreator instance;
	
	private FileConfiguration config;
	private LevelsManager levelsManager;
	
	public void onEnable()
	{
		instance = this;
		getCommand("JumpCreate").setExecutor(new JumpCreate());
		getCommand("JDebug").setExecutor(new JDebug());
		
		config = loadConfig("levels.yml");
		
		levelsManager = new LevelsManager(config);
	}
	
	public FileConfiguration loadConfig(String path)
	{
		File conf = new File(getDataFolder(), path);
		try
		{
			if(!getDataFolder().exists())
				getDataFolder().mkdir();
			
			if(conf.length() == 0L)
				conf.delete();
			
			if(!conf.exists())
			{
				Files.copy(getClass().getClassLoader().getResourceAsStream(path), conf.toPath());
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return YamlConfiguration.loadConfiguration(conf);
	}
	
	public static JumpCreator getInstance()
	{
		return instance;
	}
	
	public LevelsManager getLevelsManager()
	{
		return levelsManager;
	}
}
