package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DashMaceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command.").color(NamedTextColor.RED));
            return true;
        }

        ItemStack mace = DashMace.createDashMace();

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), mace);
            player.sendMessage(Component.text("Your inventory is full! The Dash Mace was dropped at your feet.")
                    .color(NamedTextColor.YELLOW));
        } else {
            player.getInventory().addItem(mace);
            player.sendMessage(Component.text("You received a Dash Mace! Right-click in the air to dash forward.")
                    .color(NamedTextColor.AQUA));
        }

        return true;
    }
}
