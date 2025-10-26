package dev.proplayer919.articulate.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.proplayer919.articulate.helpers.ActionBarHelper;
import dev.proplayer919.articulate.module.ModuleManager;

public class ArticulateCommand implements CommandExecutor {
    private final String commandName;
    private final ModuleManager moduleManager;

    public ArticulateCommand(String commandName, ModuleManager moduleManager) {
        this.commandName = commandName;
        this.moduleManager = moduleManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            // Check if sender is console
            if (!(sender instanceof Player player)) {
                sender.sendMessage("This command can only be executed by a player.");
                return true;
            }

            // Check arguments
            if (args.length == 0) {
                showHelp(player);
                return true;
            }

            // Check for help command
            if (args[0].equalsIgnoreCase("help")) {
                if (args.length == 1) {
                    showHelp(player);
                } else {
                    showModuleHelp(player, args[1]);
                }
                return true;
            }

            // Get module and state
            String moduleName = args[0].toLowerCase();
            String state = args.length > 1 ? args[1].toLowerCase() : "toggle";

            // Check if module exists
            if (!moduleManager.hasModule(moduleName)) {
                ActionBarHelper.sendActionBarMessage(player, "&cUnknown module: " + moduleName);
                return true;
            }

            // Check permissions - either overall permission or module-specific permission
            boolean hasOverallPermission = sender.hasPermission("articulate.use");
            boolean hasModulePermission = sender.hasPermission("articulate.module." + moduleName);

            if (!hasOverallPermission && !hasModulePermission) {
                ActionBarHelper.sendActionBarMessage(player, "&cYou do not have permission to use the " + moduleName + " module.");
                return true;
            }

            // Execute the module
            moduleManager.executeModule(player, moduleName, state);

            return true;
        }
        return false;
    }

    /**
     * Show general help message
     * @param player The player to show help to
     */
    private void showHelp(Player player) {
        player.sendMessage("§6=== Articulate Help ===");
        player.sendMessage("§eUsage: /articulate <module> [state]");
        player.sendMessage("§eAvailable modules:");

        boolean hasOverallPermission = player.hasPermission("articulate.use");

        for (String moduleName : moduleManager.getModuleNames()) {
            boolean hasModulePermission = player.hasPermission("articulate.module." + moduleName);
            if (hasOverallPermission || hasModulePermission) {
                var module = moduleManager.getModule(moduleName);
                if (module != null) {
                    player.sendMessage("§7  - §b" + moduleName + " §7(" + module.getDisplayName() + ")");
                }
            }
        }

        player.sendMessage("§eType §f/articulate help <module> §efor specific module help");
        player.sendMessage("§eAliases: §f/art, /artic, /a, /ar");
    }

    /**
     * Show help for a specific module
     * @param player The player to show help to
     * @param moduleName The module to show help for
     */
    private void showModuleHelp(Player player, String moduleName) {
        // Check permissions
        boolean hasOverallPermission = player.hasPermission("articulate.use");
        boolean hasModulePermission = player.hasPermission("articulate.module." + moduleName.toLowerCase());

        if (!hasOverallPermission && !hasModulePermission) {
            ActionBarHelper.sendActionBarMessage(player, "&cYou do not have permission to use the " + moduleName + " module.");
            return;
        }

        var module = moduleManager.getModule(moduleName.toLowerCase());
        if (module == null) {
            ActionBarHelper.sendActionBarMessage(player, "&cUnknown module: " + moduleName);
            return;
        }

        player.sendMessage("§6=== " + module.getDisplayName() + " Module Help ===");
        player.sendMessage("§eUsage: " + module.getUsage());
        player.sendMessage("§eAvailable states: §f" + String.join("§7, §f", module.getStates()));
    }
}