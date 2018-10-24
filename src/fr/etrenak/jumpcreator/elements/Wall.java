package fr.etrenak.jumpcreator.elements;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;

public class Wall extends JumpElement
{
	private int gap;
	private Material wallBlockType;

	public Wall(ConfigurationSection config)
	{
		super(new ElementSide(1.0d, 1.0d), new ElementSide(1.0d, 1.0d));
		gap = config.getInt("Gap");
		wallBlockType = config.contains("WallBlockType") ? Material.valueOf(config.getString("WallBlockType")) : Material.IRON_BLOCK;
	}

	protected Wall(ElementSide inOut, int gap, Material wallBlockType)
	{
		super(inOut, inOut);
		this.gap = gap;
		this.wallBlockType = wallBlockType;
	}

	public int getGap()
	{
		return gap;
	}

	public Material getWallBlockType()
	{
		return wallBlockType;
	}

	@Override
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		angle = Math.toRadians(Math.round(Math.toDegrees(angle) / 90) * 90);

		level.getDefaultBlock().generate(loc, angle, level);
		loc.add(Math.cos(angle), 0, Math.sin(angle));

		int wallDist = new Random().nextInt(gap - 1);
		if(gap >= 4 && wallDist == 0)
			wallDist++;
			

		
		for(int i = 0; i < wallDist; i++)
		{
			loc.add(Math.cos(angle), 0, Math.sin(angle));
		}

		for(int i = 0; i < 3; i++)
			loc.clone().add(0, i, 0).getBlock().setType(wallBlockType);

		for(int i = 0; i < gap - wallDist - 1; i++)
		{
			loc.add(Math.cos(angle), 0, Math.sin(angle));
		}
		level.getDefaultBlock().generate(loc, angle, level);

		return new int[] {(int) Math.cos(angle) * gap, 1, (int) Math.sin(angle) * gap};
	}

	@Override
	public JumpElement clone()
	{
		return new Wall(in, gap, wallBlockType);
	}

}
