package com.xxdraggy.textcreator.inventories.color.colorchooser;

import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.builders.text.TextBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.data.inventory.border.buttons.ProceedButton;
import com.xxdraggy.utils.inventory.builders.border.buttons.ProceedButtonBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ColorChooserInventory {
    private BiFunction<Player, ColorObject, Object> callback = (player, colorObject) -> null;
    private Function<Player, Object> backCallback = (player) -> null;
    private boolean isNested = false;

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Choose your color")
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(ProceedButton::remove)
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            )
            .setRows(5)
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Letters.Quartz.Letter.P.getHead())
                                    .setName(Creator.text("Presets", "007FFF"))
                                    .setLore(
                                            Creator.text("Click to use a color from", "8B4DD6"),
                                            Creator.text("the minecraft presets!", "8B4DD6")
                                    )
                                    .build()
                    )
                    .setSlot(20)
                    .setCallback(player -> new PresetColorChooser()
                            .setCallback((player1, argbColor) -> callback.apply(player1, argbColor))
                            .setBackCallback(this::open)
                            .open(player)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Letters.Quartz.Letter.F.getHead())
                                    .setName(Creator.text("Favorites", "28D456"))
                                    .setLore(
                                            Creator.text("Click to use a color", "8B4DD6"),
                                            Creator.text("from your favorites!", "8B4DD6")
                                    )
                                    .build()
                    )
                    .setSlot(21)
                    .setCallback(player -> new FavoritesColorChooser()
                            .setCallback((player1, argbColor) -> callback.apply(player1, argbColor))
                            .setBackCallback(this::open)
                            .setNested(isNested)
                            .open(player)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Letters.Quartz.Letter.D.getHead())
                                    .setName(
                                            Creator.text(
                                                    "Default (White)",
                                                    ChatColor.WHITE
                                            )
                                    )
                                    .setLore(
                                            Creator.text("Click to use the", "8B4DD6"),
                                            Creator.text("default color!", "8B4DD6")
                                    )
                                    .build()
                    )
                    .setSlot(22)
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.WHITE)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Letters.Quartz.Letter.R.getHead())
                                    .setName(
                                            Creator.text(
                                                    "Rgb (122, 20, 20)",
                                                    122, 20, 20
                                            )
                                    )
                                    .setLore(
                                            Creator.text("Click to create", "8B4DD6"),
                                            Creator.text("rgb color!", "8B4DD6")
                                    )
                                    .build()
                    )
                    .setSlot(23)
                    .setCallback(player -> new RgbColorChooser()
                            .setCallback((player1, rgbColor) -> callback.apply(player1, rgbColor))
                            .setBackCallback(this::open)
                            .open(player)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(
                            Creator.item(Heads.Letters.Quartz.Letter.H.getHead())
                                    .setName(Creator.text("Hex (#A3700A)", "A3700A"))
                                    .setLore(
                                            Creator.text("Click to create a", "8B4DD6"),
                                            Creator.text("hexadecimal color!", "8B4DD6")
                                    )
                                    .build()
                    )
                    .setSlot(24)
                    .setCallback(player -> new HexColorChooser()
                            .setCallback((player1, hexColor) -> callback.apply(player1, hexColor))
                            .setBackCallback(this::open)
                            .open(player)
                    )
            );

    public ColorChooserInventory setNested(boolean isNested) {
        this.isNested = isNested;

        return this;
    }
    public ColorChooserInventory setCallback(BiFunction<Player, ColorObject, Object> callback) {
        this.callback = callback;

        return this;
    }
    public ColorChooserInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        player.openInventory(builder.build(player));

        return null;
    }
}
