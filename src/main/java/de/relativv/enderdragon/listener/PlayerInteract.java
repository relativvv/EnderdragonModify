package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;

public class PlayerInteract implements Listener {

    private EDModify ed;
    public PlayerInteract(EDModify ed) {
        this.ed = ed;
    }

    public static HashMap<EnderDragon, Integer> cPhase = new HashMap<>();



    @EventHandler
    public void onDefine(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getItem() != null) {
            if (e.getItem().getType() == Material.BAMBOO) {
                if (ed.isInSetup.contains(p)) {
                    Setup setup = Setup.getSetup(p);
                    if (setup != null) {
                        if (setup.getStep() == 1) {
                            if(e.getClickedBlock() != null) {
                                if (!isInAnyRegion(e.getClickedBlock().getLocation())) {
                                    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                                        if(setup.pos2.containsKey(p)) {
                                            int o = e.getClickedBlock().getLocation().getBlockY() - setup.pos2.get(p).getBlockY();
                                            int n = setup.pos2.get(p).getBlockY() - e.getClickedBlock().getLocation().getBlockY();
                                            if(o >= FileManager.getCfg().getInt("setup.step1.space") || n >= FileManager.getCfg().getInt("setup.step1.space")) {

                                            } else {
                                                String notEnoughSpace = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.notEnoughSpace"));
                                                p.sendMessage(ed.pr + notEnoughSpace.replaceAll("%space%", FileManager.getCfg().getInt("setup.step1.space") + ""));
                                                e.setCancelled(true);
                                                p.updateInventory();
                                                return;
                                            }
                                        }
                                        String set1 = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("defineRegion.pos1"));
                                        p.sendMessage(ed.pr + set1);
                                        setup.pos1.remove(p);
                                        setup.pos1.put(p, e.getClickedBlock().getLocation());
                                        e.setCancelled(true);
                                        p.updateInventory();

                                    } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                        if(setup.pos1.containsKey(p)) {
                                            int o = e.getClickedBlock().getLocation().getBlockY() - setup.pos1.get(p).getBlockY();
                                            int n = setup.pos1.get(p).getBlockY() - e.getClickedBlock().getLocation().getBlockY();
                                            if(o >= FileManager.getCfg().getInt("setup.step1.space") || n >= FileManager.getCfg().getInt("setup.step1.space")) {

                                            } else {
                                                String notEnoughSpace = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.notEnoughSpace"));
                                                p.sendMessage(ed.pr + notEnoughSpace.replaceAll("%space%", FileManager.getCfg().getInt("setup.step1.space") + ""));
                                                e.setCancelled(true);
                                                p.updateInventory();
                                                return;
                                            }
                                        }

                                        String set2 = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("defineRegion.pos2"));
                                        p.sendMessage(ed.pr + set2);
                                        setup.pos2.remove(p);
                                        setup.pos2.put(p, e.getClickedBlock().getLocation());
                                        e.setCancelled(true);
                                        p.updateInventory();
                                    }

                                    if (setup.pos1.containsKey(p) && setup.pos2.containsKey(p)) {
                                        String ready = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.confirm"));
                                        p.sendMessage(ed.pr + ready);
                                    }

                                } else {
                                    String isAlreadyInARegion = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.isAlreadyInRegion"));
                                    p.sendMessage(ed.pr + isAlreadyInARegion);
                                    e.setCancelled(true);
                                    p.updateInventory();
                                }
                            }
                        }
                    }
                }
















            } else if(e.getItem().getType() == Material.ENDER_EYE) {
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if(e.getClickedBlock().getType() == Material.END_PORTAL_FRAME) {
                        if (!ed.isInSetup.contains(p)) {
                            if (isInAnyRegion(e.getClickedBlock().getLocation())) {
                                if (ed.portalFrames.contains(e.getClickedBlock().getLocation())) {
                                    EndPortalFrame frame = (EndPortalFrame) e.getClickedBlock().getBlockData();
                                if (!frame.hasEye()) {
                                    if (Setup.getEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation())) < Setup.getMaxEyes(getEndportalRegion(e.getClickedBlock().getLocation()))) {
                                        for (Entity ent : e.getClickedBlock().getWorld().getEntities()) {
                                            if (ent instanceof EnderDragon) {
                                                if (!ent.isDead()) {
                                                    e.setCancelled(true);
                                                    p.updateInventory();
                                                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.5F);
                                                    String currentEnderdragon = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("enderdragon.alreadyexists"));
                                                    p.sendMessage(ed.pr + currentEnderdragon);
                                                    return;
                                                }
                                            }
                                        }

                                        Setup.setEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation()), Setup.getEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation())) + 1);

                                        String filled = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("portalframe.filled"));
                                        String filled1 = filled.replaceAll("%region%", getEndportalRegion(e.getClickedBlock().getLocation()) + "");
                                        String filled2 = filled1.replaceAll("%current%", Setup.getEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation())) + "");
                                        String filled3 = filled2.replaceAll("%max%", Setup.getMaxEyes(getEndportalRegion(e.getClickedBlock().getLocation())) + "");
                                        p.sendMessage(ed.pr + filled3);

                                        p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1, 1);
                                        e.getClickedBlock().getWorld().playEffect(e.getClickedBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1000);


                                        if (Setup.getEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation())) == Setup.getMaxEyes(getEndportalRegion(e.getClickedBlock().getLocation()))) {
                                            Setup.setEyesPlaced(getEndportalRegion(e.getClickedBlock().getLocation()), 0);

                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < ed.portalFrames.size(); i++) {
                                                        if (getEndportalRegion(ed.portalFrames.get(i)) == getEndportalRegion(e.getClickedBlock().getLocation())) {
                                                            BlockData data = Material.END_PORTAL_FRAME.createBlockData();
                                                            EndPortalFrame fr = (EndPortalFrame) data;
                                                            fr.setEye(false);
                                                            ed.portalFrames.get(i).getBlock().setBlockData(fr);
                                                            ed.portalFrames.get(i).getBlock().getWorld().playEffect(ed.portalFrames.get(i).getBlock().getLocation(), Effect.DRAGON_BREATH, 1);
                                                        }
                                                    }
                                                }
                                            }.runTaskLater(ed, 5);

                                            p.getWorld().setTime(14000);
                                            p.getWorld().setStorm(false);
                                            p.getWorld().setThundering(true);

                                            int region = getEndportalRegion(e.getClickedBlock().getLocation());
                                            int y = e.getClickedBlock().getWorld().getHighestBlockYAt(e.getClickedBlock().getLocation().getBlockX(), e.getClickedBlock().getLocation().getBlockZ());
                                            EnderDragon dragon = e.getClickedBlock().getWorld().spawn(new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX(), y, e.getClickedBlock().getLocation().getBlockZ()), EnderDragon.class);


                                            dragon.setCustomName(Setup.getDisplaynameFromFile(region));
                                            dragon.setCustomNameVisible(true);
                                            dragon.setMaxHealth(Setup.getMaxHealthFromFile(region));
                                            dragon.setHealth(Setup.getHealthFromFile(region));
                                            dragon.setAbsorptionAmount(Setup.getAbsorptionFromFile(region));
                                            dragon.setPhase(Setup.getPhaseByString(Setup.getBeginPhaseFromFile(region)));

                                            if(ed.bar != null) {
                                                ed.bar.setVisible(false);
                                                ed.bar.removeAll();
                                                ed.bar = null;
                                            }

                                            ed.bar = Bukkit.createBossBar(dragon.getCustomName(), BarColor.RED, BarStyle.SOLID);
                                            ed.bar.setProgress(dragon.getHealth() / dragon.getMaxHealth());
                                            ed.bar.setVisible(true);
                                            for(Player all : dragon.getWorld().getPlayers()) {
                                                ed.bar.addPlayer(all);
                                            }


                                            if(Setup.getPassengerFromFile(region)) {
                                                dragon.setPassenger(p);
                                            }

                                            if (Setup.getAiFromFile(region)) {
                                                dragon.setAI(true);
                                            } else {
                                                dragon.setAI(false);
                                            }

                                            if (Setup.getGlowingFromFile(region)) {
                                                dragon.setGlowing(true);
                                            } else {
                                                dragon.setGlowing(false);
                                            }

                                            if (Setup.getSilenceFromFile(region)) {
                                                dragon.setSilent(true);
                                            } else {
                                                dragon.setSilent(false);
                                            }

                                            if (Setup.getRemoveFarAwayFromFile(region)) {
                                                dragon.setRemoveWhenFarAway(true);
                                            } else {
                                                dragon.setRemoveWhenFarAway(false);
                                            }


                                            boolean tpBack = FileManager.getCfg().getBoolean("enderdragon.teleportBackOnLeave");
                                            if (tpBack) {
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!dragon.isDead()) {
                                                            if (getRegion(dragon.getLocation()) != getEndportalRegion(e.getClickedBlock().getLocation())) {
                                                                dragon.teleport(new Location(e.getClickedBlock().getLocation().getWorld(), e.getClickedBlock().getLocation().getBlockX(), y, e.getClickedBlock().getLocation().getBlockZ()));

                                                                for (Player all : Bukkit.getOnlinePlayers()) {
                                                                    if (isInAnyRegion(all.getLocation())) {
                                                                        if (getRegion(all.getLocation()) == getEndportalRegion(e.getClickedBlock().getLocation())) {
                                                                            String enderDragonLeave = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("enderdragon.leavingArea"));
                                                                            all.sendMessage(ed.pr + enderDragonLeave);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            cancel();
                                                        }
                                                    }
                                                }.runTaskTimer(ed, 0, 40);
                                            }


                                            cPhase.put(dragon, 1);

                                            if (Setup.getRandomizeAttackFromFile(region)) {
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {

                                                        if (dragon.isDead() || dragon == null) {
                                                            ed.bar.setVisible(false);
                                                            ed.bar.removeAll();
                                                            cancel();
                                                        }

                                                        int rnd = new Random().nextInt(4) + 1;
                                                        cPhase.remove(p);

                                                        switch (rnd) {
                                                            case 1:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(1, region)));
                                                                break;
                                                            case 2:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(2, region)));
                                                                break;
                                                            case 3:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(3, region)));
                                                                break;
                                                            case 4:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(4, region)));
                                                                break;
                                                            case 5:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(5, region)));
                                                                break;
                                                            default:
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(1, region)));
                                                                break;
                                                        }


                                                    }
                                                }.runTaskTimer(ed, 0, Setup.getTimeUntilNextAttackFromFile(region));


                                            } else {
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        int pha = cPhase.get(dragon);

                                                        if (dragon.isDead() || dragon == null) {
                                                            ed.bar.setVisible(false);
                                                            ed.bar.removeAll();
                                                            cancel();
                                                        }

                                                        switch (pha) {
                                                            case 1:
                                                                cPhase.remove(dragon);
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(1, region)));
                                                                cPhase.put(dragon, 2);
                                                                break;
                                                            case 2:
                                                                cPhase.remove(dragon);
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(2, region)));
                                                                cPhase.put(dragon, 3);
                                                                break;
                                                            case 3:
                                                                cPhase.remove(dragon);
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(3, region)));
                                                                cPhase.put(dragon, 4);
                                                                break;
                                                            case 4:
                                                                cPhase.remove(dragon);
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(4, region)));
                                                                cPhase.put(dragon, 5);
                                                                break;
                                                            case 5:
                                                                cPhase.remove(dragon);
                                                                dragon.setPhase(Setup.getPhaseByString(Setup.getAttack(5, region)));
                                                                cPhase.put(dragon, 1);
                                                                break;
                                                            default:
                                                                cPhase.remove(dragon);
                                                                cPhase.put(dragon, 1);
                                                                break;
                                                        }


                                                    }
                                                }.runTaskTimer(ed, 0, Setup.getTimeUntilNextAttackFromFile(region));

                                            }


                                            for (Player all : Bukkit.getOnlinePlayers()) {
                                                if(FileManager.getCfg().getBoolean("enderdragon.comingAll")) {
                                                    String enderdragonComingA = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("enderdragon.comingAllMsg"));
                                                    all.sendMessage(ed.pr + enderdragonComingA.replaceAll("%name%", dragon.getCustomName()));
                                                }
                                                if (isInAnyRegion(all.getLocation())) {
                                                    if (getRegion(all.getLocation()) == getRegion(e.getClickedBlock().getLocation())) {
                                                        all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1, 1);
                                                        String enderdragonComing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("enderdragon.coming"));
                                                        all.sendMessage(ed.pr + enderdragonComing.replaceAll("%name%", dragon.getCustomName()));
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    e.setCancelled(true);
                                    String alreadyFilled = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("portalframe.alreadyfilled"));
                                    p.sendMessage(ed.pr + alreadyFilled);
                                    p.updateInventory();
                                }
                            }
                        }
                        } else {
                            e.setCancelled(true);
                            String forbidden = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.forbidden"));
                            p.sendMessage(ed.pr + forbidden);
                            p.updateInventory();
                        }
                    }
                }
            }
        }
    }



    private int getRegion(Location loc) {
        for(int i = 0; i < ed.regions.size(); i++) {
            Cuboid c = ed.regions.get(i);
            if(c.contains(loc)) {
                return i;
            }
        }
        return -1;
    }


    private int getEndportalRegion(Location loc) {
        for(int i = 0; i < FileManager.regionCfg.getKeys(false).size(); i++) {
            for(int f = 1; f <= Setup.getMaxEyes(i); f++) {
                String world = FileManager.regionCfg.getString(i + ".frame" + f + ".world");
                int x = FileManager.regionCfg.getInt(i + ".frame" + f + ".x");
                int y = FileManager.regionCfg.getInt(i + ".frame" + f + ".y");
                int z = FileManager.regionCfg.getInt(i + ".frame" + f + ".z");

                if(world.equalsIgnoreCase(loc.getWorld().getName()) && x == loc.getBlockX() && y == loc.getBlockY() && z == loc.getBlockZ()) {
                    return i;
                }
            }
        }
        return -1;
    }


    private boolean isInAnyRegion(Location loc) {
        for(Cuboid c : ed.regions) {
            if(c.contains(loc)) {
                return true;
            }
        }
        return false;
    }

}
