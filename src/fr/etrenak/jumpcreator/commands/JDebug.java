package fr.etrenak.jumpcreator.commands;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Ladder;

import fr.etrenak.jumpcreator.utils.DebugUtil;

public class JDebug implements CommandExecutor
{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player p = (Player) sender;
		if(args[0].equals("poulet"))
		{
			for(int i = 0; i < 360; i += 30)
			{
				DebugUtil.poulet(((Player) sender).getLocation().add(4 * Math.cos(Math.toRadians(i)), 0, 4 * Math.sin(Math.toRadians(i))), i + " | " + Math.toRadians(i));
			}
		}
		else if(args[0].equals("angle"))
		{
			HashSet<Byte> set = new HashSet<Byte>();
			set.add((byte) 0);
			 p.getTargetBlock(set, 10).setType(Material.LADDER);
			BlockState state = p.getTargetBlock(set, 10).getState();
			Ladder l = (Ladder) state.getData();
			l.setFacingDirection(BlockFace.valueOf(args[1]));

			//			state.setData(l);
			state.update(true);
		}
		return true;

	}

	public static BlockFace getBlockFace(int angle)
	{
		angle = angle + 90;

		if(angle < 0)
			angle += 360.0;

		if(0 <= angle && angle < 67.5)
			return BlockFace.NORTH;

		else if(67.5 <= angle && angle < 157.5)
			return BlockFace.EAST;

		else if(157.5 <= angle && angle < 247.5)
			return BlockFace.SOUTH;

		else if(247.5 <= angle && angle < 337.5)
			return BlockFace.WEST;

		else if(337.5 <= angle && angle < 360.0)
			return BlockFace.NORTH;

		else
			return null;

	}

}
