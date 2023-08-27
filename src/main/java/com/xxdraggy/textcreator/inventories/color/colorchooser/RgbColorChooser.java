package com.xxdraggy.textcreator.inventories.color.colorchooser;

import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.text.TextBuilder;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.data.color.RgbColor;
import com.xxdraggy.utils.inventories.Inventories;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class RgbColorChooser {
    private static Function<Player, Object> backCallback = player -> null;
    private static BiFunction<Player, ColorObject, Object> callback = (player, colorObject) -> null;

    private static final InventoryBuilder builder = Creator.inventory()
            .setTitle("Create your RGB color")
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player ->
                                    RgbColorChooser.callback.apply(
                                            player,
                                            TextBuilder.rgbColor(
                                                    new RgbColor(
                                                            Integer.parseInt(RgbColorChooser.red),
                                                            Integer.parseInt(RgbColorChooser.green),
                                                            Integer.parseInt(RgbColorChooser.blue)
                                                    )
                                            )
                                    )
                            )
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Colors.Red.Dark.getHead())
                                    .setName(Creator.text("Red", ChatColor.DARK_RED))
                                    .build()
                    )
                    .setSlot(20)
                    .setCallback(player -> Inventories.numberSelector()
                            .setNumberCount(3)
                            .setCallback((player1, number) -> {
                                red = number;

                                RgbColorChooser.sOpen(player1);

                                return null;
                            })
                            .setBackCallback(player1 -> RgbColorChooser.sOpen(player1))
                            .setMaximum("255")
                            .setDefaultNumber(s -> RgbColorChooser.red)
                            .setTitle("Select your red aspect")
                            .create()
                            .open(player)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Colors.Green.Dark.getHead())
                                    .setName(Creator.text("Green", ChatColor.DARK_GREEN))
                                    .build()
                    )
                    .setSlot(22)
                    .setCallback(player -> Inventories.numberSelector()
                            .setNumberCount(3)
                            .setCallback((player1, number) -> {
                                green = number;

                                RgbColorChooser.sOpen(player1);

                                return null;
                            })
                            .setBackCallback(player1 -> RgbColorChooser.sOpen(player1))
                            .setMaximum("255")
                            .setDefaultNumber(s -> RgbColorChooser.green)
                            .setTitle("Select your green aspect")
                            .create()
                            .open(player)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Colors.Blue.Dark.getHead())
                                    .setName(Creator.text("Blue", ChatColor.DARK_BLUE))
                                    .build()
                    )
                    .setSlot(24)
                    .setCallback(player -> Inventories.numberSelector()
                            .setNumberCount(3)
                            .setCallback((player1, number) -> {
                                blue = number;

                                sOpen(player);

                                return null;
                            })
                            .setBackCallback(player1 -> RgbColorChooser.sOpen(player1))
                            .setMaximum("255")
                            .setDefaultNumber(s -> RgbColorChooser.blue)
                            .setTitle("Select your blue aspect")
                            .create()
                            .open(player)
                    )
            )
            .setRows(5);

    private static String red = "255";
    private static String green = "255";
    private static String blue = "255";

    public RgbColorChooser setCallback(BiFunction<Player, ColorObject, Object> callback) {
        RgbColorChooser.callback = callback;

        return this;
    }
    public RgbColorChooser setBackCallback(Function<Player, Object> backCallback) {
        RgbColorChooser.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        return sOpen(player);
    }

    public static Object sOpen(Player player) {
        player.openInventory(builder.build(player));

        return null;
    }
}
