package fr.etrenak.jumpcreator.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.jump.elements.JumpBlock;
import fr.etrenak.jumpcreator.jump.elements.JumpElement;
import fr.etrenak.jumpcreator.jump.elements.LadderTower;
import fr.etrenak.jumpcreator.jump.elements.RoofedBlock;
import fr.etrenak.jumpcreator.jump.elements.Wall;

public class JumpLevel
{
	private String name;

	private int maxGap;
	private int minGap;

	private List<JumpElement> elements;

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

		elements = new ArrayList<JumpElement>();
		for(String key : config.getConfigurationSection("Elements").getKeys(false))
			for(int i = 0; i < config.getInt("Elements." + key + ".Chance"); i++)
				switch(config.getString("Elements." + key + ".Type"))
				{
					case "Block":
						elements.add(new JumpBlock(config.getConfigurationSection("Elements." + key)));
						break;

					case "LadderTower":
						elements.add(new LadderTower(config.getConfigurationSection("Elements." + key)));
						break;

					case "RoofedBlock":
						elements.add(new RoofedBlock(config.getConfigurationSection("Elements." + key)));
						break;
						
					case "Wall":
						elements.add(new Wall(config.getConfigurationSection("Elements." + key)));
						break;

				}

		for(int i = elements.size() - 1; i < 100; i++)
			elements.add(defaultBlock);
	}

	public JumpElement getRandomElement(Random rdm)
	{
		return elements.get(rdm.nextInt(100));
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
