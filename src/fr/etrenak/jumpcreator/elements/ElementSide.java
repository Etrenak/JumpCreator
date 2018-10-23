package fr.etrenak.jumpcreator.elements;

import org.bukkit.configuration.ConfigurationSection;

public class ElementSide implements Cloneable
{
	private double height;
	private double width;

	public ElementSide(ConfigurationSection config)
	{
		height = config.contains("Height") ? config.getDouble("Height") : 1;
		width = config.contains("Width") ? config.getDouble("Width") : 1;
	}

	public ElementSide(double height, double width)
	{
		super();
		this.height = height;
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public double getWidth()
	{
		return width;
	}
	
	public ElementSide clone()
	{
		return new ElementSide(height, width);
	}

}
