package com.xxdraggy.textcreator.inventories;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.textcreator.inventories.color.PartColorInventory;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.data.color.ColorType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GlobalDataInventory {
    private TextCreatorPart part = new TextCreatorPart();
    private BiFunction<Player, TextCreatorPart, Object> callback = (player, textCreatorPart) -> null;
    private Function<Player, Object> backCallback = player -> null;

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Manage the global style")
            .setRows(5)
            .setBorder(borderBuilder -> borderBuilder
                    .setBackButton(backButton -> backButton
                            .setCallback(player -> backCallback.apply(player))
                    )
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player -> callback.apply(player, part))
                    )
            );

    private void setup() {
        List<String> colorLore = new ArrayList<>();
        colorLore.add("Color type: " + part.colorType.name());
        if(part.colorType == ColorType.Gradient)
            colorLore.add("Colors: " + Arrays.toString(part.gradientColors));
        else if(part.colorType == ColorType.OneColor)
            colorLore.add("Color: " + part.color);

        List<String> decorationsLore = new ArrayList<>();
        decorationsLore.add("Magic/Obfuscated (" + Creator.text("a").magic().toString() + "): " + part.magic);
        decorationsLore.add(Creator.text("Italic").italic().toString() + ": " + part.italic);
        decorationsLore.add(Creator.text("Underlined").underline().toString() + ": " + part.underlined);
        decorationsLore.add(Creator.text("Bold").bold().toString() + ": " + part.bold);
        decorationsLore.add(Creator.text("Stroke").stroke().toString() + ": " + part.stroke);

        builder
        .setItem(inventoryItemBuilder -> inventoryItemBuilder
            .setSlot(3)
            .setItem(
                Creator.item(Heads.Blocks.PurpurBlock.getHead())
                    .setName("Edit global decorations")
                    .setLore(decorationsLore)
                    .build()
            )
            .setCallback(player -> new PartDecorationsInventory()
                .setPart(part)
                .setCallback((player1, textPart) -> {
                    part = textPart;

                    open(player1);

                    return null;
                })
                .setBackCallback(this::open)
                .open(player)
            )
        )
        .setItem(inventoryItemBuilder -> inventoryItemBuilder
            .setSlot(31)
            .setItem(
                Creator.item(Heads.Blocks.PurpurBlock.getHead())
                    .setName("Edit global color")
                    .setLore(colorLore)
                    .build()
            )
            .setCallback(player -> new PartColorInventory()
                .setPart(part)
                .setCallback((player1, textPart) -> {
                    part = textPart;

                    open(player1);

                    return null;
                })
                .setBackCallback(this::open)
                .open(player)
            )
        );
    }

    public GlobalDataInventory setPart(TextCreatorPart part) {
        this.part = part;

        return this;
    }
    public GlobalDataInventory setCallback(BiFunction<Player, TextCreatorPart, Object> callback) {
        this.callback = callback;

        return this;
    }
    public GlobalDataInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        setup();

        player.openInventory(builder.build(player));

        return null;
    }
}
