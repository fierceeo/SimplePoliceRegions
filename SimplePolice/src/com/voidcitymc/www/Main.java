package com.voidcitymc.www;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
private static Main instance;




File DataFile;
static FileConfiguration Data;


//instead of this simply use Main.Data
public FileConfiguration getData() {
    return this.Data;
}


private void createData() {
    DataFile = new File(getDataFolder(), "data.yml");
    if (!DataFile.exists()) {
        DataFile.getParentFile().mkdirs();
        saveResource("data.yml", false);
     }
    Data = new YamlConfiguration();
}




//enabled
@Override
public void onEnable() {
	getServer().getPluginManager().registerEvents(new PoliceListener(), this);
	//create config:
	this.createData();
	
	instance = this;
	
	this.getCommand("police").setExecutor(new Police());
	
	System.out.println("ramdon_person's Police Plugin Has Been Enabled!");
}

//get the instance thingy (or "this")

public static Main getInstance() {
	return instance;
}

//disabled
@Override
public void onDisable() {
	System.out.println("Thanks for using ramdon_person's police plugin!");
	System.out.println("-- Saving Data --");
	this.saveConfig();
	System.out.println("-- All data saved! --");
}
}
