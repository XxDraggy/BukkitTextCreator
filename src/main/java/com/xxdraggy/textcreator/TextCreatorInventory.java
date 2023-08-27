package com.xxdraggy.textcreator;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.textcreator.inventories.GlobalDataInventory;
import com.xxdraggy.textcreator.inventories.PartInventory;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.builders.inventory.PagedInventoryBuilder;
import com.xxdraggy.utils.data.color.ColorType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TextCreatorInventory {
    private List<TextCreatorPart> parts = new ArrayList<>();
    private TextCreatorPart global = new TextCreatorPart();
    private BiFunction<Player, String, Object> callback;
    private Function<Player, Object> backCallback;

    private InventoryBuilder base = Creator.inventory()
            .setRows(5)
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallback(player -> callback.apply(player, getString()))
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
            );
    private PagedInventoryBuilder builder = Creator.pagedInventory();

    private String getString() {
        return "";
    }
    private void setUp() {
        String color = "";
        if(global.colorType == ColorType.Gradient)
            color = "Colors: " + Arrays.toString(global.gradientColors);
        else if(global.colorType == ColorType.OneColor)
            color = "Color: " + global.color;
        final String colorLore = color;
        base.setItem(inventoryItemBuilder -> inventoryItemBuilder
                .setSlot(8)
                .setItem(
                        Creator.item(Heads.Letters.Black.Blank.getHead())
                                .setName(Creator.text("Edit global style", ChatColor.GOLD))
                                .setLore(
                                        Creator.text("Color").bold().underline().toString(),
                                        "Color type: " + global.colorType.name(),
                                        colorLore,
                                        "",
                                        Creator.text("Decoration").bold().underline().toString(),
                                        "Magic/Obfuscated (" + Creator.text("a").magic().toString() + "): " + global.magic,
                                        Creator.text("Italic").italic().toString() + ": " + global.italic,
                                        Creator.text("Underlined").underline().toString() + ": " + global.underlined,
                                        Creator.text("Bold").bold().toString() + ": " + global.bold,
                                        Creator.text("Stroke").stroke().toString() + ": " + global.stroke
                                )
                                .build()
                )
                .setCallback(player -> new GlobalDataInventory()
                        .setPart(global)
                        .setCallback((player1, textCreatorPart) -> global = textCreatorPart)
                        .setBackCallback(this::open)
                )
        );

        if(parts.size() >= 5) {
            for (int i = 0; i < parts.size(); i++) {
                int index = i;

                base.setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(19 + index)
                        .setItem(Heads.Letters.Quartz.Dot.getHead())
                        .setCallback(player -> new PartInventory()
                                .setPart(parts.get(index))
                                .setBackCallback(this::open)
                                .setCallback((player1, textCreatorPart) -> {
                                    parts.set(index, textCreatorPart);

                                    open(player1);

                                    return null;
                                })
                        )
                );
            }
        }
        else {
            for (int i = 0; i < parts.size(); i++) {
                int index = i;

                builder.addPageItems(i, inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(19 + index)
                        .setItem(Heads.Letters.Quartz.Dot.getHead())
                        .setCallback(player -> new PartInventory()
                                .setPart(parts.get(index))
                                .setBackCallback(this::open)
                                .setCallback((player1, textCreatorPart) -> {
                                    parts.set(index, textCreatorPart);

                                    open(player1);

                                    return null;
                                })
                        )
                );
            }
        }
    }

    public TextCreatorInventory setInventoryTitle(String title) {
        base.setTitle(title);

        return this;
    }
    public TextCreatorInventory setCallback(BiFunction<Player, String, Object> callback) {
        this.callback = callback;

        return this;
    }
    public TextCreatorInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        setUp();

        if(parts.size() >= 5)
            player.openInventory(base.build(player));
        else
            player.openInventory(builder.build(player));

        return null;
    }

    public Inventory getInventory(Player player) {
        return builder.build(player);
    }
}
