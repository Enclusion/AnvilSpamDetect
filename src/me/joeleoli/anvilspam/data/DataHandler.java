package me.joeleoli.anvilspam.data;

import me.joeleoli.anvilspam.DetectConfiguration;
import me.joeleoli.anvilspam.event.SpamDetectEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataHandler {

    private Plugin plugin;
    private Map<UUID, Integer> velocities = new HashMap<>();
    private Map<UUID, Integer> interacts = new HashMap<>();

    public DataHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void handle(Player player) {
        Integer interactCount = this.interacts.get(player.getUniqueId());

        if (interactCount == null) {
            interactCount = 1;
        }
        else {
            interactCount++;
        }

        if (interactCount < DetectConfiguration.VELOCITY_TO_FLAG) {
            this.interacts.put(player.getUniqueId(), interactCount);

            new BukkitRunnable() {
                public void run() {
                    if (interacts.containsKey(player.getUniqueId())) {
                        Integer updateCount = interacts.get(player.getUniqueId()) - 1;

                        if (updateCount <= 0) {
                            interacts.remove(player.getUniqueId());
                        }
                        else {
                            interacts.put(player.getUniqueId(), updateCount);
                        }
                    }
                }
            }.runTaskLater(this.plugin, 20L);
        }
        else {
            this.interacts.remove(player.getUniqueId());

            Integer velocityCount = this.velocities.get(player.getUniqueId());

            if (velocityCount == null) {
                velocityCount = 1;
            }
            else {
                velocityCount++;
            }

            this.velocities.put(player.getUniqueId(), velocityCount);

            SpamDetectEvent event = new SpamDetectEvent(Bukkit.getPlayer(player.getUniqueId()), velocityCount);

            Bukkit.getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                this.sendAlert(player, velocityCount);
            }
        }
    }

    public void clear(UUID uuid) {
        this.interacts.remove(uuid);
    }

    private void sendAlert(Player player, int velocity) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("asd.alert")) {
                p.sendMessage(DetectConfiguration.FLAG_PREFIX + DetectConfiguration.FLAG_MESSAGE.replace("%PLAYER%", player.getName()).replace("%VELOCITY%", String.valueOf(velocity)));
            }
        }
    }

}