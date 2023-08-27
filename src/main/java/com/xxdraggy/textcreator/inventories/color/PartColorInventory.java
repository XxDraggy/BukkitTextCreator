package com.xxdraggy.textcreator.inventories.color;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.textcreator.inventories.color.colorchooser.ColorChooserInventory;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.data.color.ColorType;
import com.xxdraggy.utils.data.inventory.InventoryItem;
import com.xxdraggy.utils.inventory.builders.InventoryItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class PartColorInventory {
    private TextCreatorPart part = new TextCreatorPart();
    private BiFunction<Player, TextCreatorPart, Object> callback = (player, textCreatorPart) -> null;
    private Function<Player, Object> backCallback = player -> null;

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Set the color")
            .setRows(5)
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player -> callback.apply(player, part))
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player ->  backCallback.apply(player))
                    )
            );
    private final InventoryItem gradientItem = new InventoryItem()
            .setSlot(23)
            .setItem(
                    Creator.item(Heads.Letters.Quartz.Colon.getHead())
                            .setName(Creator.text("Create colors", ChatColor.GOLD))
                            .build()
            )
            .setCallback(player -> new GradientColorSelectorInventory()
                    .setBackCallback(this::open)
                    .setPart(s -> part)
                    .setCallback((player1, textCreatorPart) -> {
                        this.part = textCreatorPart;

                        this.open(player);

                        return null;
                    })
                    .open(player)
            );
    private final InventoryItem rainbowItem = new InventoryItem()
            .setSlot(23)
            .setItem(
                    Creator.item(Heads.Blocks.Barrier.getHead())
                        .setName(Creator.text("Can not edit color!", ChatColor.DARK_RED))
                        .build()
            );
    private final InventoryItem oneColorItem = new InventoryItem()
            .setSlot(23)
            .setItem(
                    Creator.item(Heads.Letters.Quartz.Colon.getHead())
                            .setName(Creator.text("Create color", ChatColor.GOLD))
                            .build()
            )
            .setCallback(player -> new ColorChooserInventory()
                    .setBackCallback(this::open)
                    .setCallback((player1, colorObject) -> {
                        this.part.color = colorObject;

                        this.open(player);

                        return null;
                    })
                    .open(player)
            );

    private void setup() {
        builder.removeItems();

        builder.setItem(inventoryItemBuilder -> inventoryItemBuilder
                .setSlot(21)
                .setItem(
                        Creator.item(Heads.Letters.Quartz.Dot.getHead())
                                .setName("Change color mode")
                                .setLore(
                                        "Current: " + part.colorType.name()
                                )
                                .build()
                )
                .setCallback(player1 -> {
                    switch (part.colorType) {
                        case OneColor:
                            part.colorType = ColorType.Gradient;
                            break;
                        case Gradient:
                            part.colorType = ColorType.Rainbow;
                            break;
                        case Rainbow:
                            part.colorType = ColorType.OneColor;
                            break;
                    }

                    this.open(player1);

                    return null;
                })
        );

        switch (part.colorType) {
            case OneColor:
                builder.setItem(oneColorItem);
                return;
            case Gradient:
                builder.setItem(gradientItem);
                return;
            case Rainbow:
                builder.setItem(rainbowItem);
        }
    }

    public PartColorInventory setPart(TextCreatorPart part) {
        this.part = part;

        return this;
    }
    public PartColorInventory setCallback(BiFunction<Player, TextCreatorPart, Object> callback) {
        this.callback = callback;

        return this;
    }
    public PartColorInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        setup();

        builder.open(player);

        return null;
    }
}
