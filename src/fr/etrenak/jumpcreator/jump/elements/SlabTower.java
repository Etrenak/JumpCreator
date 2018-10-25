package fr.etrenak.jumpcreator.jump.elements;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.utils.Util;

public class SlabTower extends JumpElement
{
	private int size;
	private Material towerBlockType;

	public SlabTower(ConfigurationSection config)
	{
		super(new ElementSide(1.0d, 1.0d), new ElementSide(0.5d, 1.0d));
		size = config.getInt("Size");
		towerBlockType = config.contains("TowerBlockType") ? Material.valueOf(config.getString("TowerBlockType")) : Material.QUARTZ_BLOCK;
	}

	public SlabTower(ElementSide in, ElementSide out, Location location, List<Location> usedLocs, int size, Material towerBlockType)
	{
		super(in, out, location, usedLocs);
		this.size = size;
		this.towerBlockType = towerBlockType;
	}

	public int getSize()
	{
		return size;
	}

	@Override
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		loc.add(Math.cos(angle), 0, Math.sin(angle));

		angle = Math.toRadians((Math.round(Math.toDegrees(angle) / 90) * 90) - 180);
		
		for(int i = 0; i < size; i++)
		{

			loc.add(0, 1, 0);
			Util.setBlockTypeThreadSafe(loc, towerBlockType);
			usedLocs.add(loc.clone());

			Util.setBlockTypeThreadSafe(loc.clone().add(Math.cos(angle), 0, Math.sin(angle)), Material.STEP);
			usedLocs.add(loc.clone().add(Math.cos(angle), 0, Math.sin(angle)));
			
			angle -= Math.PI / 2;
		}
		loc.add(0, 1, 0);
		Util.setBlockTypeThreadSafe(loc, Material.STEP);
		usedLocs.add(loc.clone());

		setLocation(loc.clone());
		return new int[] {0, size + 1 /* Block de fin */ + 1 /*dalle*/, 0};
	}

	@Override
	public SlabTower clone()
	{
		return new SlabTower(in, out, location, usedLocs, size, towerBlockType);
	}

}
