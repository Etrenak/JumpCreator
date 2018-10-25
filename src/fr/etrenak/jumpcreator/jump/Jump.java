package fr.etrenak.jumpcreator.jump;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.jump.elements.JumpBlock;
import fr.etrenak.jumpcreator.jump.elements.JumpElement;
import fr.etrenak.jumpcreator.utils.MathUtil;
import fr.etrenak.jumpcreator.utils.Util;

public class Jump
{
	private static int lastId;

	static
	{
		lastId = 1;
	}

	private int id;
	private List<JumpElement> elements;

	public Jump()
	{
		id = lastId++;
		elements = new ArrayList<>();
	}

	public void delete()
	{
		for(JumpElement je : elements)
			je.remove();
	}

	public void generate(JumpLevel level, Location start, int size, CommandSender commander)
	{
		Thread creatorThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Location current = start;
				Location prevLoc = current.clone();
				double angle = 0;
				double length = 0;
				int blocksSinceLastStructure = 10;
				Random rdm = new Random();

				boolean jumpable;

				JumpElement rdmElement = level.getDefaultBlock();

				JumpElement prevElement = rdmElement.clone();
				int[] delta = {0, 1, 0};

				rdmElement.generate(current.clone(), 0, level);

				prevLoc = current.clone();
				elements.add(rdmElement.clone());
				for(int jumpLocIndex = 0; jumpLocIndex < size; jumpLocIndex++)
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

						if(elements.size() > 1)
						{
							for(int prevLocsIndex = 1; prevLocsIndex <= Math.min(elements.size() - 1, 3); prevLocsIndex++)
							{
								if(MathUtil.getDistanceToSegment(elements.get(elements.size() - prevLocsIndex).getLocation().getBlockX(), elements.get(elements.size() - prevLocsIndex).getLocation().getBlockZ(), elements.get(elements.size() - prevLocsIndex - 1).getLocation().getBlockX(), elements.get(elements.size() - prevLocsIndex - 1).getLocation().getBlockZ(), current.getBlockX(), current.getBlockZ()) < Math.min(length, 2))
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
					elements.add(rdmElement.clone());
				}
				
				if(commander != null)
					Util.useBukkitThreadSafe(new Runnable()
					{
						
						@Override
						public void run()
						{
							commander.sendMessage("§aFélicitations, vous venez de créer le jump §2n°" + getId() + "§a. Si vous souhaitez le supprimer, utilisez la commande /RemoveJump " + getId() +" §c[Définitif]");
						}
					});
			}
		});

		creatorThread.start();

	}

	public static int getLastId()
	{
		return lastId;
	}

	public int getId()
	{
		return id;
	}

	public List<JumpElement> getElements()
	{
		return elements;
	}
}
