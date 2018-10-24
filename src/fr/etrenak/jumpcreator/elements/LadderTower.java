package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.utils.Util;

public class LadderTower extends JumpElement
{
	private int size;
	private Material towerBlockType;

	protected LadderTower(int size, Material towerBlockType)
	{
		super(new ElementSide(1.5d, 1.5d), new ElementSide(0.5d, 1.0d));
		this.size = size;
		this.towerBlockType = towerBlockType;
	}

	public LadderTower(ConfigurationSection config)
	{
		super(new ElementSide(1.5d, 1.5d), new ElementSide(0.5d, 1.0d));
		size = config.getInt("Size");
		towerBlockType = config.contains("TowerBlockType") ? Material.valueOf(config.getString("TowerBlockType")) : Material.QUARTZ_BLOCK;
	}

	public int getSize()
	{
		return size;
	}

	@Override
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		angle = Math.toRadians((Math.round(Math.toDegrees(angle) / 90) * 90) - 180);

		for(int i = 0; i < size; i++)
		{
			loc.add(0, 1, 0);
			loc.getBlock().setType(towerBlockType);
			Block ladder = loc.clone().add(Math.cos(angle), 0, Math.sin(angle)).getBlock();
			ladder.setType(Material.LADDER);
			Util.fixAttachingFace(ladder, towerBlockType);
			angle -= Math.PI / 2;
		}
		loc.add(0, 1, 0);
		loc.getBlock().setType(Material.STEP);

		return new int[]{0,size + 1 /* Block de fin */ + 1 /*dalle*/, 0};
	}

	@Override
	public LadderTower clone()
	{
		return new LadderTower(size, towerBlockType);
	}

}
