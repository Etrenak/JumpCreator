package fr.etrenak.jumpcreator.elements;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class LadderTower extends JumpElement
{
	private int size;
	private BlockFace start;

	public LadderTower(int size, BlockFace start)
	{
		this.size = size;
		this.start = start;
	}

	public int getSize()
	{
		return size;
	}

	public BlockFace getStart()
	{
		return start;
	}

	@Override
	public void generate(Location loc)
	{
		loc = loc.clone();
		for(int i = 0;i<size;i++)
		{
			loc.add(0,1,0);
		}
	}

}
