package me._vvcraft_.spawndeathsign;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

    private static File file;
    private static FileConfiguration fileConfig;

    public static void setup(){
        // Create file
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SpawnDeathSign").getDataFolder(), "config.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return fileConfig;
    }

    public static void save(){
        try {
            fileConfig.save(file);
        }catch (IOException e){
            System.out.println("Can't save file");
        }
    }

    public static void reload(){
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
