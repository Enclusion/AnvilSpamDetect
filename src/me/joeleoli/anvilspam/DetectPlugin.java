package me.joeleoli.anvilspam;

import me.joeleoli.anvilspam.data.DataHandler;
import me.joeleoli.anvilspam.listener.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;

public class DetectPlugin extends JavaPlugin {

    private static DetectPlugin instance;

    private DataHandler dataHandler;

    public void onEnable() {
        instance = this;

        this.dataHandler = new DataHandler(this);

        new DetectConfiguration(this);
        new PlayerListener(this);
    }

    public static DetectPlugin getInstance() {
        return instance;
    }

    public DataHandler getDataHandler() {
        return this.dataHandler;
    }

}