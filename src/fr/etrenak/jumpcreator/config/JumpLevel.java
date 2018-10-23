package fr.etrenak.jumpcreator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.elements.JumpBlock;

public class JumpLevel
{
	private String name;

	private int maxGap;
	private int minGap;

	private List<JumpBlock> blocks;

	private int changeDirectionChance;
	private int blockOverHeadChance;

	private JumpBlock defaultBlock;

	public JumpLevel(String name, ConfigurationSection config)
	{
		this.name = name;
		maxGap = config.getInt("MaxGap");
		minGap = config.getInt("MinGap");
		changeDirectionChance = config.getInt("ChangeDirectionChance");
		blockOverHeadChance = config.getInt("BlockOverHeadChance");

		defaultBlock = new JumpBlock(config.getConfigurationSection("DefaultBlock"));

		blocks = new ArrayList<JumpBlock>();
		for(String key : config.getConfigurationSection("BlocksChances").getKeys(false))
			for(int i = 0; i < config.getInt("BlocksChances." + key + ".Chance"); i++)
				blocks.add(new JumpBlock(config.getConfigurationSection("BlocksChances." + key)));

		for(int i = blocks.size() - 1; i < 100; i++)
			blocks.add(defaultBlock);
	}

	public JumpBlock getRandomJumpBlock(Random rdm)
	{
		return blocks.get(rdm.nextInt(100));
	}

	public JumpBlock getDefaultBlock()
	{
		return defaultBlock;
	}

	public int getBlockOverHeadChance()
	{
		return blockOverHeadChance;
	}

	public String getName()
	{
		return name;
	}

	public int getMaxGap()
	{
		return maxGap;
	}

	public int getMinGap()
	{
		return minGap;
	}

	public int getChangeDirectionChance()
	{
		return changeDirectionChance;
	}

}
