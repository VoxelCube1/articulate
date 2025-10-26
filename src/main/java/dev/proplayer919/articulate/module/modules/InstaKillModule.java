package dev.proplayer919.articulate.module.modules;

import dev.proplayer919.articulate.module.BaseModule;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Module for instant kill (one-hit kill any mob)
 */
public class InstaKillModule extends BaseModule implements Listener {

    private static final Map<Player, Boolean> instaKillPlayers = new HashMap<>();

    @Override
    public String getName() {
        return "instakill";
    }

    @Override
    public String getDisplayName() {
        return "Instant Kill";
    }

    @Override
    public boolean execute(Player player, String state) {
        switch (state.toLowerCase()) {
            case "toggle":
                boolean currentState = isInstaKillMode(player);
                setInstaKillMode(player, !currentState);
                sendToggleMessage(player, !currentState);
                break;
            case "on":
                setInstaKillMode(player, true);
                sendToggleMessage(player, true);
                break;
            case "off":
                setInstaKillMode(player, false);
                sendToggleMessage(player, false);
                break;
            case "default":
                setInstaKillMode(player, false);
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
        return "/articulate instakill [toggle|on|off|default]";
    }

    @Override
    public java.util.List<String> getStates() {
        return java.util.Arrays.asList("toggle", "on", "off", "default");
    }

    public static boolean isInstaKillMode(Player player) {
        return instaKillPlayers.getOrDefault(player, false);
    }

    public static void setInstaKillMode(Player player, boolean enabled) {
        if (enabled) {
            instaKillPlayers.put(player, true);
        } else {
            instaKillPlayers.remove(player);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Entity entity = event.getEntity();

            if (isInstaKillMode(player) && entity instanceof LivingEntity livingEntity && !(entity instanceof Player)) {
                // Set damage to the entity's max health to instantly kill it
                event.setDamage(Objects.requireNonNull(livingEntity.getAttribute(Attribute.MAX_HEALTH)).getValue());
            }
        }
    }

    public static void cleanup(Player player) {
        instaKillPlayers.remove(player);
    }
}
