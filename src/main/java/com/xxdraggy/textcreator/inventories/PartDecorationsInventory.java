package com.xxdraggy.textcreator.inventories;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.data.inventory.InventoryClickType;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class PartDecorationsInventory {
    private TextCreatorPart part;
    private BiFunction<Player, TextCreatorPart, Object> callback;
    private Function<Player, Object> backCallback;

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Set the decorations")
            .setRows(5)
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player -> callback.apply(player, part))
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            );

    private void setup() {
        builder.removeItems();

        builder
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setItem(
                                Creator.item(Heads.Letters.Quartz.Letter.M.getHead())
                                        .setName("Magic/Obfuscated")
                                        .setLore(
                                                "Use: " + part.magic,
                                                "Use global: " + part.useGlobalMagic
                                        )
                                        .build()
                        )
                        .setSlot(20)
                        .addCallback(InventoryClickType.AnyLeftClick, player -> {
                            part.magic = !part.magic;

                            open(player);

                            return null;
                        })
                        .addCallback(InventoryClickType.RightClick, player -> {
                            part.useGlobalMagic = !part.useGlobalMagic;

                            open(player);

                            return null;
                        })
                );

        builder
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setItem(
                                Creator.item(Heads.Letters.Quartz.Letter.B.getHead())
                                        .setName("Bold")
                                        .setLore(
                                                "Use: " + part.bold,
                                                "Use global: " + part.useGlobalBold
                                        )
                                        .build()
                        )
                        .setSlot(21)
                        .addCallback(InventoryClickType.AnyLeftClick, player -> {
                            part.bold = !part.bold;

                            open(player);

                            return null;
                        })
                        .addCallback(InventoryClickType.RightClick, player -> {
                            part.useGlobalBold = !part.useGlobalBold;

                            open(player);

                            return null;
                        })
                );

        builder
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setItem(
                                Creator.item(Heads.Letters.Quartz.Letter.I.getHead())
                                        .setName("Italic")
                                        .setLore(
                                                "Use: " + part.italic,
                                                "Use global: " + part.useGlobalItalic
                                        )
                                        .build()
                        )
                        .setSlot(22)
                        .addCallback(InventoryClickType.AnyLeftClick, player -> {
                            part.italic = !part.italic;

                            open(player);

                            return null;
                        })
                        .addCallback(InventoryClickType.RightClick, player -> {
                            part.useGlobalItalic = !part.useGlobalItalic;

                            open(player);

                            return null;
                        })
                );

        builder
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setItem(
                                Creator.item(Heads.Letters.Quartz.Letter.S.getHead())
                                        .setName("Stroke")
                                        .setLore(
                                                "Use: " + part.stroke,
                                                "Use global: " + part.useGlobalStroke
                                        )
                                        .build()
                        )
                        .setSlot(23)
                        .addCallback(InventoryClickType.AnyLeftClick, player -> {
                            part.stroke = !part.stroke;

                            open(player);

                            return null;
                        })
                        .addCallback(InventoryClickType.RightClick, player -> {
                            part.useGlobalStroke = !part.useGlobalStroke;

                            open(player);

                            return null;
                        })
                );

        builder
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setItem(
                                Creator.item(Heads.Letters.Quartz.Letter.U.getHead())
                                        .setName("Underlined")
                                        .setLore(
                                                "Use: " + part.underlined,
                                                "Use global: " + part.useGlobalUnderlined
                                        )
                                        .build()
                        )
                        .setSlot(24)
                        .addCallback(InventoryClickType.AnyLeftClick, player -> {
                            part.underlined = !part.underlined;

                            open(player);

                            return null;
                        })
                        .addCallback(InventoryClickType.AnyRightClick, player -> {
                            part.useGlobalUnderlined = !part.useGlobalUnderlined;

                            player.sendMessage("Right click");

                            open(player);

                            return null;
                        })
                );
    }

    public PartDecorationsInventory setPart(TextCreatorPart part) {
        this.part = part;

        return this;
    }
    public PartDecorationsInventory setCallback(BiFunction<Player, TextCreatorPart, Object> callback) {
        this.callback = callback;

        return this;
    }
    public PartDecorationsInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        setup();

        player.openInventory(builder.build(player));

        return null;
    }
}