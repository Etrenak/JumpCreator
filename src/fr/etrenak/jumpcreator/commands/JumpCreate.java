package fr.etrenak.jumpcreator.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		int blocksSinceLastStructure = 10;
		Random rdm = new Random();

		boolean jumpable;

		JumpElement rdmElement = null;
		do
		{
			rdmElement = level.getRandomElement(rdm);
		}while(!(rdmElement instanceof JumpBlock));

		JumpElement prevElement = rdmElement.clone();
		int[] delta = {0, 1, 0};

		previousLocs.clear();

		rdmElement.generate(current.clone(), 0, level);

		prevLoc = current.clone();
		previousLocs.add(prevLoc);

		for(int jumpLocIndex = 0; jumpLocIndex < Integer.parseInt(args[0]); jumpLocIndex++)
		{
			blocksSinceLastStructure++;
			prevElement = rdmElement.clone();
			do
			{
				rdmElement = level.getRandomElement(rdm);
			}while(!(prevElement instanceof JumpBlock) && !(rdmElement instanceof JumpBlock));

			if(!(rdmElement instanceof JumpBlock))
				blocksSinceLastStructure = 0;

			length = rdm.nextInt(level.getMaxGap() + 1 - level.getMinGap()) + level.getMinGap();

			if(rdmElement.getIn().getHeight() > 1)
				delta[1] -= 1;

			if(prevElement.getOut().getHeight() < 1 && rdmElement.getIn().getHeight() >= 1)
				delta[1] -= 1;

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
				}while(nonJumpableRounds < 5 && (blocksSinceLastStructure < 3 && MathUtil.isAcute(angle, prevAngle) || blocksSinceLastStructure > 2 && (changeDirection && MathUtil.isObtuse(angle, prevAngle) || !changeDirection && MathUtil.isAcute(angle, prevAngle))));

				current.setX(Math.round(length * Math.cos(angle)) + prevLoc.getBlockX() + delta[0]);
				current.setZ(Math.round(length * Math.sin(angle)) + prevLoc.getBlockZ() + delta[2]);
				current.setY(prevLoc.getY() + delta[1]);

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

			delta = rdmElement.generate(current.clone(), angle, level);

			prevLoc = current.clone();
			previousLocs.add(prevLoc);
		}

		return true;
	}
}
