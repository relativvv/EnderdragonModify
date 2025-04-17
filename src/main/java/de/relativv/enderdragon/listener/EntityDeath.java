package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {

    private EDModify ed;
    public EntityDeath(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof EnderDragon) {

            if(e.getEntity().getWorld() != Bukkit.getWorld("world_the_end")) {
                if(ed.bar != null) {
                    ed.bar.removeAll();
                    ed.bar.setVisible(false);
                    ed.bar = null;
                }
            }
        }
    }

}
