/*Need to write to the config like this:

   - player3+worldname = __global__, spawn, wild
*/

package com.voidcitymc.plugins.SimplePoliceRegions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
	//check if player is already police
	if (alreadyPolice(uuid, regionName, world)) {
		return;
	}

        if (!alreadyPolice(uuid, regionName, world)) {
        		List<String> PoliceList = (List<String>)Main.getInstance().Data.getList(uuid+world);
        		//check that the list isn't empty
                if (PoliceList != null) {
                	//if it's not empty then add the region
            		PoliceList.add(regionName.toLowerCase());
            		//set data
            		Main.getInstance().Data.set(uuid+world, PoliceList);
                }
                if (PoliceList == null) {
                	//list isn't there so we need to create a new one
                	List<String> configListNull =  new ArrayList<String>();
                	//add the region
                	configListNull.add(regionName.toLowerCase());
                	//set data
                	Main.getInstance().Data.set(uuid+world, configListNull);
                }
		//save config
		Main.getInstance().SaveTheConfig();
		return;
	}
        return;
}


public boolean isPoliceInGeneral(String uuid, String world) {
    //create worker object
	worker Worker = new worker();
	//check if the player is *a* police, dosen't matter which region
	List<String> configList = (List<String>)Main.getInstance().Data.getList(uuid+world);
	//check if the list is empty and check that player isn't police in __global__ region
		if (configList != null && Worker.alreadyPolice(uuid, "__global__", world)) {
			//the list is not empty meaning the player is a police
                        return true;
                }
		//the list is empty meaning the player is not a police
return false;
}


	
public boolean alreadyPolice (String uuid, String regionName, String world) {
	List<String> policeList = (List<String>)Main.getInstance().Data.getList(uuid+world);
		//confirm list is there
		if (policeList != null) {
			//check if the player is a police in the region
			if (policeList.contains(regionName)) {
				//player is police
				return true;
			}
			//player is not a police in this region
                        return false;
	}
		//player is not a police because the player is not a police at all (dosen't have a list)
		return false;
}

public void removePolice(String uuid, String regionName, String world) {
    //create worker object
	worker Worker = new worker();
	//check if player is a police in the specified region and world
	if (Worker.alreadyPolice(uuid, regionName, world)) {
		//player is police meaning that we can then remove them as a police with the specified arguments
		//get list of players police regions
		List<String> configList = (List<String>)Main.getInstance().Data.getList(uuid+world);
		//remove player from specified region
		configList.remove(regionName.toLowerCase());
		//set value
		Main.getInstance().Data.set(uuid+world, configList);
		//save file
		Main.getInstance().SaveTheConfig();
		return;
	}
	//player isn't police with these arguments, nothing to do
	return;
}


public static boolean checkIfPlayerIsPoliceInRegions(Player p) {
	//get region "block"
	RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	//create block
	RegionQuery query = container.createQuery();
	//gets the list of all the region(s) the player is standing in
	ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(p.getLocation()));
	//create Worker
	worker Worker = new worker();
	for (ProtectedRegion region : set) {
		
	    if (Worker.alreadyPolice(p.getUniqueId().toString(), region.getId(), p.getWorld().toString())) {
	    	return true;
	    }
	}
	if (Worker.alreadyPolice(p.getUniqueId().toString(), "__global__", p.getWorld().toString())) {
		return true;
	}
	return false;
	
}

public static boolean isLocationSafe (Location loc1) {
	//check if the location is safe to teleport to (air)
	if (loc1.getBlock().getType().equals(Material.AIR)) {
		//we know that the inital location is safe, so we need to check one block up
		//location one block up
		Location loc2 = new Location(loc1.getWorld(), loc1.getX(), loc1.getY()+1, loc1.getZ());
		if (loc2.getBlock().getType().equals(Material.AIR)) {
			//2nd block is safe too
			return true;
		}
		//loc2 didn't check out 
		return false;
	}
	//because that method didn't pass, we know that the location isn't safe
	return false;
}

public static Location policeTp (Player player, int MaxValTp) {
	Location LocP = player.getLocation();
	//players x,y,z
	int pX = LocP.getBlockX();
	int pY = LocP.getBlockY();
	int pZ = LocP.getBlockZ();
	
	//generate a random number from 0 to the farthest /police tp value in config (MaxValTp)
    double drandom1 = Math.random() * MaxValTp;
    double drandom2 = Math.random() * MaxValTp;
    int random1 = (int) Math.round(drandom1);
    int random2 = (int) Math.round(drandom2);
	
    int nX = pX+random1;
    int nY = pY;
    int nZ = pZ+random2;
    
    Location returnLoc = new Location(player.getWorld(), nX, nY, nZ);
    
    //keeps increasing y cord until location is safe
    while (!isLocationSafe(returnLoc)) {
    	nY = nY+1;
    	returnLoc = new Location(player.getWorld(), nX, nY, nZ);
    }
	
    return returnLoc;
    
	
}


}
