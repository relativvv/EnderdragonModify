package de.relativv.enderdragon.main;

import de.relativv.enderdragon.commands.Edm;
import de.relativv.enderdragon.commands.GetMyRegionId;
import de.relativv.enderdragon.listener.*;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class EDModify extends JavaPlugin {

    public static EDModify instance;
    public String pr;
    public String noPerm;

    public HashMap<Player, Integer> modifyRegion = new HashMap<>();

    public ArrayList<Player> isInSetup = new ArrayList<>();

    public ArrayList<Cuboid> regions = new ArrayList<>();
    public ArrayList<Location> portalFrames = new ArrayList<>();

    public static ArrayList<Setup> setups = new ArrayList<Setup>();

    public BossBar bar;

    @Override
    public void onEnable() {
        instance = this;

        FileManager.setDefault();
        FileManager.readConfig();

        FileManager.registerRegions();
        FileManager.registerPortalFrames();

        sendConsoleMessage("§e============== §aEDModify §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§a§lLOADED");
        sendConsoleMessage(" ");
        register();
        for(World w : Bukkit.getWorlds()) {
            for(Entity e : w.getEntities()) {
                if(e instanceof EnderDragon) {
                    if(e.getWorld() != Bukkit.getWorld("world_the_end")) {
                        e.remove();
                    }
                }
            }
        }
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §aEDModify §e=============");
    }



    @Override
    public void onDisable() {
        sendConsoleMessage("§e============== §aEDModify §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§4§lUNLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §aEDModify §e=============");
    }


    public static EDModify getInstance() {
        return instance;
    }

    public void sendConsoleMessage(String msg) {
        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        cs.sendMessage(msg);
    }


    private void register() {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        this.getCommand("edm").setExecutor(new Edm(this));
        sendConsoleMessage("§bCommand loaded §8(§eedm§8)");
        this.getCommand("getMyRegionId").setExecutor(new GetMyRegionId(this));
        sendConsoleMessage("§bCommand loaded §8(§eGetMyRegionId§8)");

        sendConsoleMessage(" ");

        sendConsoleMessage("§bListener loaded §8(§eAsyncPlayerChat§8)");
        pm.registerEvents(new AsyncPlayerChat(this), this);
        sendConsoleMessage("§bListener loaded §8(§eBlockBreak§8)");
        pm.registerEvents(new BlockBreak(this), this);
        sendConsoleMessage("§bListener loaded §8(§eBlockPlace§8)");
        pm.registerEvents(new BlockPlace(this), this);
        sendConsoleMessage("§bListener loaded §8(§ePlayerInteract§8)");
        pm.registerEvents(new PlayerInteract(this), this);
        sendConsoleMessage("§bListener loaded §8(§eInventoryClick§8)");
        pm.registerEvents(new InventoryClick(this), this);
        sendConsoleMessage("§bListener loaded §8(§eInventoryClose§8)");
        pm.registerEvents(new InventoryClose(this), this);
        sendConsoleMessage("§bListener loaded §8(§ePlayerQuit§8)");
        pm.registerEvents(new PlayerQuit(this), this);
        sendConsoleMessage("§bListener loaded §8(§eEntityExplode§8)");
        pm.registerEvents(new EntityExplode(this), this);
        sendConsoleMessage("§bListener loaded §8(§eEntityDeath§8)");
        pm.registerEvents(new EntityDeath(this), this);
        sendConsoleMessage("§bListener loaded §8(§eEntityDamage§8)");
        pm.registerEvents(new EntityDamage(this), this);
    }

}
