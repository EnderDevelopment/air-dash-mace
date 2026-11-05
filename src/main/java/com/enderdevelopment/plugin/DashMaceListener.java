package com.enderdevelopment.plugin;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DashMaceListener implements Listener {

    private static final long COOLDOWN_TICKS = 60L; // 3 seconds
    private static final double DASH_SPEED = 2.5;

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Only when holding the dash mace
        if (!DashMace.isDashMace(item)) {
            return;
        }

        Action action = event.getAction();
        // Dash when right-clicking while in the air
        if (action != Action.RIGHT_CLICK_AIR) {
            return;
        }

        // Only dash if the player is in the air (not on the ground)
        if (player.isOnGround()) {
            return;
        }

        // Check cooldown
        UUID uuid = player.getUniqueId();
        long now = player.getWorld().getFullTime();
        Long lastUse = cooldowns.get(uuid);
        if (lastUse != null) {
            long ticksPassed = now - lastUse;
            if (ticksPassed < COOLDOWN_TICKS) {
                long remaining = COOLDOWN_TICKS - ticksPassed;
                double seconds = remaining / 20.0;
                player.sendMessage(net.kyori.adventure.text.Component.text(
                        "Dash is on cooldown! Wait " + String.format("%.1f", seconds) + " more seconds.")
                        .color(net.kyori.adventure.text.format.NamedTextColor.RED));
                return;
            }
        }

        // Perform the dash
        cooldowns.put(uuid, now);

        Location loc = player.getLocation();
        Vector direction = loc.getDirection().normalize();
        direction.multiply(DASH_SPEED);
        direction.setY(direction.getY() * 0.5);

        player.setVelocity(direction);

        player.sendMessage(net.kyori.adventure.text.Component.text("Whoosh! You dashed forward!")
                .color(net.kyori.adventure.text.format.NamedTextColor.AQUA));
    }
}
