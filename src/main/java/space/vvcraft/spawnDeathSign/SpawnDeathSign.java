package space.vvcraft.spawnDeathSign;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
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

        //Steup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        CustomConfig.setup();
        CustomConfig.get().addDefault("First_line:", "RIP: ");
        CustomConfig.get().addDefault("Second_line:", "date: ");
        CustomConfig.get().addDefault("Third_line:", "died of: ");
        CustomConfig.get().addDefault("fireworks:", "true");
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();

        getCommand("reload").setExecutor(new ReloadCommand());
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
            killerName = killer.getName();
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

        sign.setLine(0, CustomConfig.get().getString("First_line:") + playerName);
        sign.setLine(1, CustomConfig.get().getString("Second_line:") + deathDate);
        sign.setLine(2, CustomConfig.get().getString("Third_line:"));
        sign.setLine(3, killerName);
        sign.update();

        if (CustomConfig.get().getString("fireworks:").equalsIgnoreCase("true")){
            Bukkit.getWorld("world").spawnEntity(location, EntityType.FIREWORK_ROCKET);
        }
    }
}
