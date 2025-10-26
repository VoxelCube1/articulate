package dev.proplayer919.articulate.helpers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ActionBarHelper {
    public static void sendActionBarMessage(Player player, String message) {
        String actionBarMessage = ChatColor.translateAlternateColorCodes('&', message);

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(actionBarMessage));
    }
}
