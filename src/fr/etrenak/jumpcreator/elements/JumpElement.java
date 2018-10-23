package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public abstract class JumpElement implements Cloneable
{
	protected ElementSide in;
	protected ElementSide out;

	public JumpElement(ElementSide in, ElementSide out)
	{
		this.in = in;
		this.out = out;
	}

	public JumpElement(ConfigurationSection config)
	{
		in = new ElementSide(config.contains("In") ? config.getConfigurationSection("In") : config);
		out = new ElementSide(config.contains("Out") ? config.getConfigurationSection("Out") : config);
	}

	public abstract int generate(Location loc, double angle);

	public abstract JumpElement clone();

	public ElementSide getIn()
	{
		return in;
	}

	public ElementSide getOut()
	{
		return out;
	}

}
