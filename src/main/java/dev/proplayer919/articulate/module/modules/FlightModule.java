package dev.proplayer919.articulate.module.modules;

import dev.proplayer919.articulate.module.BaseModule;
import org.bukkit.entity.Player;

/**
 * Module for toggling flight on and off
 */
public class FlightModule extends BaseModule {

    @Override
    public String getName() {
        return "flight";
    }

    @Override
    public String getDisplayName() {
        return "Flight";
    }

    @Override
    public boolean execute(Player player, String state) {
        switch (state.toLowerCase()) {
            case "toggle":
                if (player.isFlying()) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    sendToggleMessage(player, false);
                } else {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    sendToggleMessage(player, true);
                }
                break;
            case "on":
                player.setAllowFlight(true);
                player.setFlying(true);
                sendToggleMessage(player, true);
                break;
            case "off":
                player.setAllowFlight(false);
                player.setFlying(false);
                sendToggleMessage(player, false);
                break;
            default:
                sendErrorMessage(player, "Invalid state for Flight module. Use: toggle, on, off.");
                return false;
        }
        return true;
    }

    @Override
    public String getUsage() {
        return "/articulate flight [toggle|on|off]";
    }
}
