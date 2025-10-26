package dev.proplayer919.articulate.module;

import org.bukkit.entity.Player;

/**
 * Base interface for all Articulate modules
 */
public interface Module {
    /**
     * Get the name of this module
     * @return The module name (lowercase)
     */
    String getName();

    /**
     * Get the display name of this module
     * @return The display name for messages
     */
    String getDisplayName();

    /**
     * Execute the module with the given state
     * @param player The player executing the command
     * @param state The state parameter (toggle, on, off, or custom value)
     * @return true if the command was handled successfully
     */
    boolean execute(Player player, String state);

    /**
     * Get the usage help for this module
     * @return Usage help string
     */
    String getUsage();

    /**
     * Get available states/parameters for this module (used for tab completion)
     * @return List of available states
     */
    java.util.List<String> getStates();
}
