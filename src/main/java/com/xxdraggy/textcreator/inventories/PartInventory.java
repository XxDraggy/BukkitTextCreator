package com.xxdraggy.textcreator.inventories;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.textcreator.inventories.color.PartColorInventory;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.builders.text.TextBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.data.text.TextPart;
import com.xxdraggy.utils.data.color.ColorType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PartInventory {
    private BiFunction<Player, TextCreatorPart, Object> callback = (player, textPart) -> null;
    private Function<Player, Object> backCallback = (player) -> null;
    private TextCreatorPart part = new TextCreatorPart();
    private TextCreatorPart global = new TextCreatorPart();

    private InventoryBuilder builder = Creator.inventory()
            .setTitle("Style the part")
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player -> callback.apply(player, part))
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            )
            .setRows(5);

    private void setup() {
        builder.removeItems();

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

        builder.setItem(
                inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(22)
                        .setItem(
                            Creator.item(Heads.Blocks.PurpurBlock.getHead())
                                .setName("Text preview")
                                .setLore(getText())
                                .build()
                            )
        );

        builder.setItem(
                inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(29)
                        .setItem(
                            Creator.item(Heads.Blocks.PurpurBlock.getHead())
                                .setName("Edit decorations")
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
        );

        builder.setItem(
                inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(31)
                        .setItem(
                            Creator.item(Heads.Blocks.PurpurBlock.getHead())
                                .setName("Edit text")
                                .setLore("Currently: " + part.text)
                                .build()
                        )
                        .setCallback(player -> Creator.input()
                                .setLines(
                                        "Enter your text",
                                        "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼",
                                        "",
                                        "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲"
                                )
                                .setCallback((player1, lines) -> {
                                    this.part.text = lines[2];

                                    open(player);

                                    return true;
                                })
                                .open(player))
        );

        builder.setItem(
                inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(31)
                        .setItem(
                            Creator.item(Heads.Blocks.PurpurBlock.getHead())
                                .setName("Edit color")
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

    public PartInventory setCallback(BiFunction<Player, TextCreatorPart, Object> callback) {
        this.callback = callback;

        return this;
    }
    public PartInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }
    public PartInventory setPart(TextCreatorPart part) {
        this.part = part;

        return this;
    }
    public PartInventory setGlobal(TextCreatorPart global) {
        this.global = global;

        return this;
    }

    public String getText() {
        TextPart newPart = part;

        // Color
        if(part.useGlobalColor) {
            newPart.colorType = global.colorType;
            newPart.color = global.color;
            newPart.gradientColors = new ColorObject[global.gradientColorsLength];

            for (int i = 0; i < global.gradientColorsLength; i++) {
                newPart.gradientColors[i] = global.gradientColors[i];
            }
        }
        else {
            newPart.gradientColors = new ColorObject[part.gradientColorsLength];

            for (int i = 0; i < part.gradientColorsLength; i++) {
                newPart.gradientColors[i] = part.gradientColors[i];
            }
        }

        // Bold
        if(part.useGlobalBold)
            newPart.bold = global.bold;

        // Italic
        if(part.useGlobalItalic)
            newPart.italic = global.italic;

        // Magic
        if(part.useGlobalMagic)
            newPart.magic = global.magic;

        // Stroke
        if(part.useGlobalStroke)
            newPart.stroke = global.stroke;

        // Underlined
        if(part.useGlobalUnderlined)
            newPart.underlined = global.underlined;

        return TextBuilder.toString(newPart);
    }

    public Object open(Player player) {
        setup();

        Creator.input()
            .setLines(
                "Enter your text",
                "▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼",
                "",
                "▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲"
            )
            .setCallback((player1, lines) -> {
                this.part.text = lines[2];

                open(player);

                return true;
            })
            .open(player);

        return null;
    }
}
