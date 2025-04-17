package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.commands.Edm;
import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private EDModify ed;
    public PlayerQuit(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (ed.isInSetup.contains(p)) {

            Setup setup = Setup.getSetup(p);
            if (setup != null) {
                p.getInventory().clear();
                p.getInventory().setContents(Edm.saveInv.get(p));
                Edm.saveInv.remove(p);
                for (Location locs : setup.endPortalFrames) {
                    locs.getBlock().setType(Material.AIR);
                }
                setup.currentRegion.remove(p);
                setup.endPortalFrames.clear();
                setup.pos1.remove(p);
                setup.pos2.remove(p);
                setup.currentSetup.remove(p);
                setup = null;
            }
        }
    }
}
