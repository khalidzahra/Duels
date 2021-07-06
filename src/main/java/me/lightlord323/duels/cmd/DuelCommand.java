package me.lightlord323.duels.cmd;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.DPlayer;
import me.lightlord323.duels.duel.Duel;
import me.lightlord323.duels.handler.Handler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Khalid on 7/6/21.
 */
public class DuelCommand extends Handler implements CommandExecutor {

    public DuelCommand(Duels plugin) {
        super(plugin);
        plugin.getCommand("duel").setExecutor(this);
    }

    private final String DUEL_START_MESSAGE = ChatColor.BLUE + "============================\n" +
            ChatColor.RED + "" + ChatColor.BOLD + "Your duel has started!\n" +
            ChatColor.BLUE + "============================\n";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.RED + "Usage:\n\t/duel <player>\n\t/duel accept\n\t/duel stats");
            return true;
        }

        if (args[0].equalsIgnoreCase("accept")) {
            DPlayer dPlayer = getPlugin().getHandlerManager().getDPlayerHandler().getDPlayer(player);
            if (dPlayer != null) {
                if (dPlayer.getCurrentChallenger() != null) {
                    Player challenger = Bukkit.getPlayer(dPlayer.getCurrentChallenger());
                    if (challenger != null) {
                        getPlugin().getHandlerManager().getDuelHandler().registerDuel(new Duel(player, challenger));
                        player.sendMessage(DUEL_START_MESSAGE);
                        challenger.sendMessage(DUEL_START_MESSAGE);
                        dPlayer.setCurrentChallenger(null);
                        return true;
                    }
                    player.sendMessage(ChatColor.RED + "Your challenger has logged off.");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "You do not have any challenges.");
                return true;
            }
            player.sendMessage(ChatColor.RED + "Something unexpected happened.");
            return true;
        }

        if (args[0].equalsIgnoreCase("stats")) {
            DPlayer dPlayer = getPlugin().getHandlerManager().getDPlayerHandler().getDPlayer(player);
            if (dPlayer == null) {
                player.sendMessage(ChatColor.RED + "No stats have been recorded for you.");
                return true;
            }
            player.sendMessage(ChatColor.BLUE + "Stats for " + ChatColor.GREEN + player.getName());
            player.sendMessage(
                    ChatColor.GREEN + "Wins: " + ChatColor.YELLOW + dPlayer.getWins() + "\n" +
                            ChatColor.WHITE + "Draws: " + ChatColor.YELLOW + dPlayer.getDraws() + "\n" +
                            ChatColor.RED + "Losses: " + ChatColor.YELLOW + dPlayer.getLosses());
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }
        DPlayer dPlayer = getPlugin().getHandlerManager().getDPlayerHandler().getDPlayer(target);
        if (dPlayer == null) {
            player.sendMessage(ChatColor.RED + "Something unexpected happened.");
            return true;
        }
        dPlayer.setCurrentChallenger(player.getUniqueId());
        target.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.RED + " has challenged you to a duel!");
        target.playSound(target.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
        player.sendMessage(ChatColor.GREEN + "Challenge issued.");
        return true;
    }
}
