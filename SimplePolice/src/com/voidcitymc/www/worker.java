		/*
List<String> configList = (List<String>)plugin.getConfig().getList("List");
configList.add(args[0]);
plugin.getConfig().set("List", configList);
		 */






//Need to write to the config like this:
/*

	- player = true
	- player2 = true
	- player3 = false

then list of all regions player  is attached to

   - player3+worldname = __global__, spawn, wild


*/

package com.voidcitymc.www;

import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
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
	} else if (!alreadyPolice(uuid, regionName, world)) {
		
		if (uuid != null && regionName != null && world != null) {
		List<String> configList = (List<String>)Main.Data.getList(uuid+world);
		configList.add(regionName);
		Main.Data.set(uuid+world, configList);
		//Saves the list ^
		
		//Puts uuid = true
		Main.Data.addDefault(uuid, true);
		Main main = new Main();
        main.saveResource("data.yml", false);
/////		
		//Need To Add Method Save This CONFIG!!!
		}
		
/*		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig(); */
		return;
	} else {
		return;
	}
}


	
public boolean alreadyPolice (String uuid, String regionName, String world) {
	List<String> configList = (List<String>)Main.Data.getList(uuid+world);

	if (Main.Data.getBoolean(uuid) && configList.contains(regionName)) {		
		return true;
	} else {
		return false;
	}

}

public void removePolice(String uuid, String regionName, String world) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.alreadyPolice(uuid, regionName, world)) {
		Main.Data.addDefault(uuid, null);
//if null dosent work change it back to false
		List<String> configList = (List<String>)Main.Data.getList(uuid+world);
		configList.remove(regionName);
		Main.Data.set(uuid+world, configList);
		
		
		//need to find the save config method of the custom config
		Main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}


public static boolean checkIfPlayerIsPoliceInRegions(Player p) {

	
	RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	RegionQuery query = container.createQuery();
	ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(p.getLocation()));
	
	worker testPoliceVar = new worker();
	for (ProtectedRegion region : set) {
	    if (testPoliceVar.alreadyPolice(p.getUniqueId().toString(), region.toString(), p.getWorld().toString())) {
	    	return true;
	    }
	}
	return false;
	
}





}
