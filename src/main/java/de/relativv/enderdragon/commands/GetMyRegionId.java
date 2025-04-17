package de.relativv.enderdragon.commands;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetMyRegionId implements CommandExecutor {

    private EDModify ed;
    public GetMyRegionId(EDModify ed) {
        this.ed = ed;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("edm.getRegion")) {
                String id = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("getCurrentRegion.msg"));
                int current = getRegion(p.getLocation());
                if(current == -1) {
                    p.sendMessage(id.replaceAll("%id%", "Keine"));
                } else {
                    p.sendMessage(ed.pr + id.replaceAll("%id%", current + ""));
                }
            } else {
                p.sendMessage(ed.noPerm);
            }

        }
        return true;
    }



    private int getRegion(Location loc) {
        for(int i = 0; i < ed.regions.size(); i++) {
            Cuboid c = ed.regions.get(i);
            if(c.contains(loc)) {
                return i;
            }
        }
        return -1;
    }
}
