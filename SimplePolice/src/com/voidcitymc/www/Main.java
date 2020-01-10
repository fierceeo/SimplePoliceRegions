package com.voidcitymc.www;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
private static Main instance;




File DataFile;
FileConfiguration Data;




public void createData() {
    DataFile = new File(getDataFolder(), "data.yml");
    if (!DataFile.exists()) {
        DataFile.getParentFile().mkdirs();
        saveResource("data.yml", false);
     }
    Data = YamlConfiguration.loadConfiguration(DataFile);
}

public static Main getInstance() {
	return instance;
}

public void SaveTheConfig() {
	try {
		Data.save(DataFile);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//enabled
@Override
public void onEnable() {
	getServer().getPluginManager().registerEvents(new PoliceListener(), this);
	//create config;
	createData();
	
	instance = this;
	
	this.getCommand("police").setExecutor(new Police());
	
	System.out.println("ramdon_person's Police Plugin Has Been Enabled!");
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
