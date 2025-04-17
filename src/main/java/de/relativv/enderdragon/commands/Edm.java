package de.relativv.enderdragon.commands;

import de.relativv.enderdragon.listener.BlockPlace;
import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.ItemBuilder;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Edm implements CommandExecutor {

    private EDModify ed;
    public Edm(EDModify ed) {
        this.ed = ed;
    }

    public static HashMap<Player, ItemStack[]> saveInv = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;


            if(args.length == 0) {
                if(p.hasPermission("edm.global")) {
                    p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                    p.sendMessage("");
                    p.sendMessage("  §7/edm §8- §7Hauptbefehl");
                    p.sendMessage("  §7/edm setup§8- §7Startet das Setup für eine neue Region");
                    p.sendMessage("  §7/edm modify <ID>§8- §7Öffnet das Modifiziermenü der gewünschten Region");
                    p.sendMessage("  §7/getmyregionid §8- §7Erfahre in welcher definierten Region du dich befindest");
                    p.sendMessage("");
                    p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                } else {
                    p.sendMessage(ed.pr + ed.noPerm);
                }

            } else if(args.length == 1) {
                if (args[0].equalsIgnoreCase("setup")) {
                    if (p.hasPermission("edm.setup")) {

                        ed.isInSetup.add(p);
                        saveInv.put(p, p.getInventory().getContents());
                        p.getInventory().clear();
                        p.setGameMode(GameMode.CREATIVE);
                        p.getInventory().setItem(4, new ItemBuilder(Material.BAMBOO, 1)
                                .setDisPlayname("§aRegion definieren")
                                .build());

                        for (int i = 0; i < 100; i++) {
                            p.sendMessage(" ");
                        }

                        Setup setup = new Setup(p);

                        String start = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.start"));
                        p.sendMessage(ed.pr + start);
                    } else {
                        p.sendMessage(ed.noPerm);
                    }



                } else if(args[0].equalsIgnoreCase("next")) {
                    if (p.hasPermission("edm.setup")) {
                        if (ed.isInSetup.contains(p)) {
                            Setup setup = Setup.getSetup(p);
                            if (setup.getStep() == 1) {
                                if (setup.pos1.containsKey(p) && setup.pos2.containsKey(p)) {

                                    p.getInventory().clear();
                                    p.getInventory().setItem(4, new ItemBuilder(Material.END_PORTAL_FRAME, 1)
                                            .setDisPlayname("§dEndportalframe")
                                            .build());

                                    setup.addStep();
                                    setup.currentRegion.put(p, new Cuboid(setup.pos1.get(p), setup.pos2.get(p)));

                                    BlockPlace.currentAmount.put(p, 0);

                                    String end = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.end"));
                                    p.sendMessage(ed.pr + end);

                                    for (int i = 0; i < 5; i++) {
                                        p.sendMessage(" ");
                                    }

                                    String start = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.start"));
                                    p.sendMessage(ed.pr + start);

                                } else {
                                    String notSelected = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step1.noRegion"));
                                    p.sendMessage(ed.pr + notSelected);
                                }


                            } else if (setup.getStep() == 2) {
                                int max = setup.getMax();
                                if (setup.endPortalFrames.size() == max) {

                                    ed.portalFrames.addAll(setup.endPortalFrames);


                                    p.getInventory().clear();
                                    setup.addStep();

                                    p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
                                    setup.editEnderDragon();

                                } else {
                                    String notSelected = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step2.notAllPlaced"));
                                    p.sendMessage(ed.pr + notSelected);
                                }
                            }


                        } else {
                            String notInSetup = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.notInSetup"));
                            p.sendMessage(ed.pr + notInSetup);
                        }
                    } else {
                        p.sendMessage(ed.noPerm);
                    }





                } else if(args[0].equalsIgnoreCase("abort")) {

                    if(ed.isInSetup.contains(p)) {
                        Setup setup = Setup.getSetup(p);
                        if(setup != null) {

                            p.getInventory().clear();
                            p.getInventory().setContents(saveInv.get(p));
                            saveInv.remove(p);

                            setup.currentRegion.remove(p);
                            for(Location locs : setup.endPortalFrames) {
                                locs.getBlock().setType(Material.AIR);
                            }
                            setup.endPortalFrames.clear();
                            setup.pos1.remove(p);
                            setup.pos2.remove(p);
                            setup.currentSetup.remove(p);

                            ed.isInSetup.remove(p);
                            setup = null;

                            String ab = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.aborted"));
                            p.sendMessage(ed.pr + ab);
                        }

                    } else {
                        String notInSetup = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.notInSetup"));
                        p.sendMessage(ed.pr + notInSetup);
                    }








                } else {
                    if(p.hasPermission("edm.global")) {
                        p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                        p.sendMessage("");
                        p.sendMessage("  §7/edm §8- §7Hauptbefehl");
                        p.sendMessage("  §7/edm setup§8- §7Startet das Setup für eine neue Region");
                        p.sendMessage("  §7/edm modify <ID>§8- §7Öffnet das Modifiziermenü der gewünschten Region");
                        p.sendMessage("  §7/getmyregionid §8- §7Erfahre in welcher definierten Region du dich befindest");
                        p.sendMessage("");
                        p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                    } else {
                        p.sendMessage(ed.pr + ed.noPerm);
                    }
                }













            } else if(args.length == 2) {
                if(isInteger(args[1])) {
                int id = Integer.parseInt(args[1]);
                    if(args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("modify")) {
                        if (p.hasPermission("edm.modify")) {
                            if (FileManager.regionCfg.isSet(id + "")) {
                                Setup.modifyRegion(p, id);
                                ed.modifyRegion.put(p, id);
                            } else {
                                String doesntExist = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("modify.noExist"));
                                p.sendMessage(ed.pr + doesntExist);
                            }

                        } else {
                            p.sendMessage(ed.noPerm);
                        }




                        } else {
                        if(p.hasPermission("edm.global")) {
                            p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                            p.sendMessage("");
                            p.sendMessage("  §7/edm §8- §7Hauptbefehl");
                            p.sendMessage("  §7/edm setup§8- §7Startet das Setup für eine neue Region");
                            p.sendMessage("  §7/edm modify <ID>§8- §7Öffnet das Modifiziermenü der gewünschten Region");
                            p.sendMessage("  §7/getmyregionid §8- §7Erfahre in welcher definierten Region du dich befindest");
                            p.sendMessage("");
                            p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                        } else {
                            p.sendMessage(ed.pr + ed.noPerm);
                        }
                    }



                } else {
                    String noNumber = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("delete.hasToBeNumber"));
                    p.sendMessage(noNumber);
                }


            } else {
                if(p.hasPermission("edm.global")) {
                    p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                    p.sendMessage("");
                    p.sendMessage("  §7/edm §8- §7Hauptbefehl");
                    p.sendMessage("  §7/edm setup§8- §7Startet das Setup für eine neue Region");
                    p.sendMessage("  §7/edm modify <ID>§8- §7Öffnet das Modifiziermenü der gewünschten Region");
                    p.sendMessage("  §7/getmyregionid §8- §7Erfahre in welcher definierten Region du dich befindest");
                    p.sendMessage("");
                    p.sendMessage("§8[] §7======= §aEnderDragonModify §7======= §8[]");
                } else {
                    p.sendMessage(ed.pr + ed.noPerm);
                }
            }
        }
        return true;
    }


    private boolean isInteger(String i) {
        try {
            Integer.parseInt(i);
            return true;
        } catch(NumberFormatException ex) {
            return false;
        }
    }


    private boolean isBoolean(String i) {
        if(Boolean.parseBoolean(i)) {
            return Boolean.parseBoolean(i);
        } else {
            return false;
        }
    }
}
