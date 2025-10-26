package dev.proplayer919.articulate.module;

import dev.proplayer919.articulate.helpers.ActionBarHelper;
import org.bukkit.entity.Player;

/**
 * Abstract base class for modules that provides common functionality
 */
public abstract class BaseModule implements Module {

    /**
     * Send a toggle message to the player
     * @param player The player to send the message to
     * @param state The toggle state
     */
    protected void sendToggleMessage(Player player, Boolean state) {
        String stateStr = state ? "&aON" : "&cOFF";
        String message = String.format("&fToggled &b%s %s", getDisplayName(), stateStr);
        ActionBarHelper.sendActionBarMessage(player, message);
    }

    /**
     * Send a value change message to the player
     * @param player The player to send the message to
     * @param value The new value
     */
    protected void sendValueMessage(Player player, Object value) {
        String message = String.format("&fSet &b%s &fto &a%s", getDisplayName(), value.toString());
        ActionBarHelper.sendActionBarMessage(player, message);
    }

    /**
     * Send a current value message to the player
     * @param player The player to send the message to
     * @param value The current value
     */
    protected void sendCurrentMessage(Player player, Object value) {
        String message = String.format("&fCurrent &b%s &fis &a%s", getDisplayName(), value.toString());
        ActionBarHelper.sendActionBarMessage(player, message);
    }

    /**
     * Send an error message to the player
     * @param player The player to send the message to
     * @param error The error message
     */
    protected void sendErrorMessage(Player player, String error) {
        ActionBarHelper.sendActionBarMessage(player, "&c" + error);
    }

    /**
     * Default implementation of getStates - provides common toggle states
     * Override this method in subclasses to provide module-specific states
     * @return List of common states
     */
    @Override
    public java.util.List<String> getStates() {
        return java.util.Arrays.asList("toggle", "on", "off");
    }
}
