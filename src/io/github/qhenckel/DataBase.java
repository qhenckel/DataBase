package io.github.qhenckel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DataBase{

	Logger logger = Bukkit.getLogger();
	Plugin plugin;
	String filename;
	private FileConfiguration customConfig = null;
	private File custdb = null;
	//             plugin,   filename no ".yml"
	public DataBase(Plugin p, String fn){
		plugin = p;
		filename = fn;
	}
	
	public FileConfiguration getConfig() {
        if (customConfig == null) {
            this.reloadDataBase();
        }
        return customConfig;
    }
	
	public void reloadDataBase() {
		
	    if (custdb == null) {
	    	custdb = new File(plugin.getDataFolder(), filename + ".yml");
	    }
	    customConfig = YamlConfiguration.loadConfiguration(custdb);
	 
	    InputStream defConfigStream = plugin.getResource(filename + ".yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        customConfig.setDefaults(defConfig);
	    }
	}
	
	public void saveDataBase() {
	    if (customConfig == null || custdb == null) {
	        return;
	    }
	    try {
	    	getConfig().save(custdb);
	    } catch (IOException ex) {
	        logger.severe("Could not save config to " + custdb);
	    }
	}
	
	public void saveDefaultDataBase() {
	    if (custdb == null) {
	    	custdb = new File(plugin.getDataFolder(), filename + ".yml");
	    }
	    if (!custdb.exists()) {            
	         plugin.saveResource(filename + ".yml", false);
	     }
	}
}
