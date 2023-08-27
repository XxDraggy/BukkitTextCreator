package com.xxdraggy.textcreator;

import com.xxdraggy.datamanager.DataManager;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TextCreator extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("test").setExecutor((sender, command, label, args) -> {
            if(sender instanceof Player) {
                Creator.input()
                        .reopenOnFail()
                        .setCallback((player, lines) -> {
                            player.sendMessage(lines[0]);

                            return true;
                        })
                        .open((Player) sender);
            }

            return true;
        });

        Utils.register(this);
        DataManager.register(this);

        Bukkit.getLogger().log(Level.INFO, "[TextCreator] Successfully started up!");
    }

    public static void open(String title, Player player) {
        new TextCreatorInventory().setInventoryTitle(title).open(player);
    }

    public static void open(Player player) {
        TextCreator.open("Create your text", player);
    }

    public static Inventory inventory(Player player) {
        return new TextCreatorInventory().setInventoryTitle("Create your text").getInventory(player);
    }

    public static TextCreatorCommand command() {
        return new TextCreatorCommand();
    }
}
