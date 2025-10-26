package dev.proplayer919.articulate.module;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Manager class for handling all modules
 */
public class ModuleManager {
    private final Map<String, Module> modules = new HashMap<>();

    /**
     * Register a module
     * @param module The module to register
     */
    public void registerModule(Module module) {
        modules.put(module.getName().toLowerCase(), module);
    }

    /**
     * Get a module by name
     * @param name The module name
     * @return The module or null if not found
     */
    public Module getModule(String name) {
        return modules.get(name.toLowerCase());
    }

    /**
     * Execute a module command
     * @param player The player executing the command
     * @param moduleName The name of the module
     * @param state The state parameter
     * @return true if the module was found and executed, false otherwise
     */
    public boolean executeModule(Player player, String moduleName, String state) {
        Module module = getModule(moduleName);
        if (module == null) {
            return false;
        }
        return module.execute(player, state);
    }

    /**
     * Get all registered module names
     * @return Set of module names
     */
    public Set<String> getModuleNames() {
        return modules.keySet();
    }

    /**
     * Check if a module exists
     * @param name The module name
     * @return true if the module exists
     */
    public boolean hasModule(String name) {
        return modules.containsKey(name.toLowerCase());
    }
}
