package de.relativv.enderdragon.utils;

import de.relativv.enderdragon.main.EDModify;
import org.bukkit.*;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Setup {

    private int maxHealth;
    private int absorption;
    private int health;
    private String displayName;
    private boolean glowing;
    private boolean ai;
    private String beginPhase;
    private boolean silent;
    private boolean removeWhenFarAway;
    private int timeUntilNextAttack;
    private boolean passenger;

    private int eyesPlaced;
    private int max;

    private boolean randomizeAttacks;

    private String phase1;
    private String phase2;
    private String phase3;
    private String phase4;
    private String phase5;

    private Player p;
    private int id;
    private int step;

    public HashMap<Player, Integer> currentSetup = new HashMap<>();
    public HashMap<Player, Cuboid> currentRegion = new HashMap<>();
    public HashMap<Player, Location> pos1 = new HashMap<Player, Location>();
    public HashMap<Player, Location> pos2 = new HashMap<Player, Location>();

    public ArrayList<Location> endPortalFrames = new ArrayList<>();

    public Setup(Player p) {
        this.p = p;
        this.id = FileManager.regionCfg.getKeys(false).size();
        this.step = 1;

        maxHealth = 200;
        health = 200;
        absorption = 0;
        displayName = "EnderDragon";
        glowing = false;
        ai = true;
        beginPhase = "circling";
        silent = true;
        removeWhenFarAway = false;
        randomizeAttacks = false;
        timeUntilNextAttack = 20*11;
        eyesPlaced = 0;
        max = FileManager.getCfg().getInt("setup.step2.max");
        passenger = false;

        phase1 = "strafing";
        phase2 = "searching";
        phase3 = "firebreath";
        phase4 = "shouting";
        phase5 = "strafing";

        currentSetup.put(p, id);
        EDModify.setups.add(this);
    }


    public Location getPos1() {
        if(pos1.containsKey(p)) {
            return pos1.get(p);
        }
        return null;
    }

    public Location getPos2() {
        if(pos2.containsKey(p)) {
            return pos2.get(p);
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public int getStep() {
        return step;
    }

    public void addStep() {
        this.currentSetup.remove(p);
        this.currentSetup.put(p, step);
        step++;
    }

    public Player getP() {
        return p;
    }

    public ArrayList<Location> getEndPortalFrames() {
        return endPortalFrames;
    }


    public void addRegion(Location loc1, Location loc2, ArrayList<Location> frames) {

        FileManager.regionCfg.set(this.id + ".pos1.world", loc1.getWorld().getName());
        FileManager.regionCfg.set(this.id + ".pos1.x", loc1.getBlockX());
        FileManager.regionCfg.set(this.id + ".pos1.y", loc1.getBlockY());
        FileManager.regionCfg.set(this.id + ".pos1.z", loc1.getBlockZ());

        FileManager.regionCfg.set(this.id + ".pos2.world", loc2.getWorld().getName());
        FileManager.regionCfg.set(this.id + ".pos2.x", loc2.getBlockX());
        FileManager.regionCfg.set(this.id + ".pos2.y", loc2.getBlockY());
        FileManager.regionCfg.set(this.id + ".pos2.z", loc2.getBlockZ());

        FileManager.regionCfg.set(this.id + ".eyes.placed", eyesPlaced);
        FileManager.regionCfg.set(this.id + ".eyes.max", FileManager.getCfg().getInt("setup.step2.max"));


        for(int i = 1; i <= FileManager.getCfg().getInt("setup.step2.max"); i++) {
            FileManager.regionCfg.set(this.id + ".frame" + i + ".world", frames.get(i-1).getWorld().getName());
            FileManager.regionCfg.set(this.id + ".frame" + i + ".x", frames.get(i-1).getBlockX());
            FileManager.regionCfg.set(this.id + ".frame" + i + ".y", frames.get(i-1).getBlockY());
            FileManager.regionCfg.set(this.id + ".frame" + i + ".z", frames.get(i-1).getBlockZ());
        }


        FileManager.regionCfg.set(this.id + ".enderdragon.attack.1", phase1);
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.2", phase2);
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.3", phase3);
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.4", phase4);
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.5", phase5);

        FileManager.regionCfg.set(this.id + ".enderdragon.health", health);
        FileManager.regionCfg.set(this.id + ".enderdragon.maxhealth", maxHealth);
        FileManager.regionCfg.set(this.id + ".enderdragon.absorption", absorption);
        FileManager.regionCfg.set(this.id + ".enderdragon.displayname", displayName);
        FileManager.regionCfg.set(this.id + ".enderdragon.glowing", glowing);
        FileManager.regionCfg.set(this.id + ".enderdragon.ai", ai);
        FileManager.regionCfg.set(this.id + ".enderdragon.beginphase", beginPhase);
        FileManager.regionCfg.set(this.id + ".enderdragon.silent", silent);
        FileManager.regionCfg.set(this.id + ".enderdragon.timeuntilnextattack", timeUntilNextAttack);
        FileManager.regionCfg.set(this.id + ".enderdragon.randomizeattacks", randomizeAttacks);
        FileManager.regionCfg.set(this.id + ".enderdragon.removeWhenFarAway", removeWhenFarAway);

        FileManager.saveRegions();
    }



    public void editEnderDragon() {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.mainTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*5, title);

        String minus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.minus"));
        String plus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.plus"));
        String health = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.health"));
        String maxHealth = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.maxhealth"));
        String absorption = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.absorption"));
        String silent = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.silent"));
        String glowing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.glowing"));
        String ai = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.ai"));
        String despawnFarAway = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.despawnFarAway"));
        String startPhase = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startPhase"));
        String customizeAttacks = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.customizeAttack"));
        String fertig = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.finishItem"));
        String passengerLastSet = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.passenger"));

        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));


        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(1, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                .setDisPlayname(health)
                .setLore("", lore.replaceAll("%state%", this.getHealthString() + ""))
                .build());
        inv.setItem(3, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        inv.setItem(5, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(maxHealth)
                .setLore("", lore.replaceAll("%state%", this.getMaxHealthString() + ""))
                .build());
        inv.setItem(7, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        inv.setItem(10, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                .setDisPlayname(absorption)
                .setLore("", lore.replaceAll("%state%", this.getAbsorptionString() + ""))
                .build());
        inv.setItem(12, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());




        inv.setItem(28, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(silent)
                .setLore("", lore.replaceAll("%state%", this.isSilent()))
                .build());
        inv.setItem(29, new ItemBuilder(Material.GLOWSTONE_DUST, 1)
                .setDisPlayname(glowing)
                .setLore("", lore.replaceAll("%state%", this.isGlowing()))
                .build());
        inv.setItem(30, new ItemBuilder(Material.COMMAND_BLOCK, 1)
                .setDisPlayname(ai)
                .setLore("", lore.replaceAll("%state%", this.isAi()))
                .build());

        inv.setItem(37, new ItemBuilder(Material.BLACK_WOOL, 1)
                .setDisPlayname(despawnFarAway)
                .setLore("", lore.replaceAll("%state%", this.isRemoveWhenFarAway()))
                .build());
        inv.setItem(38, new ItemBuilder(Material.SADDLE, 1)
                .setDisPlayname(passengerLastSet)
                .setLore("", lore.replaceAll("%state%", this.isPassenger()))
                .build());
        inv.setItem(39, new ItemBuilder(Material.WOODEN_SWORD, 1)
                .setDisPlayname(startPhase)
                .setLore("", lore.replaceAll("%state%", beautifulPhase(this.getBeginPhase()) + ""))
                .build());

        inv.setItem(44, new ItemBuilder(Material.SLIME_BALL, 1)
                .setDisPlayname(fertig)
                .build());


        inv.setItem(33, new ItemBuilder(Material.DIAMOND_SWORD, 1)
                .setDisPlayname(customizeAttacks)
                .build());


        p.openInventory(inv);
    }



    public void editAttacks() {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.attacksTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*6, title);

        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));

        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        if(FileManager.regionCfg.isSet(this.id + ".enderdragon.attack.1")) {
            inv.setItem(11, getItemStackByPhaseString(this.getPhase1()));
        } else {
            inv.setItem(11, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 1")
                    .build());
        }


        if(FileManager.regionCfg.isSet(this.id + ".enderdragon.attack.2")) {
            inv.setItem(12, getItemStackByPhaseString(this.getPhase2()));
        } else {
            inv.setItem(12, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 2")
                    .build());
        }



        if(FileManager.regionCfg.isSet(this.id + ".enderdragon.attack.3")) {
            inv.setItem(13, getItemStackByPhaseString(this.getPhase3()));
        } else {
            inv.setItem(13, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 3")
                    .build());
        }




        if(FileManager.regionCfg.isSet(this.id + ".enderdragon.attack.4")) {
            inv.setItem(14, getItemStackByPhaseString(this.getPhase4()));
        } else {
            inv.setItem(14, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 4")
                    .build());
        }



        if(FileManager.regionCfg.isSet(this.id + ".enderdragon.attack.5")) {
            inv.setItem(15, getItemStackByPhaseString(this.getPhase5()));
        } else {
            inv.setItem(15, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 5")
                    .build());
        }


        String minus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.minus"));
        String plus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.plus"));
        String rndmze = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.randomizeAttacks"));
        inv.setItem(29, new ItemBuilder(Material.ENDER_PEARL, 1)
                .setDisPlayname(rndmze)
                .setLore("", lore.replaceAll("%state%", this.isRandomizeAttacks()))
                .build());


        String timerUntilNext = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.timerUntilNext"));
        inv.setItem(31, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                .setDisPlayname(timerUntilNext)
                .setLore("", lore.replaceAll("%state%", this.getTimeUntilNextAttackString() + ""))
                .build());
        inv.setItem(33, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));
        inv.setItem(53, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }







    public void openAllAttackInventory() {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.allAttacksTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*4, title);


        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));
        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));




        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(10, new ItemBuilder(Material.ARROW, 1)
                .setDisPlayname(strafing)
                .build());
        inv.setItem(11, new ItemBuilder(Material.COMPASS, 1)
                .setDisPlayname(searching)
                .build());
        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK, 1)
                .setDisPlayname(shouting)
                .build());
        inv.setItem(13, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(hovering)
                .build());
        inv.setItem(14, new ItemBuilder(Material.LEAD, 1)
                .setDisPlayname(circling)
                .build());
        inv.setItem(15, new ItemBuilder(Material.MINECART, 1)
                .setDisPlayname(chargeplayer)
                .build());
        inv.setItem(16, new ItemBuilder(Material.FIRE_CHARGE, 1)
                .setDisPlayname(firebreath)
                .build());

        inv.setItem(35, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }



    public void chooseStartAttack() {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startAttackTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*4, title);


        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));
        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));



        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(10, new ItemBuilder(Material.ARROW, 1)
                .setDisPlayname(strafing)
                .build());
        inv.setItem(11, new ItemBuilder(Material.COMPASS, 1)
                .setDisPlayname(searching)
                .build());
        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK, 1)
                .setDisPlayname(shouting)
                .build());
        inv.setItem(13, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(hovering)
                .build());
        inv.setItem(14, new ItemBuilder(Material.LEAD, 1)
                .setDisPlayname(circling)
                .build());
        inv.setItem(15, new ItemBuilder(Material.MINECART, 1)
                .setDisPlayname(chargeplayer)
                .build());
        inv.setItem(16, new ItemBuilder(Material.FIRE_CHARGE, 1)
                .setDisPlayname(firebreath)
                .build());

        inv.setItem(35, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }



    public int getHealth() {
        return health;
    }

    public String getHealthString() {
        return health / 2 + " Herzen";
    }

    public int getTimeUntilNextAttack() {
        return timeUntilNextAttack;
    }

    public String getTimeUntilNextAttackString() {
        return getTimeUntilNextAttack() / 20 + " Sekunden";
    }


    public int getMaxHealth() {
        return maxHealth;
    }

    public String getMaxHealthString() {
        return maxHealth / 2 + " Herzen";
    }

    public String getBeginPhase() {
        return beginPhase;
    }

    public String getDisplayName() {
        return displayName;
    }


    public String getPhase1() {
        return phase1;
    }

    public String getPhase2() {
        return phase2;
    }

    public String getPhase3() {
        return phase3;
    }

    public String getPhase4() {
        return phase4;
    }

    public String getPhase5() {
        return phase5;
    }


    public static String enabled = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.enabled"));
    public static String disabled = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.disabled"));

    public String isGlowing() {
        return this.glowing ? enabled : disabled;
    }

    public int getAbsorption() {
        return absorption;
    }

    public String getAbsorptionString() {
        return absorption / 2 + " Herzen";
    }

    public String isPassenger() {
        return this.passenger ? enabled : disabled;
    }

    public String isAi() {
        return this.ai ? enabled : disabled;
    }

    public String isRandomizeAttacks() {
        return this.randomizeAttacks ? enabled : disabled;
    }

    public String isSilent() {
        return this.silent ? enabled : disabled;
    }

    public String isRemoveWhenFarAway() {
        return this.removeWhenFarAway ? enabled : disabled;
    }

    public boolean getSilence() {
        return this.silent;
    }

    public boolean getAi() {
        return this.ai;
    }

    public boolean getGlowing() {
        return this.glowing;
    }

    public boolean getPassenger() {
        return this.passenger;
    }

    public boolean getRandomizeAttacks() {
        return this.randomizeAttacks;
    }

    public boolean getRemoveWhenFarAway() {
        return this.removeWhenFarAway;
    }




























    public void setTimeUntilNextAttack(int timeUntilNextAttack) {
        this.timeUntilNextAttack = timeUntilNextAttack;
        FileManager.regionCfg.set(this.id + ".enderdragon.timeuntilnextattack", timeUntilNextAttack);
        FileManager.saveRegions();
    }

    public void setHealth(int health) {
        this.health = health;
        FileManager.regionCfg.set(this.id + ".enderdragon.health", health);
        FileManager.saveRegions();
    }

    public int getMax() {
        return max;
    }

    public void setBeginPhase(String beginPhase) {
        this.beginPhase = beginPhase;
        FileManager.regionCfg.set(this.id + ".enderdragon.beginphase", beginPhase);
        FileManager.saveRegions();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        FileManager.regionCfg.set(this.id + ".enderdragon.displayname", displayName);
        FileManager.saveRegions();
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
        FileManager.regionCfg.set(this.id + ".enderdragon.glowing", glowing);
        FileManager.saveRegions();
    }

    public void setSilence(boolean silent) {
        this.silent = silent;
        FileManager.regionCfg.set(this.id + ".enderdragon.silent", silent);
        FileManager.saveRegions();
    }

    public void setPassenger(boolean passenger) {
        this.passenger = passenger;
        FileManager.regionCfg.set(this.id + ".enderdragon.passenger", passenger);
        FileManager.saveRegions();
    }

    public void setAi(boolean ai) {
        this.ai = ai;
        FileManager.regionCfg.set(this.id + ".enderdragon.ai", ai);
        FileManager.saveRegions();
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        FileManager.regionCfg.set(this.id + ".enderdragon.maxhealth", maxHealth);
        FileManager.saveRegions();
    }

    public void setRandomizeAttacks(boolean randomizeAttacks) {
        this.randomizeAttacks = randomizeAttacks;
        FileManager.regionCfg.set(this.id + ".enderdragon.randomizeattacks", randomizeAttacks);
        FileManager.saveRegions();
    }



    public void setAbsorption(int absorption) {
        this.absorption = absorption;
        FileManager.regionCfg.set(this.id + ".enderdragon.absorption", absorption);
        FileManager.saveRegions();
    }

    public void setRemoveWhenFarAway(boolean removeWhenFarAway) {
        this.removeWhenFarAway = removeWhenFarAway;
        FileManager.regionCfg.set(this.id + ".enderdragon.removeWhenFarAway", removeWhenFarAway);
        FileManager.saveRegions();
    }


    public void setPhase1(String phase1) {
        this.phase1 = phase1;
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.1", phase1);
        FileManager.saveRegions();
    }

    public void setPhase2(String phase2) {
        this.phase2 = phase2;
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.2", phase2);
        FileManager.saveRegions();
    }

    public void setPhase3(String phase3) {
        this.phase3 = phase3;
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.3", phase3);
        FileManager.saveRegions();
    }

    public void setPhase4(String phase4) {
        this.phase4 = phase4;
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.4", phase4);
        FileManager.saveRegions();
    }

    public void setPhase5(String phase5) {
        this.phase5 = phase5;
        FileManager.regionCfg.set(this.id + ".enderdragon.attack.5", phase5);
        FileManager.saveRegions();
    }

    public void setPhase(String phase, int number) {
        if(number == 1) {
            this.phase1 = phase;
            FileManager.regionCfg.set(this.id + ".enderdragon.attack.1", phase);
            FileManager.saveRegions();

        } else if(number == 2) {
            this.phase2 = phase;
            FileManager.regionCfg.set(this.id + ".enderdragon.attack.2", phase);
            FileManager.saveRegions();

        } else if(number == 3) {
            this.phase3 = phase;
            FileManager.regionCfg.set(this.id + ".enderdragon.attack.3", phase);
            FileManager.saveRegions();

        } else if(number == 4) {
            this.phase4 = phase;
            FileManager.regionCfg.set(this.id + ".enderdragon.attack.4", phase);
            FileManager.saveRegions();

        } else if(number == 5) {
            this.phase5 = phase;
            FileManager.regionCfg.set(this.id + ".enderdragon.attack.5", phase);
            FileManager.saveRegions();
        }

    }





    public static String beautifulPhase(String phase) {

        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));

        if(phase.equalsIgnoreCase("strafing")) {
            return strafing;
        } else if(phase.equalsIgnoreCase("searching")){
            return searching;
        } else if(phase.equalsIgnoreCase("shouting")){
            return shouting;
        } else if(phase.equalsIgnoreCase("hovering")){
            return hovering;
        } else if(phase.equalsIgnoreCase("circling")){
            return circling;
        } else if(phase.equalsIgnoreCase("chargeplayer")){
            return chargeplayer;
        } else if(phase.equalsIgnoreCase("firebreath")){
            return firebreath;
        }
        return null;
    }

    public static EnderDragon.Phase getPhaseByString(String phase) {
        if(phase.equalsIgnoreCase("strafing")) {
            return EnderDragon.Phase.STRAFING;
        } else if(phase.equalsIgnoreCase("searching")){
            return EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET;
        } else if(phase.equalsIgnoreCase("shouting")){
            return EnderDragon.Phase.ROAR_BEFORE_ATTACK;
        } else if(phase.equalsIgnoreCase("hovering")){
            return EnderDragon.Phase.HOVER;
        } else if(phase.equalsIgnoreCase("circling")){
            return EnderDragon.Phase.CIRCLING;
        } else if(phase.equalsIgnoreCase("chargeplayer")){
            return EnderDragon.Phase.CHARGE_PLAYER;
        } else if(phase.equalsIgnoreCase("firebreath")){
            return EnderDragon.Phase.BREATH_ATTACK;
        }
        return null;
    }



    public static String getPhaseStringByItem(Material mat) {
        if(mat == Material.ARROW) {
            return "strafing";
        } else if(mat == Material.COMPASS) {
            return "searching";
        } else if(mat == Material.NOTE_BLOCK) {
            return "shouting";
        } else if(mat == Material.ELYTRA) {
            return "hovering";
        } else if(mat == Material.LEAD) {
            return "circling";
        } else if(mat == Material.MINECART) {
            return "chargeplayer";
        } else if(mat == Material.FIRE_CHARGE) {
            return "firebreath";
        }
        return null;
    }



    public static ItemStack getItemStackByPhaseString(String phase) {

        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));


        if(phase.equalsIgnoreCase("strafing")) {
            return new ItemBuilder(Material.ARROW, 1)
                    .setDisPlayname(strafing)
                    .build();
        } else if(phase.equalsIgnoreCase("searching")){
            return new ItemBuilder(Material.COMPASS, 1)
                    .setDisPlayname(searching)
                    .build();
        } else if(phase.equalsIgnoreCase("shouting")){
            return new ItemBuilder(Material.NOTE_BLOCK, 1)
                    .setDisPlayname(shouting)
                    .build();
        } else if(phase.equalsIgnoreCase("hovering")){
            return new ItemBuilder(Material.ELYTRA, 1)
                    .setDisPlayname(hovering)
                    .build();
        } else if(phase.equalsIgnoreCase("circling")){
            return new ItemBuilder(Material.LEAD, 1)
                    .setDisPlayname(circling)
                    .build();
        } else if(phase.equalsIgnoreCase("chargeplayer")){
            return new ItemBuilder(Material.MINECART, 1)
                    .setDisPlayname(chargeplayer)
                    .build();
        } else if(phase.equalsIgnoreCase("firebreath")){
            return new ItemBuilder(Material.FIRE_CHARGE, 1)
                    .setDisPlayname(firebreath)
                    .build();
        }
        return null;
    }





    public static Setup getSetup(Player p) {
        for(Setup setups : EDModify.setups) {
            if(setups.currentSetup.containsKey(p)) {
                return setups;
            }
        }
        return null;
    }
















    public static int getHealthFromFile(int region) {
        return FileManager.regionCfg.getInt(region + ".enderdragon.health");
    }
    public static void setHealthFromFile(int region, int amount) {
        FileManager.regionCfg.set(region + ".enderdragon.health", amount);
        FileManager.saveRegions();
    }

    public static int getMaxHealthFromFile(int region) {
        return FileManager.regionCfg.getInt(region + ".enderdragon.maxhealth");
    }
    public static void setMaxHealthFromFile(int region, int amount) {
        FileManager.regionCfg.set(region + ".enderdragon.maxhealth", amount);
        FileManager.saveRegions();
    }

    public static int getAbsorptionFromFile(int region) {
        return FileManager.regionCfg.getInt(region + ".enderdragon.absorption");
    }
    public static void setAbsorptionFromFile(int region, int amount) {
        FileManager.regionCfg.set(region + ".enderdragon.absorption", amount);
        FileManager.saveRegions();
    }

    public static String getDisplaynameFromFile(int region) {
        return FileManager.regionCfg.getString(region + ".enderdragon.displayname");
    }

    public static boolean getPassengerFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.passenger");
    }

    public static boolean getGlowingFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.glowing");
    }

    public static void setGlowingFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.glowing", value);
        FileManager.saveRegions();
    }

    public static String isGlowingFromFile(int region) {
        return getGlowingFromFile(region) ? enabled : disabled;
    }


    public static boolean getAiFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.ai");
    }

    public static void setAiFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.ai", value);
        FileManager.saveRegions();
    }

    public static String isAiFromFile(int region) {
        return getAiFromFile(region) ? enabled : disabled;
    }

    public static String getBeginPhaseFromFile(int region) {
        return FileManager.regionCfg.getString(region + ".enderdragon.beginphase");
    }

    public static void setBeginPhaseFromFile(int region, String which) {
        FileManager.regionCfg.set(region + ".enderdragon.beginphase", which);
        FileManager.saveRegions();
    }

    public static boolean getSilenceFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.silent");
    }

    public static String isSilenceFromFile(int region) {
        return getSilenceFromFile(region) ? enabled : disabled;
    }

    public static void setSilenceFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.silent", value);
        FileManager.saveRegions();
    }

    public static void setPassengerFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.passenger", value);
        FileManager.saveRegions();
    }

    public static int getTimeUntilNextAttackFromFile(int region) {
        return FileManager.regionCfg.getInt(region + ".enderdragon.timeuntilnextattack");
    }

    public static void setTimeUntilNextAttackFromFile(int region, int amount) {
        FileManager.regionCfg.set(region + ".enderdragon.timeuntilnextattack", amount);
        FileManager.saveRegions();
    }

    public static String getTimeUntilNextAttackStringFile(int region) {
        return getTimeUntilNextAttackFromFile(region) / 20 + " Sekunden";
    }


    public static boolean getRandomizeAttackFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.randomizeattacks");
    }

    public static void setRandomizeAttackFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.randomizeattacks", value);
        FileManager.saveRegions();
    }

    public static String isRandomizeAttackFromFile(int region) {
        return getRandomizeAttackFromFile(region) ? enabled : disabled;
    }


    public static boolean getRemoveFarAwayFromFile(int region) {
        return FileManager.regionCfg.getBoolean(region + ".enderdragon.removeWhenFarAway");
    }

    public static void setRemoveFarAwayFromFile(int region, boolean value) {
        FileManager.regionCfg.set(region + ".enderdragon.removeWhenFarAway", value);
        FileManager.saveRegions();
    }

    public static String isRemoveFarAwayFromFile(int region) {
        return getRemoveFarAwayFromFile(region) ? enabled : disabled;
    }

    public static String isPassengerFromFile(int region) {
        return getPassengerFromFile(region) ? enabled : disabled;
    }

    public static int getMaxEyes(int region) {
        return FileManager.regionCfg.getInt(region + ".eyes.max");
    }





    public static void setPhaseFromFile(String phase, int region, int number) {
        if(number == 1) {
            FileManager.regionCfg.set(region + ".enderdragon.attack.1", phase);
            FileManager.saveRegions();

        } else if(number == 2) {
            FileManager.regionCfg.set(region + ".enderdragon.attack.2", phase);
            FileManager.saveRegions();

        } else if(number == 3) {
            FileManager.regionCfg.set(region + ".enderdragon.attack.3", phase);
            FileManager.saveRegions();

        } else if(number == 4) {
            FileManager.regionCfg.set(region + ".enderdragon.attack.4", phase);
            FileManager.saveRegions();

        } else if(number == 5) {
            FileManager.regionCfg.set(region + ".enderdragon.attack.5", phase);
            FileManager.saveRegions();
        }

    }


    public static String getAttack(int number, int region) {
        if(number == 1) {
            return FileManager.regionCfg.getString(region + ".enderdragon.attack.1");
        } else if(number == 2) {
            return FileManager.regionCfg.getString(region + ".enderdragon.attack.2");
        } else if(number == 3) {
            return FileManager.regionCfg.getString(region + ".enderdragon.attack.3");
        } else if(number == 4) {
            return FileManager.regionCfg.getString(region + ".enderdragon.attack.4");
        } else if(number == 5) {
            return FileManager.regionCfg.getString(region + ".enderdragon.attack.5");
        } else {
            return null;
        }
    }

    public static int getEyesPlaced(int region) {
        return FileManager.regionCfg.getInt(region + ".eyes.placed");
    }

    public static void setEyesPlaced(int region, int number) {
        FileManager.regionCfg.set(region + ".eyes.placed", number);
        FileManager.saveRegions();
    }
























    //-----------------------------------------------------------------------------------------------------------------------------------//

    public static void modifyRegion(Player p, int region) {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.mainTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*5, title + " §8(§bEdit§8)");


        String minus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.minus"));
        String plus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.plus"));
        String health = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.health"));
        String maxHealth = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.maxhealth"));
        String absorption = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.absorption"));
        String silent = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.silent"));
        String glowing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.glowing"));
        String ai = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.ai"));
        String despawnFarAway = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.despawnFarAway"));
        String startPhase = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startPhase"));
        String customizeAttacks = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.customizeAttack"));
        String passengerLastSet = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.passenger"));


        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));


        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(1, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                .setDisPlayname(health)
                .setLore("", lore.replaceAll("%state%", Setup.getHealthFromFile(region) / 2 + " Herzen"))
                .build());
        inv.setItem(3, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        inv.setItem(5, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(maxHealth)
                .setLore("", lore.replaceAll("%state%", Setup.getMaxHealthFromFile(region) / 2 + " Herzen"))
                .build());
        inv.setItem(7, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        inv.setItem(10, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());

        inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                .setDisPlayname(absorption)
                .setLore("", lore.replaceAll("%state%", Setup.getAbsorptionFromFile(region) / 2 + " Herzen"))
                .build());

        inv.setItem(12, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());




        inv.setItem(28, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(silent)
                .setLore("", lore.replaceAll("%state%", Setup.isSilenceFromFile(region) + ""))
                .build());
        inv.setItem(29, new ItemBuilder(Material.GLOWSTONE_DUST, 1)
                .setDisPlayname(glowing)
                .setLore("", lore.replaceAll("%state%", Setup.isGlowingFromFile(region) + ""))
                .build());
        inv.setItem(30, new ItemBuilder(Material.COMMAND_BLOCK, 1)
                .setDisPlayname(ai)
                .setLore("", lore.replaceAll("%state%", Setup.isAiFromFile(region) + ""))
                .build());

        inv.setItem(37, new ItemBuilder(Material.BLACK_WOOL, 1)
                .setDisPlayname(despawnFarAway)
                .setLore("", lore.replaceAll("%state%", Setup.isRemoveFarAwayFromFile(region) + ""))
                .build());
        inv.setItem(38, new ItemBuilder(Material.SADDLE, 1)
                .setDisPlayname(passengerLastSet)
                .setLore("", lore.replaceAll("%state%", Setup.isPassengerFromFile(region) + ""))
                .build());
        inv.setItem(39, new ItemBuilder(Material.WOODEN_SWORD, 1)
                .setDisPlayname(startPhase)
                .setLore("", lore.replaceAll("%state%", beautifulPhase(Setup.getBeginPhaseFromFile(region)) + ""))
                .build());



        inv.setItem(33, new ItemBuilder(Material.DIAMOND_SWORD, 1)
                .setDisPlayname(customizeAttacks)
                .build());

        p.openInventory(inv);
    }









    public static void modifyStartAttack(int region, Player p) {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startAttackTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*4, title + " §8(§bEdit§8)");


        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));
        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));



        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(10, new ItemBuilder(Material.ARROW, 1)
                .setDisPlayname(strafing)
                .build());
        inv.setItem(11, new ItemBuilder(Material.COMPASS, 1)
                .setDisPlayname(searching)
                .build());
        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK, 1)
                .setDisPlayname(shouting)
                .build());
        inv.setItem(13, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(hovering)
                .build());
        inv.setItem(14, new ItemBuilder(Material.LEAD, 1)
                .setDisPlayname(circling)
                .build());
        inv.setItem(15, new ItemBuilder(Material.MINECART, 1)
                .setDisPlayname(chargeplayer)
                .build());
        inv.setItem(16, new ItemBuilder(Material.FIRE_CHARGE, 1)
                .setDisPlayname(firebreath)
                .build());

        inv.setItem(35, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }



    public static void modifyAttacks(int region, Player p) {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.attacksTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*6, title + " §8(§bEdit§8)");

        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));

        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        if(FileManager.regionCfg.isSet(region + ".enderdragon.attack.1")) {
            inv.setItem(11, getItemStackByPhaseString(Setup.getAttack(1, region)));
        } else {
            inv.setItem(11, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 1")
                    .build());
        }


        if(FileManager.regionCfg.isSet(region + ".enderdragon.attack.2")) {
            inv.setItem(12, getItemStackByPhaseString(Setup.getAttack(2, region)));
        } else {
            inv.setItem(12, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 2")
                    .build());
        }



        if(FileManager.regionCfg.isSet(region + ".enderdragon.attack.3")) {
            inv.setItem(13, getItemStackByPhaseString(Setup.getAttack(3, region)));
        } else {
            inv.setItem(13, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 3")
                    .build());

        }



        if(FileManager.regionCfg.isSet(region + ".enderdragon.attack.4")) {
            inv.setItem(14, getItemStackByPhaseString(Setup.getAttack(4, region)));
        } else {
            inv.setItem(14, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 4")
                    .build());
        }



        if(FileManager.regionCfg.isSet(region + ".enderdragon.attack.5")) {
            inv.setItem(15, getItemStackByPhaseString(Setup.getAttack(5, region)));
        } else {
            inv.setItem(15, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, 1)
                    .setDisPlayname("§aPhase 5")
                    .build());
        }


        String minus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.minus"));
        String plus = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.plus"));
        String rndmze = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.randomizeAttacks"));
        inv.setItem(29, new ItemBuilder(Material.ENDER_PEARL, 1)
                .setDisPlayname(rndmze)
                .setLore("", lore.replaceAll("%state%", Setup.isRandomizeAttackFromFile(region)))
                .build());


        String timerUntilNext = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.timerUntilNext"));
        inv.setItem(31, new ItemBuilder(Material.OAK_BUTTON, 1)
                .setDisPlayname(minus)
                .build());
        inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                .setDisPlayname(timerUntilNext)
                .setLore("", lore.replaceAll("%state%", Setup.getTimeUntilNextAttackStringFile(region) + ""))
                .build());
        inv.setItem(33, new ItemBuilder(Material.STONE_BUTTON, 1)
                .setDisPlayname(plus)
                .build());

        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));
        inv.setItem(53, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }







    public static void modifyAllAttacks(Player p) {
        String title = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.allAttacksTitle"));
        Inventory inv = Bukkit.createInventory(null, 9*4, title + " §8(§bEdit§8)");


        String strafing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.strafing"));
        String searching = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.searching"));
        String shouting = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.shouting"));
        String hovering = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.hovering"));
        String circling = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.circling"));
        String chargeplayer = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chargePlayer"));
        String firebreath = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.firebreath"));
        String returning = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.return"));




        fillInventory(inv, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1)
                .setDisPlayname(" ")
                .build());

        inv.setItem(10, new ItemBuilder(Material.ARROW, 1)
                .setDisPlayname(strafing)
                .build());
        inv.setItem(11, new ItemBuilder(Material.COMPASS, 1)
                .setDisPlayname(searching)
                .build());
        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK, 1)
                .setDisPlayname(shouting)
                .build());
        inv.setItem(13, new ItemBuilder(Material.ELYTRA, 1)
                .setDisPlayname(hovering)
                .build());
        inv.setItem(14, new ItemBuilder(Material.LEAD, 1)
                .setDisPlayname(circling)
                .build());
        inv.setItem(15, new ItemBuilder(Material.MINECART, 1)
                .setDisPlayname(chargeplayer)
                .build());
        inv.setItem(16, new ItemBuilder(Material.FIRE_CHARGE, 1)
                .setDisPlayname(firebreath)
                .build());

        inv.setItem(35, new ItemBuilder(Material.REDSTONE, 1)
                .setDisPlayname(returning)
                .build());

        p.openInventory(inv);
    }






    private static void fillInventory(Inventory inv, ItemStack stack) {
        for(int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, stack);
        }
    }

}
