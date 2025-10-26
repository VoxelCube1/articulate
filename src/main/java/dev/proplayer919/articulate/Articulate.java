package dev.proplayer919.articulate;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import dev.proplayer919.articulate.commands.ArticulateCommand;
import dev.proplayer919.articulate.commands.ArticulateTabCompleter;
import dev.proplayer919.articulate.module.ModuleManager;
import dev.proplayer919.articulate.module.modules.FlightModule;
import dev.proplayer919.articulate.module.modules.FlightSpeedModule;
import dev.proplayer919.articulate.module.modules.WalkSpeedModule;
import dev.proplayer919.articulate.module.modules.GodModule;
import dev.proplayer919.articulate.module.modules.InstaKillModule;
import dev.proplayer919.articulate.module.modules.HealthModule;

public final class Articulate extends JavaPlugin implements Listener {
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        // Initialize module manager
        moduleManager = new ModuleManager();

        // Register modules
        moduleManager.registerModule(new FlightModule());
        moduleManager.registerModule(new FlightSpeedModule());
        moduleManager.registerModule(new WalkSpeedModule());
        moduleManager.registerModule(new HealthModule());

        // Register modules that need event listeners
        GodModule godModule = new GodModule();
        moduleManager.registerModule(godModule);
        getServer().getPluginManager().registerEvents(godModule, this);

        InstaKillModule instaKillModule = new InstaKillModule();
        moduleManager.registerModule(instaKillModule);
        getServer().getPluginManager().registerEvents(instaKillModule, this);

        // Register command and tab completer
        this.getCommand("articulate").setExecutor(new ArticulateCommand("articulate", moduleManager));
        this.getCommand("articulate").setTabCompleter(new ArticulateTabCompleter(moduleManager));

        // Register this plugin as event listener for cleanup
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Clean up player data when they disconnect
        GodModule.cleanup(event.getPlayer());
        InstaKillModule.cleanup(event.getPlayer());
    }
}
