//Need to write to the config like this:
/*

	- player = true
	- player2 = true
	- player3 = false

then list of all regions player  is attached to

   - player3 = __global__, spawn, wild


*/

package com.voidcitymc.www;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;


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

public void addPolice (String uuid) {
	if (alreadyPolice(uuid)) {
		return;
	} else if (!alreadyPolice(uuid)) {
		Main.getInstance().getConfig().addDefault(uuid, true);
		Main.getInstance().saveConfig();
		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}

public boolean alreadyPolice (String uuid) {
	

	if (Main.Data.getBoolean(uuid)) {
		
		/*
List<String> configList = (List<String>)plugin.getConfig().getList("List");
configList.add(args[0]);
plugin.getConfig(),set("List", configList);
		 */
		
		
		if (Main.Data.getBooleanArray(CurrentregionName)) {
			return true;
		}
		return false;
	} else {
		return false;
	}

}

public void removePolice(String uuid) {
	worker testPoliceVar = new worker();
	if (testPoliceVar.alreadyPolice(uuid)) {
		Main.getInstance().getConfig().addDefault(uuid, false);
		Main.getInstance().saveConfig();
		return;
	} else {
		return;
	}
}





}


// https://www.spigotmc.org/threads/getconfig-outside-main-class.117854/?__cf_chl_jschl_tk__=243e49cdca59cc0a8fcc1edf2aaaf25be23203d5-1575926286-0-Aalx2i7iXifBWc6XR9i3zRp88ZD4TfVHYda2ZJLBKOLE2YlAdqbaipspC3mhjzy1svz_ygInJM9NKggkxB6Q4PismhSL4HB2Q1ggEvPXVTGViINkQqHQJCM9IoYVPuPvJD_JHfToN5wM1uiIZyjyaUjHZ0Ulxv5irwajC3_4u6E3KDjwoKM1pAI_jAJ6oAGZjGuR8ByTtXVwNAvc87yl9L7y59GIg1X9PAsofMIXTnPpkAp0wCi6kzhgfTJ9ANkSnI-hWTZM4dKGwefs2Iv1R2JhQUl23I-u1nfapprBuyINYlCex1MAp-AZBfvMiEFgcw
