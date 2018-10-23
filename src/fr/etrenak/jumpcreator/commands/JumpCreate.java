package fr.etrenak.jumpcreator.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.Directional;

import fr.etrenak.jumpcreator.JumpCreator;
import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.elements.JumpBlock;
import fr.etrenak.jumpcreator.elements.JumpElement;
import fr.etrenak.jumpcreator.utils.MathUtil;

public class JumpCreate implements CommandExecutor
{
	private List<Location> previousLocs;

	public JumpCreate()
	{
		previousLocs = new LinkedList<>();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("§cPlayer only");
			return true;
		}

		if(args.length < 2 || !args[0].matches("\\d+") || JumpCreator.getInstance().getLevelsManager().getJumpLevel(args[1].toLowerCase()) == null)
		{
			sender.sendMessage("§c/JumpCreate <nombre de blocs> [" + String.join("|", JumpCreator.getInstance().getLevelsManager().getLevels()) + "]");
			return true;
		}

		JumpLevel level = JumpCreator.getInstance().getLevelsManager().getJumpLevel(args[1]);

		Player p = (Player) sender;
		Location current = p.getLocation().clone();
		Location prevLoc = current.clone();
		double angle = 0;
		double length = 0;
		Random rdm = new Random();

		boolean jumpable;

		JumpElement rdmElement = null;
		do
		{
			rdmElement = level.getRandomElement(rdm);
		}while(!(rdmElement instanceof JumpBlock));

		JumpElement prevElement = rdmElement.clone();
		int deltaY = 1;

		previousLocs.clear();

		rdmElement.generate(current, 0);

		prevLoc = current.clone();
		previousLocs.add(prevLoc);

		for(Entity en : p.getWorld().getEntities())
			if(!(en instanceof Player))
				en.remove();

		for(int jumpLocIndex = 0; jumpLocIndex < Integer.parseInt(args[0]); jumpLocIndex++)
		{
			prevElement = rdmElement.clone();
			do
			{
				rdmElement = level.getRandomElement(rdm);
			}while(!(prevElement instanceof JumpBlock) && !(rdmElement instanceof JumpBlock));
			length = rdm.nextInt(level.getMaxGap() + 1 - level.getMinGap()) + level.getMinGap();

			if(rdmElement.getIn().getHeight() > 1)
				deltaY -= 1;

			if(prevElement.getOut().getHeight() < 1 && rdmElement.getIn().getHeight() >= 1)
				deltaY -= 1;

			if(prevElement.getOut().getWidth() < 1 || rdmElement.getIn().getWidth() < 1)
				if(length > 1)
					length -= 1;
			if(prevElement.getOut().getWidth() >= 1.5 || rdmElement.getIn().getWidth() > 1.5)
				length += 1;

			jumpable = false;

			double prevAngle = angle;
			boolean changeDirection = rdm.nextInt(100) > level.getChangeDirectionChance();
			int nonJumpableRounds = 0;
			while(!jumpable)
			{
				nonJumpableRounds++;
				do
				{
					angle = Math.toRadians(rdm.nextInt(361));
				}while(nonJumpableRounds < 5 && (changeDirection && Math.min(Math.abs(angle - prevAngle), Math.abs(2 * Math.PI - Math.abs(angle - prevAngle))) < 3 * Math.PI / 4.0d || !changeDirection && Math.min(Math.abs(angle - prevAngle), Math.abs(2 * Math.PI - Math.abs(angle - prevAngle))) > Math.PI / 4.0d));

				current.setX(Math.round(length * Math.cos(angle)) + prevLoc.getBlockX());
				current.setZ(Math.round(length * Math.sin(angle)) + prevLoc.getBlockZ());
				current.setY(prevLoc.getY() + deltaY);

				if(previousLocs.size() > 1)
				{
					for(int prevLocsIndex = 1; prevLocsIndex <= Math.min(previousLocs.size() - 1, 3); prevLocsIndex++)
					{
						if(MathUtil.getDistanceToSegment(previousLocs.get(previousLocs.size() - prevLocsIndex).getBlockX(), previousLocs.get(previousLocs.size() - prevLocsIndex).getBlockZ(), previousLocs.get(previousLocs.size() - prevLocsIndex - 1).getBlockX(), previousLocs.get(previousLocs.size() - prevLocsIndex - 1).getBlockZ(), current.getBlockX(), current.getBlockZ()) < Math.min(length, 2))
						{
							jumpable = false;
							break;
						}
						else
						{
							jumpable = true;
						}

					}
				}
				else
				{
					jumpable = true;
				}

			}

			deltaY = rdmElement.generate(current, angle);

			prevLoc = current.clone();
			previousLocs.add(prevLoc);
		}

		return true;
	}

	public void orienteBlock(int degAngle, Block b)
	{
		BlockState state = b.getState();
		if(!(state.getData() instanceof Directional))
			return;

		Directional metaData = (Directional) state.getData();
		metaData.setFacingDirection(getBlockFace(degAngle).getOppositeFace());
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
