package fr.etrenak.jumpcreator.jump;

import java.util.ArrayList;
import java.util.List;

public class JumpsManager
{
	private List<Jump> jumps;

	public JumpsManager()
	{
		jumps = new ArrayList<>();
	}

	public void register(Jump jump)
	{
		jumps.add(jump);
	}

	public boolean delete(int id)
	{
		for(Jump jump : jumps)
			if(jump.getId() == id)
				return delete(jump);

		return false;
	}

	public boolean delete(Jump jump)
	{
		if(!jumps.contains(jump))
			return false;
		jump.delete();
		jumps.remove(jump);
		return true;
	}
}
