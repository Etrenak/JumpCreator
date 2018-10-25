package fr.etrenak.jumpcreator.jump.elements;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.utils.Util;

public class RoofedBlock extends JumpElement
{
	public RoofedBlock(ConfigurationSection config)
	{
		super(new ElementSide(1.0d, 1.0d), new ElementSide(1.0d, 1.0d));
	}

	@Override
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		setLocation(loc.clone());
		JumpBlock defaultBlock = level.getDefaultBlock().clone();
		defaultBlock.generate(loc, angle, level);
		
		Util.setBlockTypeThreadSafe(loc.add(0, 3, 0), Material.SNOW);

		usedLocs.add(loc.clone());
		usedLocs.addAll(defaultBlock.usedLocs);
		
		return new int[] {0, 1, 0};
	}

	public RoofedBlock(ElementSide in, ElementSide out, Location location, List<Location> usedLocs)
	{
		super(in, out, location, usedLocs);
	}

	@Override
	public JumpElement clone()
	{
		return new RoofedBlock(in, out, location, usedLocs);
	}
}
