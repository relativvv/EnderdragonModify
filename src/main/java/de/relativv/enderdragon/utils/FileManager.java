package de.relativv.enderdragon.utils;

import de.relativv.enderdragon.main.EDModify;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static File rgs = new File("plugins/EDmodify", "regions.yml");
    public static FileConfiguration regionCfg = YamlConfiguration.loadConfiguration(rgs);

    public static void saveRegions() {
        try {
            regionCfg.save(rgs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void registerRegions() {
        for(int i = 0; i < regionCfg.getKeys(false).size(); i++) {
                EDModify.getInstance().regions.add(new Cuboid(getLoc(i, "pos1"), getLoc(i, "pos2")));
        }
    }


    public static void registerPortalFrames() {
        for(int i = 0; i < regionCfg.getKeys(false).size(); i++) {
                for (int f = 1; f <= Setup.getMaxEyes(i); f++) {
                    EDModify.getInstance().portalFrames.add(getPortalFrame(i, f));
            }
        }
    }

    public static Location getPortalFrame(int id, int frameNumber) {
        return new Location(Bukkit.getWorld(regionCfg.getString(id + ".frame" + frameNumber + ".world")),
                regionCfg.getInt(id + ".frame" + frameNumber + ".x"),
                regionCfg.getInt(id + ".frame" + frameNumber + ".y"),
                regionCfg.getInt(id + ".frame" + frameNumber + ".z"));
    }


    public static Location getLoc(int id, String name) {
        return new Location(Bukkit.getWorld(regionCfg.getString(id + "." + name + ".world")),
                regionCfg.getInt(id + "." + name + ".x"),
                        regionCfg.getInt(id + "." + name + ".y"),
                                regionCfg.getInt(id + "." + name + ".z"));
    }




















    public static File getFile() {
        return new File("plugins/EDModify", "config.yml");
    }
    public static FileConfiguration getCfg() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public static void setDefault() {
        FileConfiguration cfg = getCfg();
        cfg.options().copyDefaults(true);
        cfg.addDefault("prefix", "&8▌ &dEnderDragon &8»");
        cfg.addDefault("noPermission", "&cDu hast keine Berechtigung diese Aktion durchzuführen!");

        cfg.addDefault("defineRegion.pos1", "&7Du hast die &b1. &7Position gesetzt!");
        cfg.addDefault("defineRegion.pos2", "&7Du hast die &b2. &7Position gesetzt!");
        cfg.addDefault("setup.forbidden", "&cDiese Aktion ist während des Setups gesperrt!");
        cfg.addDefault("setup.notInSetup", "&cDu bist gerade in keinem Setup!");
        cfg.addDefault("setup.aborted", "&cDu hast das Setup abgebrochen!");

        cfg.addDefault("setup.step1.start", "&7Definiere die Region, in der der Enderdrache spawnen soll und die Endportalrahmen gefüllt werden müssen. &8(&b1&7/&b3&8)");
        cfg.addDefault("setup.step1.confirm", "&7Bist du zufrieden mit deiner Region &8- &7/edm next");
        cfg.addDefault("setup.step1.end", "&aSehr gut! Schritt 1 wäre damit abehakt!");
        cfg.addDefault("setup.step1.noRegion", "&cDu hast noch keine Region festgelegt.");
        cfg.addDefault("setup.step1.isAlreadyInRegion", "&cDieser Block ist bereits in einer Region, du kannst ihn nicht markieren.");
        cfg.addDefault("setup.step1.notEnoughSpace", "&cZwischen den beiden Positionen muss mindestens ein Höhenunterschied von %space% Blöcken sein!");
        cfg.addDefault("setup.step1.space", 50);



        cfg.addDefault("setup.step2.max", 8);
        cfg.addDefault("setup.step2.start", "&7Als nächstes platziere bitte die Enderportalrahmen an den gewünschten Positionen. &8(&b2&7/&b3&8)");
        cfg.addDefault("setup.step2.end", "&aSehr gut! Schritt 2 wäre damit abehakt!");
        cfg.addDefault("setup.step2.currentAmountPlaced", "&aEndportalrahmen platziert &7- &8(&b%number%&7/&b%max%&8)");
        cfg.addDefault("setup.step2.currentAmountBreaked", "&aEndportalrahmen zerstört &7- &8(&b%number%&7/&b%max%&8)");
        cfg.addDefault("setup.step2.blockNotInRegion", "&cDu kannst nur Endportalrahmen in deiner Region platzieren/zerstören");
        cfg.addDefault("setup.step2.maximumPlaced", "&aGlückwunsch, du hast alle %max% Endportalrahmen platziert, /edm next &ain den Chat um zum nächsten Schritt zu gelangen!");
        cfg.addDefault("setup.step2.notAllPlaced", "&cDu hast noch nicht genug Endportalrahmen platziert!");
        cfg.addDefault("setup.step3.inventory.mainTitle", "&8> &5Enderdrache");
        cfg.addDefault("setup.step3.inventory.startAttackTitle", "&8> &aStartangriff");
        cfg.addDefault("setup.step3.inventory.attacksTitle", "&8> &4Angriffsabläufe");
        cfg.addDefault("setup.step3.inventory.allAttacksTitle", "&8> &cWähle einen Angriff");


        cfg.addDefault("setup.step3.inventory.minus", "&4-");
        cfg.addDefault("setup.step3.inventory.plus", "&a+");
        cfg.addDefault("setup.step3.inventory.health", "&cLeben");
        cfg.addDefault("setup.step3.inventory.maximumHealth", 1000);
        cfg.addDefault("setup.step3.inventory.maxhealth", "&cMaximale Leben");
        cfg.addDefault("setup.step3.inventory.maximumAbsorption", 100);
        cfg.addDefault("setup.step3.inventory.absorption", "&6Absorption");
        cfg.addDefault("setup.step3.inventory.silent", "&5Stille...");
        cfg.addDefault("setup.step3.inventory.glowing", "&eGlühen");
        cfg.addDefault("setup.step3.inventory.ai", "&cKünstliche Intelligenz");
        cfg.addDefault("setup.step3.inventory.despawnFarAway", "&4Despawn bei großer Entfernung");
        cfg.addDefault("setup.step3.inventory.startPhase", "&dStartphase");
        cfg.addDefault("setup.step3.inventory.customizeAttack", "&4Angriffe bearbeiten");
        cfg.addDefault("setup.step3.inventory.passenger", "&7Letzter Setzer des Enderauges Reiten");
        cfg.addDefault("setup.step3.inventory.itemLore", "&7Aktuell&8: &e%state%");
        cfg.addDefault("setup.step3.inventory.enabled", "&aAKTIVIERT");
        cfg.addDefault("setup.step3.inventory.disabled", "&4DEAKTIVIERT");
        cfg.addDefault("setup.step3.inventory.finishItem", "&aFertig!");
        cfg.addDefault("setup.step3.inventory.return", "&cZurück!");
        cfg.addDefault("setup.step3.inventory.chooseDisplayNameMSG", "&7Zuletzt musst du noch einen Namen für den Enderdrachen angeben! Schreibe ihn bitte einfach in den Chat &c(Du kannst auch Farbcodes verwenden).");
        cfg.addDefault("setup.step3.inventory.chooseDisplayNameAccept", "&aName des Drachen geändert&8: &r%name%!");
        cfg.addDefault("setup.step3.finish", "&aDas Setup für Region %id% ist jetzt abgeschlossen, Glückwunsch!");

        cfg.addDefault("setup.step3.inventory.randomizeAttacks", "&9Zufällige Reihenfolge");
        cfg.addDefault("setup.step3.inventory.timerUntilNext", "&eZeit bis zum nächsten Angriff");
        cfg.addDefault("setup.step3.inventory.maximumTimeUntilNextAttack", 120);
        cfg.addDefault("setup.step3.inventory.healthError", "&cDie normale HP muss kleiner oder gleich der maximalen HP sein!");

        cfg.addDefault("setup.step3.inventory.strafing", "&2Unter Beschuss nehmen");
        cfg.addDefault("setup.step3.inventory.searching", "&3Zielsuchend");
        cfg.addDefault("setup.step3.inventory.shouting", "&cRoar bevor Angriff");
        cfg.addDefault("setup.step3.inventory.hovering", "&cGleiten");
        cfg.addDefault("setup.step3.inventory.circling", "&9Kreiseln");
        cfg.addDefault("setup.step3.inventory.chargePlayer", "&7Spieler angreifen");
        cfg.addDefault("setup.step3.inventory.firebreath", "&6Feuerspeien");


        cfg.addDefault("modify.finish", "&aÄnderungen gespeichert!");
        cfg.addDefault("modify.noExist", "&cDiese Region existiert nicht!");

        cfg.addDefault("getRegionID.finish", "&cRegion %id% gelöscht!");
        cfg.addDefault("getCurrentRegion.msg", "&7Du befindest dich in Region &e%id%&7!");

        cfg.addDefault("portalframe.filled", "&7Du hast ein Enderauge in Region &c%region% &7platziert &8(&b%current%&8/&b%max%&8)");
        cfg.addDefault("portalframe.alreadyfilled", "&cDieser Endportalrahmen enthält bereits ein Enderauge!");

        cfg.addDefault("enderdragon.alreadyexists", "&cAus Performancegründen kannst du während ein Enderdrache existiert kein Enderauge in einen Endportalrahmen platzieren!");
        cfg.addDefault("enderdragon.coming", "&4!!! &cIn deiner Region wurde &r%name% &cerschaffen, nimm dich in acht! &4!!!");
        cfg.addDefault("enderdragon.teleportBackOnLeave", false);
        cfg.addDefault("enderdragon.leavingArea", "&cDer Enderdrache war im Begriff seinen Bereich zu verlassen, er wurde zurück teleportiert.");
        cfg.addDefault("enderdragon.canDestroy", false);

        cfg.addDefault("enderdragon.comingAllMsg", "&r%name% &cwurde erschaffen, nimm dich in acht!");
        cfg.addDefault("enderdragon.comingAll", true);


        try {
            cfg.save(getFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void readConfig() {
        FileConfiguration cfg = getCfg();
        EDModify.getInstance().pr = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix") + " §r");
        EDModify.getInstance().noPerm = ChatColor.translateAlternateColorCodes('&', cfg.getString("noPermission"));
    }

}
