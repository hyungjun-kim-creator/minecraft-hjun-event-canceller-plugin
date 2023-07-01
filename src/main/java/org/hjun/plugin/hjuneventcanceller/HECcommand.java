package org.hjun.plugin.hjuneventcanceller;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getServer;
import static org.hjun.plugin.hjuneventcanceller.HjunEventCanceller.*;

public class HECcommand implements CommandExecutor {
    public static HjunEventCanceller plugin;

    public static void setPlugin(HjunEventCanceller MainPlugin) {
        plugin = MainPlugin;
    }

    //계획
    // azure 서버의 paperconfig에 player 충돌 허용하기
    // /hec tp sp <- 정상 작동하는지 체크
    // /hec placeblock,breakblock,teleport splist 고치기 - 작동 안함
    // config.yml에 있는 셋팅 가져오는 명령어 만들기 - console에서만 사용 가능하게 하기
    // 특정 변수만 저장하게 하는 셋팅 명령어 만들기
    // 이미 해당 플레이어 설정이 되어있는 경우에 다른 메시지 주기 EX) 현재 이미 (sp)에게 블럭 설치가 허용되어 있습니다.
    //
    // /hec placeblock [true, false] - 블럭 설치 [true, false]  0
    //                 sp (specific player) [true, false] - 특정 플레이어가 블럭 설치 [true, false]   0
    //                 splist   0
    //
    // /hec breakblock [true, false] - 블럭 파괴 [true, false]  0
    //                 sp (specific player) [true, false] - 특정 플레이어가 블럭 파괴 [true, false]   0
    //                 splist   0
    //
    // /hec tp[(teleport] allplayer [true, false] - 모든 플레이어가 tp 하는 것을 [true, false]   0
    //                   tome [true, false] - 모든 플레이어가 나에게 tp 하는 것을 [true, false]   0
    //                   tome sp (specific player) [true, false] - 나에게 특정 플레이어가 tp 하는 것을 [true, false] X
    //                   tosp  [true, false] - 특정 플레이어에게 tp 하는 것을 [true, false]    X
    //                   sp (specific player) [true, false] - 특정 플레이어가 tp 하는 것을 [true, false] 0
    //                   splist - /hec tp sp 명령어의 플레이어 리스트
    //
    // /hec tntexplode [true, false] - tnt 폭발 [true, false] 0
    // /hec creeperexplode [true, false] - creeper 폭발 [true, false] 0
    // /hec explode [true, false] - 모든 폭발 [true, false] 0
    //
    // /hec spreadevent [true, false] - 불+머쉬룸이 번지는 것(불을 일으키는 것은 허용) [true, false]   0
    // /hec blockignite [true, false] - 블럭 연소 [true, false] 0
    //
    // /hecc lock [true, false] - 플레이어들이 명령어로 해당 플러그인의 설정을 변경하는 것을 [true, false] - console에서만 사용 가능 0
    // /hecc reload - config.yml 설정 불러오기 <- 설정 기능을 넣어 더 많은 변수를 선택적으로 불러올 수 있게 하기
    //
    // allay poop
    // /hec allaypoop [true, false] - 알레이가 아이템을 줄때 '똥'을 주는 것을 [true, false]
    //
    // sp = specific player
    //
    // https://cchplugin.tistory.com/9 config.yml 참조하기
    // https://cchplugin.tistory.com/13
    // https://bukkit.org/threads/save-load-a-hashset.253660/ hashset 저장/load
    // https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/block/BlockFormEvent.html 눈 쌓이는 거 막을 수 있게 하기
    //
    // 마플 알레이 똥 제작
    // https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemStack.html
    // https://stackoverflow.com/questions/69729331/drop-item-spigot
    // https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDropItemEvent.html
    // https://bukkit.org/threads/itemstacks-and-how-to-use-them.460216/ - itemstack

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("hjuneventcanceller")){
            if(sender instanceof Player) {

                Player player = (Player) sender;

                sender.sendMessage("hjuneventcanceller 명령어가 실행되었습니다.");

                if (lock == 0) {
                    if (args.length == 0) {
                        sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                    }
                    //placeblock
                    else if (args[0].equals("placeblock")) {
                        if (args.length == 1) {
                            if (blockplace == 0) {
                                sender.sendMessage("현재 블럭 설치가 불가능합니다.");
                            } else if (blockplace == 1) {
                                sender.sendMessage("현재 블럭 설치가 가능합니다.");
                            }
                        } else if (args[1].equals("splist")) {
                            if (spblockplace.size() > 0) {
                                sender.sendMessage(ChatColor.DARK_RED + "현재 블럭 설치가 불가능한 플레이어: " + spblockplace);
                            } else if (spblockplace.size() == 0) {
                                sender.sendMessage("현재 따로 블럭 설치가 불가능한 플레이어가 없습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                blockplace = 1;
                                sender.sendMessage("지금부터 블럭 설치가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                blockplace = 0;
                                sender.sendMessage("지금부터 블럭 설치가 허용되지 않습니다.");
                            } else if (args[1].equals("sp")) {
                                if (args[3].equals("true")) {
                                    spblockplace.remove(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "에게 블럭 설치가 허용됩니다.");
                                } else if (args[3].equals("false")) {
                                    spblockplace.add(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "에게 블럭 설치가 허용되지 않습니다..");
                                } else {
                                    sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                    sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                                }
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //breakblock
                    else if (args[0].equals("breakblock")) {
                        if (args.length == 1) {
                            if (blockbreak == 0) {
                                sender.sendMessage("현재 블럭 파괴가 불가능합니다.");
                            } else if (blockbreak == 1) {
                                sender.sendMessage("현재 블럭 파괴가 가능합니다.");
                            }
                        } else if (args[1].equals("splist")) {
                            if (spblockbreak.size() > 0) {
                                sender.sendMessage(ChatColor.DARK_RED + "현재 블럭 파괴가 불가능한 플레이어: " + spblockbreak);
                            } else if (spblockbreak.size() == 0) {
                                sender.sendMessage("현재 특별히 블럭 파괴가 불가능한 플레이어가 없습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                blockbreak = 1;
                                sender.sendMessage("지금부터 블럭 파괴가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                blockbreak = 0;
                                sender.sendMessage("지금부터 블럭 파괴가 허용되지 않습니다.");
                            } else if (args[1].equals("sp")) {
                                if (args[3].equals("true")) {
                                    spblockbreak.remove(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "에게 블럭 파괴가 허용됩니다.");
                                } else if (args[3].equals("false")) {
                                    spblockbreak.add(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "에게 블럭 파괴가 허용되지 않습니다..");
                                } else {
                                    sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                    sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                                }
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //tp[teleport]
                    else if (args[0].equals("teleport") || args[0].equals("tp")) {
                        if (args.length == 1) {
                            sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                        } else if (args[1].equals("allplayer")) {
                            if (args.length == 2) {
                                if (allplayertp == 0) {
                                    sender.sendMessage("현재 모든 플레이어에게 tp가 불가능합니다.");
                                } else if (allplayertp == 1) {
                                    sender.sendMessage("현재 모든 플레이어에게 tp가 가능합니다.");
                                }
                            } else if (sender.isOp()) {
                                if (args[2].equals("true")) {
                                    allplayertp = 1;
                                    sender.sendMessage("지금부터 모든 플레이어에게 tp가 허용됩니다.");
                                } else if (args[2].equals("false")) {
                                    allplayertp = 0;
                                    sender.sendMessage("지금부터 모든 플레이어에게 tp가 허용되지 않습니다.");
                                } else {
                                    sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                    sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                                }
                            } else if (sender.isOp() == false) {
                                sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                            }
                        } else if (args[1].equals("tome")) {
                            if (args.length == 2) {
                                if (tptomehashset.contains(player) == true) {
                                    sender.sendMessage("현재 " + sender.getName() + "에게 tp가 불가능합니다.");
                                } else if (tptomehashset.contains(player) == false) {
                                    sender.sendMessage("현재 " + sender.getName() + "에게 tp가 가능합니다.");
                                }
                            } else if (sender.isOp()) {
                                if (args[2].equals("true")) {
                                    tptomehashset.remove(player);
                                    sender.sendMessage("지금부터 " + sender.getName() + "에게 tp가 허용됩니다.");
                                } else if (args[2].equals("false")) {
                                    tptomehashset.add(player);
                                    sender.sendMessage("지금부터 " + sender.getName() + "에게 tp가 허용되지 않습니다.");
                                } else {
                                    sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                    sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                                }
                            } else if (sender.isOp() == false) {
                                sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                            }
                        } else if (args[1].equals("splist")) {
                            if (spblockplace.size() > 0) {
                                sender.sendMessage(ChatColor.DARK_RED + "현재 블럭 설치가 불가능한 플레이어: " + spblockplace);
                            } else if (spblockplace.size() == 0) {
                                sender.sendMessage("현재 따로 블럭 설치가 불가능한 플레이어가 없습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("sp")) {
                                if (args[3].equals("true")) {
                                    sptphashset.remove(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "이 tp 하는 것이 허용됩니다.");
                                } else if (args[3].equals("false")) {
                                    sptphashset.add(args[2]);
                                    sender.sendMessage("지금부터 " + args[2] + "이 tp 하는 것이 허용되지 않습니다..");
                                } else {
                                    sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                    sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                                }
                            }
                        } else {
                            sender.sendMessage("명령어 입력이 잘못되었습니다.");
                            sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                        }
                    }
                    //tntexplode
                    else if (args[0].equals("tntexplode")) {
                        if (args.length == 1) {
                            if (tntexplode == 1) {
                                sender.sendMessage("현재 TNT 폭발 이벤트가 허용되어 있습니다.");
                            } else if (tntexplode == 0) {
                                sender.sendMessage("현재 TNT 폭발 이벤트가 허용되어 있지 않습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                tntexplode = 1;
                                sender.sendMessage("지금부터 TNT 폭발 이벤트가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                tntexplode = 0;
                                sender.sendMessage("지금부터 TNT 폭발 이벤트가 허용되지 않습니다.");
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //creeperexplode
                    else if (args[0].equals("creeperexplode")) {
                        if (args.length == 1) {
                            if (creeperexplode == 1) {
                                sender.sendMessage("현재 크리퍼 폭발 이벤트가 허용되어 있습니다.");
                            } else if (creeperexplode == 0) {
                                sender.sendMessage("현재 크리퍼 폭발 이벤트가 허용되어 있지 않습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                creeperexplode = 1;
                                sender.sendMessage("지금부터 크리퍼 폭발 이벤트가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                creeperexplode = 0;
                                sender.sendMessage("지금부터 크리퍼 폭발 이벤트가 허용되지 않습니다.");
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    else if (args[0].equals("explode")) {
                        if (args.length == 1) {
                            if (explode == 1) {
                                sender.sendMessage("현재 모든 폭발 이벤트가 허용되어 있습니다.");
                            } else if (explode == 0) {
                                sender.sendMessage("현재 모든 폭발 이벤트가 허용되어 있지 않습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                explode = 1;
                                sender.sendMessage("지금부터 모든 폭발 이벤트가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                explode = 0;
                                sender.sendMessage("지금부터 모든 폭발 이벤트가 허용되지 않습니다.");
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //spreadevent
                    else if (args[0].equals("spreadevent")) {
                        if (args.length == 1) {
                            if (spreadevent == 1) {
                                sender.sendMessage("현재 Spread 이벤트가 허용되어 있습니다.");
                            } else if (spreadevent == 0) {
                                sender.sendMessage("현재 Spread 이벤트가 허용되어 있지 않습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                spreadevent = 1;
                                sender.sendMessage("지금부터 Spread 이벤트가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                spreadevent = 0;
                                sender.sendMessage("지금부터 Spread 이벤트가 허용되지 않습니다.");
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //blockignite
                    else if (args[0].equals("blockignite")) {
                        if (args.length == 1) {
                            if (blockignite == 1) {
                                sender.sendMessage("현재 블럭 연소 이벤트가 허용되어 있습니다.");
                            } else if (blockignite == 0) {
                                sender.sendMessage("현재 블럭 연소 이벤트가 허용되어 있지 않습니다.");
                            }
                        } else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                blockignite = 1;
                                sender.sendMessage("지금부터 블럭 연소 이벤트가 허용됩니다.");
                            } else if (args[1].equals("false")) {
                                blockignite = 0;
                                sender.sendMessage("지금부터 블럭 연소 이벤트가 허용되지 않습니다.");
                            } else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        } else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    //allaypoop
                    else if(args[0].equals("allaypoop")){
                        if (args.length == 1) {
                            if (allaypoop == 1) {
                                sender.sendMessage("현재 알레이가 플레이어에게 똥만 줍니다.");
                            } else if (allaypoop == 0) {
                                sender.sendMessage("현재 알레이가 플레이어에게 똥을 주지 않습니다.");
                            }
                        }
                        else if (sender.isOp()) {
                            if (args[1].equals("true")) {
                                allaypoop = 1;
                                sender.sendMessage("지금부터 알레이가 플레이어에게 똥만 줍니다.");
                            }
                            else if (args[1].equals("false")) {
                                allaypoop = 0;
                                sender.sendMessage("지금부터 알레이가 플레이어에게 똥을 주지 않습니다.");
                            }
                            else {
                                sender.sendMessage("명령어 입력이 잘못되었습니다.");
                                sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                            }
                        }
                        else if (sender.isOp() == false) {
                            sender.sendMessage("관리자 권한(op)가 없어서 해당 명령어 입력이 불가능합니다.");
                        }
                    }
                    else {
                        sender.sendMessage("명령어 입력이 잘못되었습니다.");
                        sender.sendMessage("§e/" + label + " help §f도움말을 표시합니다.");
                    }
                }
                else if (lock == 1) {
                    sender.sendMessage("현재 해당 플러그인의 명령어 잠금 기능이 작동되고 있습니다.");
                    sender.sendMessage("플러그인 폴더의 config.yml을 통해서만 해당 명령어를 잠금 해제할 수 있습니다.");
                }
            }
        }
        return false;
    }
}
