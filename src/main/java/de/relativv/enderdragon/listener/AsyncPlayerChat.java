package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.commands.Edm;
import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncPlayerChat implements Listener {

    private EDModify ed;
    public AsyncPlayerChat(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if(ed.isInSetup.contains(p)) {
            if(InventoryClick.bypass.contains(p)) {
                e.setCancelled(true);
                Setup setup = Setup.getSetup(p);
                if (setup != null) {
                    setup.setDisplayName(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
                    InventoryClick.bypass.remove(p);
                    String chooseNameAccept = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chooseDisplayNameAccept"));
                    p.sendMessage(ed.pr + chooseNameAccept.replaceAll("%name%", ChatColor.translateAlternateColorCodes('&', e.getMessage())));

                    ed.regions.add(new Cuboid(setup.pos1.get(p), setup.pos2.get(p)));
                    setup.addRegion(setup.getPos1(), setup.getPos2(), setup.endPortalFrames);

                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

                    p.sendMessage(" ");
                    p.sendMessage(" ");
                    p.sendMessage(" ");

                    String finished = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.finish"));
                    p.sendMessage(ed.pr + finished.replaceAll("%id%", setup.getId() + ""));
                    p.getInventory().clear();
                    p.getInventory().setContents(Edm.saveInv.get(p));
                    Edm.saveInv.remove(p);
                    setup.currentSetup.remove(p);
                    EDModify.setups.remove(setup);
                    ed.isInSetup.remove(p);
                }
            }
        }
    }
}
