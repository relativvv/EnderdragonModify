package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClose implements Listener {

    private EDModify ed;
    public InventoryClose(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if(ed.modifyRegion.containsKey(p)) {
            if(!InventoryClick.bypass.contains(p)) {
                if (Setup.getHealthFromFile(getRegion(p.getLocation())) <= Setup.getMaxHealthFromFile(getRegion(p.getLocation()))) {

                    ed.modifyRegion.remove(p);

                    String modifyFinished = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("modify.finish"));
                    p.sendMessage(ed.pr + modifyFinished);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                } else {
                    String err = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.healthError"));
                    p.sendMessage(ed.pr + err);
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.5F);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.openInventory(e.getInventory());
                        }
                    }.runTaskLater(ed, 5);

                }
            }
        }

        if(ed.isInSetup.contains(p)) {
            if(!InventoryClick.bypass.contains(p)) {
            if(e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.attacksTitle")))
            || e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.mainTitle")))
            || e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startAttackTitle")))
            || e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.allAttacksTitle")))) {

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.openInventory(e.getInventory());
                    }
                }.runTaskLater(ed, 5);

            }
            }
        }
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
