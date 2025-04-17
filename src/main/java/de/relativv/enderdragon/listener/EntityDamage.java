package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {

    private EDModify ed;
    public EntityDamage(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onDMG(EntityDamageEvent e) {
        if(e.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon) e.getEntity();

            if(e.getEntity().getWorld() != Bukkit.getWorld("world_the_end")) {
                if(ed.bar != null) {
                    ed.bar.setProgress(dragon.getHealth() / dragon.getMaxHealth());
                }
            }
        }
    }
}
