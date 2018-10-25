package fr.etrenak.jumpcreator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.etrenak.jumpcreator.JumpCreator;

public class RemoveJump implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length < 1 || !args[0].matches("\\d+"))
		{
			sender.sendMessage("§c/RemoveJump <id>");
			return true;
		}

		if(JumpCreator.getInstance().getJumpsManager().delete(Integer.parseInt(args[0])))
			sender.sendMessage("§bLe jump n°"+args[0]+" a bien été §csupprimé §b!");
		else
			sender.sendMessage("§cId inconnue");


		return true;
	}
}
