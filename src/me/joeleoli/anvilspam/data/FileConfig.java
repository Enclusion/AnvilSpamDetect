package me.joeleoli.anvilspam.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileConfig {

    private File file;
    private FileConfiguration config;
    
    public FileConfig(Plugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();

            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                }
                catch (IOException e) {
                    plugin.getLogger().severe("Failed to create new file " + fileName);
                }
            }
            else {
                plugin.saveResource(fileName, false);
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public int getInt(String path) {
        if (this.config.contains(path)) {
            return this.config.getInt(path);
        }

        return 0;
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public String getString(String path) {
        if (this.config.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', this.config.getString(path));
        }

        return null;
    }

}