package de.relativv.enderdragon.listener;

import de.relativv.enderdragon.main.EDModify;
import de.relativv.enderdragon.utils.Cuboid;
import de.relativv.enderdragon.utils.FileManager;
import de.relativv.enderdragon.utils.ItemBuilder;
import de.relativv.enderdragon.utils.Setup;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class InventoryClick implements Listener {

    private EDModify ed;
    public InventoryClick(EDModify ed) {
        this.ed = ed;
    }

    private HashMap<Player, Integer> whichPhase = new HashMap<>();
    public static ArrayList<Player> bypass = new ArrayList<>();
    private ArrayList<Player> readyConfirm = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != null) {
            if (ed.isInSetup.contains(p)) {
                Setup setup = Setup.getSetup(p);
                if (setup != null) {
                    Inventory inv = e.getInventory();
                    if (e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.mainTitle")))) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

                        String health = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.health"));
                        String maxHealth = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.maxhealth"));
                        String absorption = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.absorption"));
                        String silent = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.silent"));
                        String glowing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.glowing"));
                        String ai = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.ai"));
                        String despawnFarAway = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.despawnFarAway"));
                        String passengerLastSet = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.passenger"));


                        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));

                        int maximumHealth = FileManager.getCfg().getInt("setup.step3.inventory.maximumHealth");
                        int maximumAbsorption = FileManager.getCfg().getInt("setup.step3.inventory.maximumAbsorption");


                        if (e.getSlot() == 1) {
                            if (setup.getHealth() > 0) {
                                setup.setHealth(setup.getHealth() - 5);
                                inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                                        .setDisPlayname(health)
                                        .setLore("", lore.replaceAll("%state%", setup.getHealthString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        } else if (e.getSlot() == 3) {
                            if (setup.getHealth() < maximumHealth) {
                                setup.setHealth(setup.getHealth() + 5);
                                inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                                        .setDisPlayname(health)
                                        .setLore("", lore.replaceAll("%state%", setup.getHealthString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        }


                        if (e.getSlot() == 5) {
                            if (setup.getMaxHealth() > 0) {
                                setup.setMaxHealth(setup.getMaxHealth() - 5);
                                inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                                        .setDisPlayname(maxHealth)
                                        .setLore("", lore.replaceAll("%state%", setup.getMaxHealthString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        } else if (e.getSlot() == 7) {
                            if (setup.getMaxHealth() < maximumHealth) {
                                setup.setMaxHealth(setup.getMaxHealth() + 5);
                                inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                                        .setDisPlayname(maxHealth)
                                        .setLore("", lore.replaceAll("%state%", setup.getMaxHealthString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        }


                        if (e.getSlot() == 10) {
                            if (setup.getAbsorption() > 0) {
                                setup.setAbsorption(setup.getAbsorption() - 5);
                                inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                                        .setDisPlayname(absorption)
                                        .setLore("", lore.replaceAll("%state%", setup.getAbsorptionString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        } else if (e.getSlot() == 12) {
                            if (setup.getAbsorption() < maximumAbsorption) {
                                setup.setAbsorption(setup.getAbsorption() + 5);
                                inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                                        .setDisPlayname(absorption)
                                        .setLore("", lore.replaceAll("%state%", setup.getAbsorptionString() + ""))
                                        .build());
                                p.updateInventory();
                            }
                        }


                        if (e.getSlot() == 28) {
                            setup.setSilence(!setup.getSilence());
                            inv.setItem(28, new ItemBuilder(Material.ELYTRA, 1)
                                    .setDisPlayname(silent)
                                    .setLore("", lore.replaceAll("%state%", setup.isSilent()))
                                    .build());
                            p.updateInventory();
                        } else if (e.getSlot() == 29) {
                            setup.setGlowing(!setup.getGlowing());
                            inv.setItem(29, new ItemBuilder(Material.GLOWSTONE_DUST, 1)
                                    .setDisPlayname(glowing)
                                    .setLore("", lore.replaceAll("%state%", setup.isGlowing()))
                                    .build());
                            p.updateInventory();
                        } else if (e.getSlot() == 30) {
                            setup.setAi(!setup.getAi());
                            inv.setItem(30, new ItemBuilder(Material.COMMAND_BLOCK, 1)
                                    .setDisPlayname(ai)
                                    .setLore("", lore.replaceAll("%state%", setup.isAi()))
                                    .build());
                            p.updateInventory();
                        } else if (e.getSlot() == 37) {
                            setup.setRemoveWhenFarAway(!setup.getRemoveWhenFarAway());
                            inv.setItem(37, new ItemBuilder(Material.BLACK_WOOL, 1)
                                    .setDisPlayname(despawnFarAway)
                                    .setLore("", lore.replaceAll("%state%", setup.isRemoveWhenFarAway()))
                                    .build());
                            p.updateInventory();
                        } else if(e.getSlot() == 38) {
                            setup.setPassenger(!setup.getPassenger());
                            inv.setItem(38, new ItemBuilder(Material.SADDLE, 1)
                                    .setDisPlayname(passengerLastSet)
                                    .setLore("", lore.replaceAll("%state%", setup.isPassenger()))
                                    .build());
                            p.updateInventory();
                        }


                        if (e.getSlot() == 39) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.chooseStartAttack();


                        } else if (e.getSlot() == 33) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.editAttacks();
                        }

                        if (e.getSlot() == 44) {
                            if (setup.getHealth() <= setup.getMaxHealth()) {
                                bypass.add(p);
                                p.closeInventory();

                                String chooseName = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.chooseDisplayNameMSG"));
                                p.sendMessage(ed.pr + chooseName);

                            } else {
                                String err = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.healthError"));
                                p.sendMessage(ed.pr + err);
                                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.5F);
                            }
                        }


                    } else if (e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.attacksTitle")))) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);


                        if (e.getSlot() == 11) {
                            whichPhase.put(p, 1);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);

                            setup.openAllAttackInventory();

                        } else if (e.getSlot() == 12) {
                            whichPhase.put(p, 2);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);

                            setup.openAllAttackInventory();

                        } else if (e.getSlot() == 13) {
                            whichPhase.put(p, 3);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);

                            setup.openAllAttackInventory();

                        } else if (e.getSlot() == 14) {
                            whichPhase.put(p, 4);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);

                            setup.openAllAttackInventory();

                        } else if (e.getSlot() == 15) {
                            whichPhase.put(p, 5);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.openAllAttackInventory();

                        }

                        String timerUntilNext = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.timerUntilNext"));
                        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));
                        String rndmze = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.randomizeAttacks"));

                        int maximumTimeUntilNextAttack = FileManager.getCfg().getInt("setup.step3.inventory.maximumTimeUntilNextAttack");

                        if (e.getSlot() == 29) {
                            setup.setRandomizeAttacks(!setup.getRandomizeAttacks());
                            inv.setItem(29, new ItemBuilder(Material.ENDER_PEARL, 1)
                                    .setDisPlayname(rndmze)
                                    .setLore("", lore.replaceAll("%state%", setup.isRandomizeAttacks()))
                                    .build());
                            p.updateInventory();
                        } else if (e.getSlot() == 31) {
                            if (setup.getTimeUntilNextAttack() > 0) {
                                setup.setTimeUntilNextAttack(setup.getTimeUntilNextAttack() - 20);
                                inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                                        .setDisPlayname(timerUntilNext)
                                        .setLore("", lore.replaceAll("%state%", setup.getTimeUntilNextAttackString()))
                                        .build());
                                p.updateInventory();
                            }
                        } else if (e.getSlot() == 33) {
                            if (setup.getTimeUntilNextAttack() < maximumTimeUntilNextAttack * 20) {
                                setup.setTimeUntilNextAttack(setup.getTimeUntilNextAttack() + 20);
                                inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                                        .setDisPlayname(timerUntilNext)
                                        .setLore("", lore.replaceAll("%state%", setup.getTimeUntilNextAttackString()))
                                        .build());
                                p.updateInventory();
                            }
                        }

                        if (e.getSlot() == 53) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.editEnderDragon();
                        }


                    } else if (e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.allAttacksTitle")))) {
                        e.setCancelled(true);

                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

                        if (whichPhase.containsKey(p)) {
                            if (e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16) {
                                setup.setPhase(Setup.getPhaseStringByItem(e.getCurrentItem().getType()), whichPhase.get(p));
                                whichPhase.remove(p);
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                setup.editAttacks();
                                p.updateInventory();
                            }

                            if (e.getSlot() == 35) {
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                setup.editAttacks();
                                whichPhase.remove(p);
                            }
                        } else {
                            p.sendMessage("ERROR! Player is not in HashMap 'whichPhase'");
                        }


                    } else if (e.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startAttackTitle")))) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

                        if (e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16) {
                            setup.setBeginPhase(Setup.getPhaseStringByItem(e.getCurrentItem().getType()));
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.editEnderDragon();
                            p.updateInventory();
                        }

                        if (e.getSlot() == 35) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            setup.editEnderDragon();
                        }

                    }

                }


                //-------------------------------------------------------------------------------------------------------------//

            } else {
                Inventory inv = e.getInventory();
                if (ed.modifyRegion.containsKey(p)) {
                    int region = ed.modifyRegion.get(p);
                    if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.mainTitle")) + " §8(§bEdit§8)")) {
                        if (ed.modifyRegion.containsKey(p)) {
                            e.setCancelled(true);
                            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);


                            String health = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.health"));
                            String maxHealth = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.maxhealth"));
                            String absorption = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.absorption"));
                            String silent = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.silent"));
                            String glowing = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.glowing"));
                            String ai = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.ai"));
                            String despawnFarAway = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.despawnFarAway"));
                            String passengerLastSet = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.passenger"));

                            String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));

                            int maximumHealth = FileManager.getCfg().getInt("setup.step3.inventory.maximumHealth");
                            int maximumAbsorption = FileManager.getCfg().getInt("setup.step3.inventory.maximumAbsorption");


                            if (e.getSlot() == 1) {
                                if (Setup.getHealthFromFile(region) > 0) {
                                    Setup.setHealthFromFile(region, Setup.getHealthFromFile(region) - 5);
                                    inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                                            .setDisPlayname(health)
                                            .setLore("", lore.replaceAll("%state%", Setup.getHealthFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            } else if (e.getSlot() == 3) {
                                if (Setup.getHealthFromFile(region) < maximumHealth) {
                                    Setup.setHealthFromFile(region, Setup.getHealthFromFile(region) + 5);
                                    inv.setItem(2, new ItemBuilder(Material.RED_DYE, 1)
                                            .setDisPlayname(health)
                                            .setLore("", lore.replaceAll("%state%", Setup.getHealthFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            }


                            if (e.getSlot() == 5) {
                                if (Setup.getMaxHealthFromFile(region) > 0) {
                                    Setup.setMaxHealthFromFile(region, Setup.getMaxHealthFromFile(region) - 5);
                                    inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                                            .setDisPlayname(maxHealth)
                                            .setLore("", lore.replaceAll("%state%", Setup.getMaxHealthFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            } else if (e.getSlot() == 7) {
                                if (Setup.getMaxHealthFromFile(region) < maximumHealth) {
                                    Setup.setMaxHealthFromFile(region, Setup.getMaxHealthFromFile(region) + 5);
                                    inv.setItem(6, new ItemBuilder(Material.REDSTONE, 1)
                                            .setDisPlayname(maxHealth)
                                            .setLore("", lore.replaceAll("%state%", Setup.getMaxHealthFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            }


                            if (e.getSlot() == 10) {
                                if (Setup.getAbsorptionFromFile(region) > 0) {
                                    Setup.setAbsorptionFromFile(region, Setup.getAbsorptionFromFile(region) - 5);
                                    inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                                            .setDisPlayname(absorption)
                                            .setLore("", lore.replaceAll("%state%", Setup.getAbsorptionFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            } else if (e.getSlot() == 12) {
                                if (Setup.getAbsorptionFromFile(region) < maximumAbsorption) {
                                    Setup.setAbsorptionFromFile(region, Setup.getAbsorptionFromFile(region) + 5);
                                    inv.setItem(11, new ItemBuilder(Material.GOLDEN_APPLE, 1)
                                            .setDisPlayname(absorption)
                                            .setLore("", lore.replaceAll("%state%", Setup.getAbsorptionFromFile(region) / 2 + " Herzen"))
                                            .build());
                                    p.updateInventory();
                                }
                            }


                            if (e.getSlot() == 28) {
                                Setup.setSilenceFromFile(region, !Setup.getSilenceFromFile(region));
                                inv.setItem(28, new ItemBuilder(Material.ELYTRA, 1)
                                        .setDisPlayname(silent)
                                        .setLore("", lore.replaceAll("%state%", Setup.isSilenceFromFile(region)))
                                        .build());
                                p.updateInventory();
                            } else if (e.getSlot() == 29) {
                                Setup.setGlowingFromFile(region, !Setup.getGlowingFromFile(region));
                                inv.setItem(29, new ItemBuilder(Material.GLOWSTONE_DUST, 1)
                                        .setDisPlayname(glowing)
                                        .setLore("", lore.replaceAll("%state%", Setup.isGlowingFromFile(region)))
                                        .build());
                                p.updateInventory();
                            } else if (e.getSlot() == 30) {
                                Setup.setAiFromFile(region, !Setup.getAiFromFile(region));
                                inv.setItem(30, new ItemBuilder(Material.COMMAND_BLOCK, 1)
                                        .setDisPlayname(ai)
                                        .setLore("", lore.replaceAll("%state%", Setup.isAiFromFile(region)))
                                        .build());
                                p.updateInventory();
                            } else if (e.getSlot() == 37) {
                                Setup.setRemoveFarAwayFromFile(region, !Setup.getRemoveFarAwayFromFile(region));
                                inv.setItem(37, new ItemBuilder(Material.BLACK_WOOL, 1)
                                        .setDisPlayname(despawnFarAway)
                                        .setLore("", lore.replaceAll("%state%", Setup.isRemoveFarAwayFromFile(region)))
                                        .build());
                                p.updateInventory();
                            } else if (e.getSlot() == 38) {
                                Setup.setPassengerFromFile(region, !Setup.getPassengerFromFile(region));
                                inv.setItem(38, new ItemBuilder(Material.SADDLE, 1)
                                        .setDisPlayname(passengerLastSet)
                                        .setLore("", lore.replaceAll("%state%", Setup.isPassengerFromFile(region)))
                                        .build());
                                p.updateInventory();
                            }


                            if (e.getSlot() == 39) {
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                Setup.modifyStartAttack(region, p);


                            } else if (e.getSlot() == 33) {
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                Setup.modifyAttacks(region, p);
                            }


                        } else {
                            e.setCancelled(true);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                        }


                    } else if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.startAttackTitle")) + " §8(§bEdit§8)")) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

                        if (e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16) {
                            Setup.setBeginPhaseFromFile(region, Setup.getPhaseStringByItem(e.getCurrentItem().getType()));
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyRegion(p, region);
                            p.updateInventory();
                        }

                        if (e.getSlot() == 35) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyRegion(p, region);
                        }


                    } else if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.attacksTitle")) + " §8(§bEdit§8)")) {
                        e.setCancelled(true);
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);


                        if (e.getSlot() == 11) {
                            whichPhase.put(p, 1);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyAllAttacks(p);

                        } else if (e.getSlot() == 12) {
                            whichPhase.put(p, 2);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyAllAttacks(p);

                        } else if (e.getSlot() == 13) {
                            whichPhase.put(p, 3);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyAllAttacks(p);

                        } else if (e.getSlot() == 14) {
                            whichPhase.put(p, 4);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyAllAttacks(p);

                        } else if (e.getSlot() == 15) {
                            whichPhase.put(p, 5);
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyAllAttacks(p);

                        }

                        String timerUntilNext = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.timerUntilNext"));
                        String lore = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.itemLore"));
                        String rndmze = ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.randomizeAttacks"));

                        int maximumTimeUntilNextAttack = FileManager.getCfg().getInt("setup.step3.inventory.maximumTimeUntilNextAttack");

                        if (e.getSlot() == 29) {
                            Setup.setRandomizeAttackFromFile(region, !Setup.getRandomizeAttackFromFile(region));
                            inv.setItem(29, new ItemBuilder(Material.ENDER_PEARL, 1)
                                    .setDisPlayname(rndmze)
                                    .setLore("", lore.replaceAll("%state%", Setup.isRandomizeAttackFromFile(region)))
                                    .build());
                            p.updateInventory();
                        } else if (e.getSlot() == 31) {
                            if (Setup.getTimeUntilNextAttackFromFile(region) > 0) {
                                Setup.setTimeUntilNextAttackFromFile(region, Setup.getTimeUntilNextAttackFromFile(region) - 20);
                                inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                                        .setDisPlayname(timerUntilNext)
                                        .setLore("", lore.replaceAll("%state%", Setup.getTimeUntilNextAttackStringFile(region)))
                                        .build());
                                p.updateInventory();
                            }
                        } else if (e.getSlot() == 33) {
                            if (Setup.getTimeUntilNextAttackFromFile(region) < maximumTimeUntilNextAttack * 20) {
                                Setup.setTimeUntilNextAttackFromFile(region, Setup.getTimeUntilNextAttackFromFile(region) + 20);
                                inv.setItem(32, new ItemBuilder(Material.CLOCK, 1)
                                        .setDisPlayname(timerUntilNext)
                                        .setLore("", lore.replaceAll("%state%", Setup.getTimeUntilNextAttackStringFile(region)))
                                        .build());
                                p.updateInventory();
                            }
                        }

                        if (e.getSlot() == 53) {
                            bypass.add(p);
                            p.closeInventory();
                            bypass.remove(p);
                            Setup.modifyRegion(p, region);
                        }


                    } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', FileManager.getCfg().getString("setup.step3.inventory.allAttacksTitle")) + " §8(§bEdit§8)")) {
                        e.setCancelled(true);

                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);

                        if (whichPhase.containsKey(p)) {
                            if (e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16) {
                                Setup.setPhaseFromFile(Setup.getPhaseStringByItem(e.getCurrentItem().getType()), region, whichPhase.get(p));
                                whichPhase.remove(p);
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                Setup.modifyAttacks(region, p);
                                p.updateInventory();
                            }

                            if (e.getSlot() == 35) {
                                bypass.add(p);
                                p.closeInventory();
                                bypass.remove(p);
                                Setup.modifyAttacks(region, p);
                                whichPhase.remove(p);
                            }
                        } else {
                            p.sendMessage("ERROR! Player is not in HashMap 'whichPhase'");
                        }
                    }
                }
            }
        }
    }

}
