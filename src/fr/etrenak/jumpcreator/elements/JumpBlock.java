package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;

import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.utils.Util;

public class JumpBlock extends JumpElement
{
	private Material mat;
	private byte data;
	private String owner;

	public JumpBlock(ConfigurationSection config)
	{
		super(config);
		mat = Material.valueOf(config.getString("Material"));

		data = config.contains("Data") ? (byte) config.getInt("Data") : 0;

		owner = config.contains("Owner") ? config.getString("Owner") : null;
		//		System.out.println(this);

	}

	protected JumpBlock(Material mat, byte data, String owner, ElementSide inOut)
	{
		super(inOut, inOut);
		this.mat = mat;
		this.data = data;
		this.owner = owner;
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
	public int[] generate(Location loc, double angle, JumpLevel level)
	{
		loc.getBlock().setType(mat);
		loc.getBlock().setData(data);
		if(mat == Material.SKULL && owner != null)
		{
			Skull skull = (Skull) loc.getBlock().getState();
			skull.setOwner(owner);
			skull.update();
			skull.setRotation(Util.getBlockFace(angle).getOppositeFace());
		}
		return new int[] {0, 1, 0};
	}

	public JumpBlock clone()
	{
		return new JumpBlock(mat, data, owner, in);
	}

	@Override
	public String toString()
	{
		return "JumpBlock [mat=" + mat + ", data=" + data + ", owner=" + owner + ", inh=" + in.getHeight() + ", inw=" + in.getWidth() + ", outh=" + out.getHeight() + ", outw=" + out.getWidth() + "]";
	}

}
