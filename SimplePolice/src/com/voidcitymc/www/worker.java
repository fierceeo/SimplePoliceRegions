/*Need to write to the config like this:

   - player3+worldname = __global__, spawn, wild
*/

package com.voidcitymc.www;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class worker {
	 

	
//check if a player has item in hand
public static boolean TestForItem(Player p, Material item, String DisplayName) {
        if (item != null && p.getInventory().getItemInMainHand().getType() == item) {
            return true;
        }
        return false;
}

public String playerToUUID (String player) {
	return Bukkit.getPlayer(player).getUniqueId().toString();
}

public String playerToString (Player player) {
	return player.toString();
}

public void addPolice (String uuid, String regionName, String world) {
	if (alreadyPolice(uuid, regionName, world)) {
		return;
	}

        if (!alreadyPolice(uuid, regionName, world)) {
		
                List<String> configList = (List<String>)Main.getInstance().Data.getList(uuid+world);
                if (configList != null) {
            		configList.add(regionName);
            		Main.getInstance().Data.set(uuid+world, configList);
                }
                if (configList == null) {
                	List<String> configListNull =  new ArrayList<String>();
                	configListNull.add(regionName);
                	Main.getInstance().Data.set(uuid+world, configListNull);
                }

		
		//Puts uuid = true
		Main.getInstance().Data.addDefault(uuid, true);
		
		Main.getInstance().SaveTheConfig();
			
			
			
			//Main.getInstance().saveResource("data.yml", false);
		return;
	}
        return;
}

	
public boolean alreadyPolice (String uuid, String regionName, String world) {
	List<String> configList = (List<String>)Main.getInstance().Data.getList(uuid+world);
	
		if (configList != null) {
			if (Main.getInstance().Data.getBoolean(uuid) && configList.contains(regionName)) {
				return true;
			}
			return false;
	}
		return false;
}

public void removePolice(String uuid, String regionName, String world) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.alreadyPolice(uuid, regionName, world)) {
		Main.getInstance().Data.addDefault(uuid, false);
		List<String> configList = (List<String>)Main.getInstance().Data.getList(uuid+world);
		configList.remove(regionName);
		Main.getInstance().Data.set(uuid+world, configList);
		
		
		//need to find the save config method of the custom config

		Main.getInstance().SaveTheConfig();
		
		return;
	}
}


public static boolean checkIfPlayerIsPoliceInRegions(Player p) {

//remove me
        System.out.println(“reached method”);
	
	RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	RegionQuery query = container.createQuery();
	ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(p.getLocation()));
	
	worker testPoliceVar = new worker();
	for (ProtectedRegion region : set) {
//remove me v
            System.out.println(region.getId());
	    if (testPoliceVar.alreadyPolice(p.getUniqueId().toString(), region.getId(), p.getWorld().toString())) {
	    	return true;
	    }
	}
	return false;
	
}





}
