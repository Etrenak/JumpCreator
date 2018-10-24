package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.Material;

import fr.etrenak.jumpcreator.config.JumpLevel;

public class RoofedBlock extends JumpElement
{
	public RoofedBlock()
	{
		super(new ElementSide(1.0d, 1.0d), new ElementSide(1.0d, 1.0d));
	}

	@Override
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		level.getDefaultBlock().generate(loc, angle, level);
		
		loc.add(0,3,0).getBlock().setType(Material.SNOW);
		return new int[] {0, 1, 0};
	}

	@Override
	public JumpElement clone()
	{
		return new RoofedBlock();
	}
}
