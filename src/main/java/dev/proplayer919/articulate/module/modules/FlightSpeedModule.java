package dev.proplayer919.articulate.module.modules;

import dev.proplayer919.articulate.module.BaseModule;
import org.bukkit.entity.Player;

/**
 * Module for controlling flight speed
 */
public class FlightSpeedModule extends BaseModule {

    @Override
    public String getName() {
        return "fspeed";
    }

    @Override
    public String getDisplayName() {
        return "Flight Speed";
    }

    @Override
    public boolean execute(Player player, String state) {
        switch (state.toLowerCase()) {
            case "toggle":
                float currentSpeed = player.getFlySpeed() * 10.0f; // Convert back to user-friendly scale
                sendCurrentMessage(player, currentSpeed);
                break;
            case "default":
                player.setFlySpeed(0.1f);
                sendValueMessage(player, 1.0f);
                break;
            default:
                try {
                    float speed = Float.parseFloat(state);
                    if (speed < -1.0f || speed > 10.0f) {
                        sendErrorMessage(player, "Flight speed must be between -1.0 and 10.0.");
                        return false;
                    } else {
                        player.setFlySpeed(speed / 10.0f); // Bukkit fly speed is between 0.1 and 1.0
                        sendValueMessage(player, speed);
                    }
                } catch (NumberFormatException e) {
                    sendErrorMessage(player, "Invalid speed value.");
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public String getUsage() {
        return "/articulate fspeed [toggle|default|1.0-10.0]";
    }

    @Override
    public java.util.List<String> getStates() {
        return java.util.Arrays.asList("toggle", "default", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0", "10.0");
    }
}
