package fr.etrenak.jumpcreator.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Attachable;

public class Util
{
	private static final BlockFace[] faces = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

	public static void fixAttachingFace(Block b, Material toAttach)
	{
		for(BlockFace face : faces)
			if(b.getRelative(face).getType() == toAttach)
			{

				BlockState state = b.getState();

				if(!(state.getData() instanceof Attachable))
					return;

				Attachable mData = (Attachable) state.getData();
				mData.setFacingDirection(face);
				state.update(true);
				return;

			}
	}
}