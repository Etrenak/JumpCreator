package fr.etrenak.jumpcreator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.etrenak.jumpcreator.JumpCreator;
import fr.etrenak.jumpcreator.config.JumpLevel;
import fr.etrenak.jumpcreator.jump.Jump;

public class CreateJump implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("§cPlayer only");
			return true;
		}

		if(args.length < 2 || !args[0].matches("\\d+") || JumpCreator.getInstance().getLevelsManager().getJumpLevel(args[1].toLowerCase()) == null)
		{
			sender.sendMessage("§c/CreateJump <nombre de blocs> [" + String.join("|", JumpCreator.getInstance().getLevelsManager().getLevels()) + "]");
			return true;
		}
		
		sender.sendMessage("§7Génération du jump...");

		JumpLevel level = JumpCreator.getInstance().getLevelsManager().getJumpLevel(args[1]);
		Jump jump = new Jump();
		jump.generate(level, ((Player) sender).getLocation(), Integer.parseInt(args[0]));
		JumpCreator.getInstance().getJumpsManager().register(jump);

		sender.sendMessage("§aFélicitations, vous venez de créer le jump §2n°" + jump.getId() + "§a. Si vous souhaitez le supprimer, utilisez la commande /RemoveJump " + jump.getId() +" §c[Définitif]");
		return true;
	}
}
