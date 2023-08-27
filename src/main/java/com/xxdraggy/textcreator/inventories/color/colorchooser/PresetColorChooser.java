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

public class PresetColorChooser {
    private BiFunction<Player, ColorObject, Object> callback = (player, colorObject) -> null;
    private Function<Player, Object> backCallback = player -> null;

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Select a color")
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(ProceedButton::remove)
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            )
            .setRows(4)
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(10)
                    .setItem(
                            Creator.item(Heads.Colors.Black.getHead())
                                    .setName(Creator.text("Black", ChatColor.BLACK))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.BLACK)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(11)
                    .setItem(
                            Creator.item(Heads.Colors.Blue.Dark.getHead())
                                    .setName(Creator.text("Blue", ChatColor.DARK_BLUE))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_BLUE)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(12)
                    .setItem(
                            Creator.item(Heads.Colors.Pink.Purple.getHead())
                                    .setName(Creator.text("Purple", ChatColor.BLUE))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.BLUE)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(13)
                    .setItem(
                            Creator.item(Heads.Colors.Green.Dark.getHead())
                                    .setName(Creator.text("Dark green", ChatColor.DARK_GREEN))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_GREEN)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(14)
                    .setItem(
                            Creator.item(Heads.Colors.Blue.DarkCyan.getHead())
                                    .setName(Creator.text("Dark aqua", ChatColor.DARK_AQUA))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_AQUA)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(15)
                    .setItem(
                            Creator.item(Heads.Colors.Blue.Cyan.getHead())
                                    .setName(Creator.text("Aqua", ChatColor.AQUA))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.AQUA)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(16)
                    .setItem(
                            Creator.item(Heads.Colors.Red.Dark.getHead())
                                    .setName(Creator.text("Dark red", ChatColor.DARK_RED))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_RED)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(19)
                    .setItem(
                            Creator.item(Heads.Colors.Red.Red.getHead())
                                    .setName(Creator.text("Red", ChatColor.RED))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.RED)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(20)
                    .setItem(
                            Creator.item(Heads.Colors.Orange.Light.getHead())
                                    .setName(Creator.text("Gold", ChatColor.GOLD))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.GOLD)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(21)
                    .setItem(
                            Creator.item(Heads.Colors.Gray.Gray.getHead())
                                    .setName(Creator.text("Dark gray", ChatColor.DARK_GRAY))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_GRAY)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(22)
                    .setItem(
                            Creator.item(Heads.Colors.Gray.Light.getHead())
                                    .setName(Creator.text("Gray", ChatColor.GRAY))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.GRAY)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(23)
                    .setItem(
                            Creator.item(Heads.Colors.Pink.Dark.getHead())
                                    .setName(Creator.text("Dark Pink", ChatColor.DARK_PURPLE))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.DARK_PURPLE)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(24)
                    .setItem(
                            Creator.item(Heads.Colors.Pink.Pink.getHead())
                                    .setName(Creator.text("Pink", ChatColor.LIGHT_PURPLE))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.LIGHT_PURPLE)))
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(25)
                    .setItem(
                            Creator.item(Heads.Colors.Yellow.Yellow.getHead())
                                    .setName(Creator.text("Yellow", ChatColor.YELLOW))
                                    .build()
                    )
                    .setCallback(player -> callback.apply(player, TextBuilder.getColorObject(ChatColor.YELLOW)))
            );

    public PresetColorChooser setCallback(BiFunction<Player, ColorObject, Object> callback) {
        this.callback = callback;

        return this;
    }
    public PresetColorChooser setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Void open(Player player) {
        player.openInventory(builder.build(player));

        return null;
    }
}

