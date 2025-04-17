package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private EDModify ed;
    public BlockBreak(EDModify ed) {
        this.ed = ed;
    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if(ed.portalFrames.contains(e.getBlock().getLocation())) {
            if (!p.isOp()) {
                e.setCancelled(true);
            }
        }

        if(ed.isInSetup.contains(p)) {
            Setup setup = Setup.getSetup(p);
            if (setup != null) {
                if (setup.getStep() == 2) {
                    if (e.getBlock().getType() == Material.END_PORTAL_FRAME) {
                        if (BlockPlace.currentAmount.containsKey(p)) {
                            if (isInCurrentRegion(p, e.getBlock().getLocation())) {
                                if (setup.getEndPortalFrames().contains(e.getBlock().getLocation())) {
                                        int curr = BlockPlace.currentAmount.get(p);
                                        curr--;
                                        BlockPlace.currentAmount.remove(p);
                                        BlockPlace.currentAmount.put(p, curr);

                                        setup.endPortalFrames.remove(e.getBlock().getLocation());

                                        String currBreakedMsg = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.currentAmountBreaked"));
                                        p.sendMessage(ed.pr + currBreakedMsg.replaceAll("%number%", BlockPlace.currentAmount.get(p) + "").replaceAll("%max%", FileManager.getCfg().getInt("setup.step2.max") + ""));

                                    }
                                }
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
