package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.utils.Util;

public class LadderTower extends JumpElement
{
	private int size;

	protected LadderTower(int size)
	{
		super(new ElementSide(1.5d, 1.5d), new ElementSide(0.5d, 1.0d));
		this.size = size;
	}

	public LadderTower(ConfigurationSection config)
	{
		super(new ElementSide(1.5d, 1.5d), new ElementSide(0.5d, 1.0d));
		size = config.getInt("Size");
	}

	public int getSize()
	{
		return size;
	}

	@Override
	public int generate(Location loc, double angle)
	{
		angle = Math.toRadians((Math.round(Math.toDegrees(angle) / 90) * 90) - 180);

		loc = loc.clone();
		for(int i = 0; i < size; i++)
		{
			loc.add(0, 1, 0);
			loc.getBlock().setType(Material.QUARTZ_BLOCK);
			Block ladder = loc.clone().add(Math.cos(angle), 0, Math.sin(angle)).getBlock();
			ladder.setType(Material.LADDER);
			Util.fixAttachingFace(ladder, Material.QUARTZ_BLOCK);
			angle -= Math.PI / 2;
		}
		loc.add(0, 1, 0);
		loc.getBlock().setType(Material.STEP);

		return size + 1 /* Block de fin */ + 1 /*dalle*/;
	}

	@Override
	public LadderTower clone()
	{
		return new LadderTower(size);
	}

}
