package fr.etrenak.jumpcreator.jump.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;

public abstract class JumpElement implements Cloneable
{
	protected ElementSide in;
	protected ElementSide out;
	protected List<Location> usedLocs;
	protected Location location;

	private JumpElement()
	{
		usedLocs = new ArrayList<>();
	}
	
	public JumpElement(ElementSide in, ElementSide out)
	{
		this();
		this.in = in;
		this.out = out;
	}

	
	public JumpElement(ElementSide in, ElementSide out, Location location, List<Location> usedLocs)
	{
		this.in = in;
		this.out = out;
		this.location = location;
		this.usedLocs = usedLocs;
	}

	public JumpElement(ConfigurationSection config)
	{
		this();
		in = new ElementSide(config.contains("In") ? config.getConfigurationSection("In") : config);
		out = new ElementSide(config.contains("Out") ? config.getConfigurationSection("Out") : config);
	}

	public abstract int[] generate(Location loc, double angle, JumpLevel level);

	public abstract JumpElement clone();
	
	@SuppressWarnings("deprecation") // Pour applyPhysics = false
	public void remove()
	{
		for(Location loc : usedLocs)
			loc.getBlock().setTypeId(Material.AIR.getId(), false);
	}

	public ElementSide getIn()
	{
		return in;
	}

	public ElementSide getOut()
	{
		return out;
	}

	public Location getLocation()
	{
		return location;
	}

	protected void setLocation(Location location)
	{
		this.location = location;
	}
}
