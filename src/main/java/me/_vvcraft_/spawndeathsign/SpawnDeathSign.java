package me._vvcraft_.spawndeathsign;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpawnDeathSign extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("##########################################");
        getLogger().info("SpawnDeathSign is ON");
        getLogger().info("##########################################");
    }

    @Override
    public void onDisable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("##########################################");
        getLogger().info("SpawnDeathSign is OFF");
        getLogger().info("##########################################");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Get coord of death player
        double x = event.getEntity().getLocation().getX();
        double y = event.getEntity().getLocation().getY();
        double z = event.getEntity().getLocation().getZ();
        String worldName = event.getEntity().getWorld().getName();

        // Get death player name
        String playerName = player.getName();

        // Get date of death
        Date deathDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = formatter.format(deathDate);

        // Get killer name
        Entity killer = player.getKiller();
        String killerName = "Environnement";
        if (killer instanceof Player) {
            killerName = ((Player) killer).getName();
        } else if (killer != null) {
            killerName = killer.getType().name();
        }

        // Create the sign
        Location deathLocation = new Location(event.getEntity().getWorld(), x, y, z);
        createDeathSign(deathLocation, playerName, formattedDate, killerName);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            if (sign.getLine(0).equalsIgnoreCase("[Death]")) {
                event.setCancelled(true);
            }
        }
    }

    private void createDeathSign(Location location, String playerName, String deathDate, String killerName) {
        location.getBlock().setType(Material.OAK_SIGN);
        Sign sign = (Sign) location.getBlock().getState();
        sign.setLine(0, "RIP: " + playerName);
        sign.setLine(1, "Date: " + deathDate);
        sign.setLine(2, "death by:");
        sign.setLine(3, killerName);
        sign.update();
    }
}

