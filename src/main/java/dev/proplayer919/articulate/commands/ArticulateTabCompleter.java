package dev.proplayer919.articulate.commands;

import dev.proplayer919.articulate.module.ModuleManager;
import dev.proplayer919.articulate.module.Module;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tab completer for the Articulate command
 */
public class ArticulateTabCompleter implements TabCompleter {
    private final ModuleManager moduleManager;

    // Common states that most modules support (fallback)
    private static final List<String> COMMON_STATES = Arrays.asList("toggle", "on", "off");

    public ArticulateTabCompleter(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Only provide completions for players
        if (!(sender instanceof Player player)) {
            return Collections.emptyList();
        }

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // First argument: module names + help command
            completions.addAll(getAvailableModules(player));
            completions.add("help");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help")) {
                // Help command - show available modules for help
                completions.addAll(getAvailableModules(player));
            } else {
                // Second argument: states for the specified module
                String moduleName = args[0].toLowerCase();
                completions.addAll(getModuleStates(player, moduleName));
            }
        }
        // For args.length > 2, we don't provide completions as modules typically only take 2 arguments

        // Filter completions based on what the user has typed
        String currentArg = args.length > 0 ? args[args.length - 1].toLowerCase() : "";
        return completions.stream()
                .filter(completion -> completion.toLowerCase().startsWith(currentArg))
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Get all available module names that the player has permission to use
     * @param player The player requesting tab completion
     * @return List of available module names
     */
    private List<String> getAvailableModules(Player player) {
        List<String> availableModules = new ArrayList<>();

        boolean hasOverallPermission = player.hasPermission("articulate.use");

        for (String moduleName : moduleManager.getModuleNames()) {
            boolean hasModulePermission = player.hasPermission("articulate.module." + moduleName);
            if (hasOverallPermission || hasModulePermission) {
                availableModules.add(moduleName);
            }
        }

        return availableModules;
    }

    /**
     * Get available states for a specific module
     * @param player The player requesting tab completion
     * @param moduleName The name of the module
     * @return List of available states for the module
     */
    private List<String> getModuleStates(Player player, String moduleName) {
        // Check if player has permission for this module
        boolean hasOverallPermission = player.hasPermission("articulate.use");
        boolean hasModulePermission = player.hasPermission("articulate.module." + moduleName);

        if (!hasOverallPermission && !hasModulePermission) {
            return Collections.emptyList();
        }

        // Get the module and return its states
        Module module = moduleManager.getModule(moduleName);
        if (module != null) {
            return module.getStates();
        }

        // Fallback to common states if module not found
        return COMMON_STATES;
    }
}
