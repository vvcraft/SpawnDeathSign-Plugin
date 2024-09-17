package space.vvcraft.spawnDeathSign;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]){

        Player player = (Player) sender;
        CustomConfig.reload();

        return true;
    }
}
