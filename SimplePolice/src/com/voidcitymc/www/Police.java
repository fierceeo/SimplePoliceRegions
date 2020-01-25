package com.voidcitymc.www;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import java.util.UUID;
//import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class Police implements CommandExecutor {

// This method is called, when somebody uses our command
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

if (sender instanceof Player == false) {
	sender.sendMessage("[Police] You must be a player to run commands, sorry");
}
	
if (args.length == 0 && sender instanceof Player) {
	Player player = (Player) sender;
	worker work = new worker();
	if (work.isPoliceInGeneral(player.getUniqueId().toString(), player.getWorld().toString())) {
		//Test if player is police


		player.sendMessage("[Police]");
		player.sendMessage("To arrest someone simply attack them with a blaze rod");
		if (player.hasPermission("police.add")) {
			player.sendMessage("/police add (username) (region)");
		}
		if (player.hasPermission("police.remove")) {
			player.sendMessage("/police remove (username) (region)");
		}
		        player.sendMessage("/police unjail (username)");
	}
	
}
	
if (args.length > 0) {

if (sender instanceof Player) {
Player player = (Player) sender;
worker work = new worker();


//String uuidString = player.getUniqueId().toString();
//convert string to player

//creates string called playerFromCommand that is the selected player uuid
String playerFromCommand = "null";
if (args.length > 1) {
	playerFromCommand = Bukkit.getPlayer(args[1]).getUniqueId().toString();	
} else {
	playerFromCommand = player.getUniqueId().toString();
}
boolean done = false;
// convert string to uuid: UUID returnUUID = UUID.fromString(uuidVarname);




//unjail

if (work.isPoliceInGeneral(player.getUniqueId().toString(), player.getWorld().toString())) {
	if (args[0].equalsIgnoreCase("unjail")) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials:unjail "+args[1]);
		done = true;
	}
}

//add police
if (player.hasPermission("police.add")) {
// need to check if player has perm ^
if (args[0].equalsIgnoreCase("add")) {
	if (playerFromCommand != "null") {
		work.addPolice(playerFromCommand, args[2], player.getWorld().toString());	
		player.sendMessage("Added "+args[1]+" as a police officer!");
		} else if (playerFromCommand == "null") {
		player.sendMessage("You need to specify a player!");
	}
	done = true;
}

}

//Remove
if (player.hasPermission("police.remove")) {

if (args[0].equalsIgnoreCase("remove")) {
	if (playerFromCommand != "null") {
		work.removePolice(playerFromCommand, args[2], player.getWorld().toString());	
		player.sendMessage("Removed "+args[1]+" as a police officer!");
		} else if (playerFromCommand == "null") {
		player.sendMessage("You need to specify a player!");
	}
	done = true;
}

}

//help
if (work.isPoliceInGeneral(player.getUniqueId().toString(), player.getWorld().toString())) {
	//Test if player is police

if (args[0].equalsIgnoreCase("help")) {
	player.sendMessage("[Police]");
	player.sendMessage("To arrest someone simply attack them with a blaze rod");
	if (player.hasPermission("police.add")) {
		player.sendMessage("/police add (username) (region)");
	}
	if (player.hasPermission("police.remove")) {
		player.sendMessage("/police remove (username) (region)");
	}
	        player.sendMessage("/police unjail (username)");
	done = true;
}
}





}

}
// If the command is used correctly return true
return true;
}
}
