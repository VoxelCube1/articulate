package dev.proplayer919.articulate.module.modules;

import dev.proplayer919.articulate.module.BaseModule;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Module for controlling max health
 */
public class HealthModule extends BaseModule {

    @Override
    public String getName() {
        return "health";
    }

    @Override
    public String getDisplayName() {
        return "Max Health";
    }

    @Override
    public boolean execute(Player player, String state) {
        switch (state.toLowerCase()) {
            case "toggle":
                double currentHealth = Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
                sendCurrentMessage(player, (int) currentHealth);
                break;
            case "default":
                Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(20.0);
                player.setHealth(20.0);
                sendValueMessage(player, 20);
                break;
            default:
                try {
                    int health = Integer.parseInt(state);
                    if (health < 1 || health > 2048) {
                        sendErrorMessage(player, "Max health must be between 1 and 2048.");
                        return false;
                    } else {
                        double oldMaxHealth = Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
                        Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(health);

                        // If the new max health is lower than current health, adjust current health
                        if (player.getHealth() > health) {
                            player.setHealth(health);
                        }
                        // If the new max health is higher and player was at max health, restore to full
                        else if (player.getHealth() == oldMaxHealth) {
                            player.setHealth(health);
                        }

                        sendValueMessage(player, health);
                    }
                } catch (NumberFormatException e) {
                    sendErrorMessage(player, "Invalid health value.");
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public String getUsage() {
        return "/articulate health [toggle|default|1-2048]";
    }

    @Override
    public java.util.List<String> getStates() {
        return java.util.Arrays.asList("toggle", "default", "20", "40", "60", "80", "100", "200", "500", "1000");
    }
}
