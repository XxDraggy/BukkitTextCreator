package com.xxdraggy.textcreator.inventories.color;

import com.xxdraggy.textcreator.data.TextCreatorPart;
import com.xxdraggy.textcreator.inventories.color.colorchooser.ColorChooserInventory;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class GradientColorSelectorInventory {
    private TextCreatorPart part = new TextCreatorPart();
    private Function<Player, Object> backCallback = player -> null;
    private BiFunction<Player, TextCreatorPart, Object> callback = (player, textCreatorPart) -> null;

    private final InventoryBuilder builder = Creator.inventory()
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
                .setTitle("Select your colors")
                .setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(8)
                        .setItem(Heads.Letters.Quartz.Dot.getHead())
                        .setCallback(player -> {
                            part.gradientColorsLength++;

                            if(part.gradientColorsLength == 6) part.gradientColorsLength = 2;

                            open(player);

                            return null;
                        })
                );

        String[] numbers = new String[] {
                "First",
                "Second",
                "Third",
                "Fourth",
                "Fifth"
        };
        int[] slots = new int[5];
        switch (part.gradientColorsLength) {
            case 2:
                slots[0] = 21;
                slots[1] = 23;
                break;
            case 3:
                slots[0] = 21;
                slots[1] = 22;
                slots[2] = 23;
                break;
            case 4:
                slots[0] = 20;
                slots[1] = 21;
                slots[2] = 23;
                slots[3] = 24;
                break;
            case 5:
                slots[0] = 20;
                slots[1] = 21;
                slots[2] = 22;
                slots[3] = 23;
                slots[4] = 24;
                break;
        }

        for (int i = 0; i < part.gradientColorsLength; i++) {
            final int index = i;
            int slot = slots[index];
            String number = numbers[index];

            builder.setItem(
                    inventoryItemBuilder -> inventoryItemBuilder
                            .setSlot(slot)
                            .setItem(
                                    Creator.item(Heads.Colors.Red.Dark.getHead())
                                            .setName(number + " color")
                                            .setLore(
                                                    "Click to edit the " + number .toLowerCase() + " color",
                                                    part.gradientColors[index].getColor() + "Current: #" + part.gradientColors[index].getHex()
                                            )
                                            .build()
                            )
                            .setCallback(player -> new ColorChooserInventory()
                                    .setCallback((player1, colorObject) -> {
                                        part.gradientColors[index] = colorObject;

                                        this.open(player1);

                                        return null;
                                    })
                                    .setNested(false)
                                    .setBackCallback(this::open)
                                    .open(player)
                            )
            );
        }
    }

    public GradientColorSelectorInventory setPart(Function<String, TextCreatorPart> part) {
        this.part = part.apply("");

        return this;
    }
    public GradientColorSelectorInventory setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }
    public GradientColorSelectorInventory setCallback(BiFunction<Player, TextCreatorPart, Object> callback) {
        this.callback = callback;

        return this;
    }

    public Object open(Player player) {
        setup();

        this.builder.open(player);

        return null;
    }
}
