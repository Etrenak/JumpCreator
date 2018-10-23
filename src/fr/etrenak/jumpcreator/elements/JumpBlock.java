package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;

public class JumpBlock
{
	private Material mat;
	private byte data;
	private boolean isThin;
	private int size;
	private String owner;

	public JumpBlock(ConfigurationSection config)
	{
		mat = Material.valueOf(config.getString("Type"));

		data = config.contains("Data") ? (byte) config.getInt("Data") : 0;

		isThin = config.contains("Thin") ? config.getBoolean("Thin") : false;

		size = config.contains("Size") ? config.getInt("Size") : 1;

		owner = config.contains("Owner") ? config.getString("Owner") : null;

	}

	public JumpBlock(Material mat, byte data, int size, boolean isThin)
	{
		this(mat, data, size, isThin, null);
	}

	public JumpBlock(Material mat, byte data, int size, boolean isThin, String owner)
	{
		this.mat = mat;
		this.data = data;
		this.isThin = isThin;
		this.size = size;
		this.owner = owner;
	}

	public boolean isThin()
	{
		return isThin;
	}

	public int getSize()
	{
		return size;
	}

	public Material getType()
	{
		return mat;
	}

	public byte getData()
	{
		return data;
	}

	@SuppressWarnings("deprecation")
	public void place(Location loc)
	{
		loc.getBlock().setType(mat);
		loc.getBlock().setData(data);
		if(mat == Material.SKULL && owner != null)
		{
			Skull skull = (Skull) loc.getBlock().getState();
			skull.setOwner(owner);
			skull.update();
		}
	}

	public JumpBlock clone()
	{
		return new JumpBlock(mat, data, size, isThin, owner);
	}
}
