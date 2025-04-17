package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;

public class BlockPlace implements Listener {

    private EDModify ed;
    public BlockPlace(EDModify ed) {
        this.ed = ed;
    }

    public static HashMap<Player, Integer> currentAmount = new HashMap<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(ed.isInSetup.contains(p)) {
            Setup setup = Setup.getSetup(p);
            if (setup != null) {
                if (setup.getStep() == 2) {
                    if (e.getBlockPlaced().getType() == Material.END_PORTAL_FRAME) {
                        if (currentAmount.containsKey(p)) {
                            if (isInCurrentRegion(p, e.getBlockPlaced().getLocation())) {

                            if(currentAmount.get(p) == FileManager.getCfg().getInt("setup.step2.max")) {
                                e.setCancelled(true);
                                String rdy = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.maximumPlaced"));
                                p.sendMessage(ed.pr + rdy.replaceAll("%max%", FileManager.getCfg().getInt("setup.step2.max") + ""));
                                return;
                            }

                                int curr = currentAmount.get(p);
                                curr++;
                                currentAmount.remove(p);
                                currentAmount.put(p, curr);

                                setup.endPortalFrames.add(e.getBlockPlaced().getLocation());


                                String currPlacedMsg = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.currentAmountPlaced"));
                                p.sendMessage(ed.pr + currPlacedMsg.replaceAll("%number%", currentAmount.get(p) + "").replaceAll("%max%", FileManager.getCfg().getInt("setup.step2.max") + ""));


                                if (currentAmount.get(p) == FileManager.getCfg().getInt("setup.step2.max")) {
                                    String rdy = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.maximumPlaced"));
                                    p.sendMessage(ed.pr + rdy.replaceAll("%max%", FileManager.getCfg().getInt("setup.step2.max") + ""));
                                    return;
                                }

                            } else {
                                e.setCancelled(true);
                                String errReg = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.blockNotInRegion"));
                                p.sendMessage(ed.pr + errReg);
                            }
                        } else {
                            currentAmount.put(p, 1);
                            String currPlacedMsg = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.currentAmountPlaced"));
                            p.sendMessage(ed.pr + currPlacedMsg.replaceAll("%number%", currentAmount.get(p) + "").replaceAll("%max%", FileManager.getCfg().getInt("setup.step2.max") + ""));
                        }
                    }
                }
        }
        }

    }

    private boolean isInCurrentRegion(Player p, Location loc) {
        if(ed.isInSetup.contains(p)) {
            Setup setup = Setup.getSetup(p);
            if (setup.currentRegion.containsKey(p)) {
                if (setup.currentRegion.get(p).contains(loc)) {
                    return true;
                }
            }
        }
        return false;
    }




}
