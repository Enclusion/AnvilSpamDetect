package me.joeleoli.anvilspam;

import me.joeleoli.anvilspam.data.FileConfig;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class DetectConfiguration {

    public static int VELOCITY_TO_FLAG = 3;

    public static String FLAG_PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "!" + ChatColor.GRAY + "] ";
    public static String FLAG_MESSAGE = ChatColor.RED + "%PLAYER%" + ChatColor.GRAY + " has been flagged for anvil spamming " + ChatColor.DARK_GRAY + "(" + ChatColor.RED + "%VELOCITY%" + ChatColor.WHITE + "VL" + ChatColor.DARK_GRAY + ")";

    private FileConfig config;

    DetectConfiguration(Plugin plugin) {
        this.config = new FileConfig(plugin, "config.yml");

        this.load();
    }

    private void load() {
        if (this.config.contains("SETTINGS.VELOCITY_TO_FLAG")) {
            VELOCITY_TO_FLAG = this.config.getInt("SETTINGS.VELOCITY_TO_FLAG");
        }

        if (this.config.contains("MESSAGES.FLAG_PREFIX")) {
            FLAG_PREFIX = this.config.getString("MESSAGES.FLAG_PREFIX");
        }

        if (this.config.contains("MESSAGES.FLAG_MESSAGE")) {
            FLAG_MESSAGE = this.config.getString("MESSAGES.FLAG_MESSAGE");
        }
    }

}