package dev.proplayer919.articulate.module.modules;

import dev.proplayer919.articulate.module.BaseModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Module for god mode (no damage)
 */
public class GodModule extends BaseModule implements Listener {

    private static final Map<Player, Boolean> godPlayers = new HashMap<>();

    @Override
    public String getName() {
        return "god";
    }

    @Override
    public String getDisplayName() {
        return "God Mode";
    }

    @Override
    public boolean execute(Player player, String state) {
        switch (state.toLowerCase()) {
            case "toggle":
                boolean currentState = isGodMode(player);
                setGodMode(player, !currentState);
                sendToggleMessage(player, !currentState);
                break;
            case "on":
                setGodMode(player, true);
                sendToggleMessage(player, true);
                break;
            case "off":
                setGodMode(player, false);
                sendToggleMessage(player, false);
                break;
            case "default":
                setGodMode(player, false);
                sendToggleMessage(player, false);
                break;
            default:
                sendErrorMessage(player, "Use: toggle, on, off, or default");
                return false;
        }
        return true;
    }

    @Override
    public String getUsage() {
        return "/articulate god [toggle|on|off|default]";
    }

    @Override
    public java.util.List<String> getStates() {
        return java.util.Arrays.asList("toggle", "on", "off", "default");
    }

    public static boolean isGodMode(Player player) {
        return godPlayers.getOrDefault(player, false);
    }

    public static void setGodMode(Player player, boolean enabled) {
        if (enabled) {
            godPlayers.put(player, true);
        } else {
            godPlayers.remove(player);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (isGodMode(player)) {
                event.setCancelled(true);
            }
        }
    }

    public static void cleanup(Player player) {
        godPlayers.remove(player);
    }
}
