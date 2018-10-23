package fr.etrenak.jumpcreator.utils;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DebugUtil
{
	public static void poulet(Location loc, String str)
	{
		Chicken poulet = (Chicken) loc.getWorld().spawnEntity(loc, EntityType.CHICKEN);
		poulet.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 100000));
		poulet.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10000, 100000));
		poulet.setCustomNameVisible(true);
		poulet.setCustomName(str);
	}
}
