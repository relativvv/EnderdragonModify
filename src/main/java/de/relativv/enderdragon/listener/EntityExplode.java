package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.FileManager;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

    private EDModify ed;
    public EntityExplode(EDModify ed) {
        this.ed = ed;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if(!FileManager.getCfg().getBoolean("enderdragon.canDestroy")) {
            if (e.getEntity() instanceof EnderDragon || e.getEntity() instanceof EnderDragonPart) {
                e.blockList().clear();
            }
        }
    }
}
