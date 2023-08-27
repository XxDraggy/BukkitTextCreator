package com.xxdraggy.textcreator.inventories.color.colorchooser;

public class ArgbColorChooser {
    /*
    private static Function<Player, Object> backCallback = player -> null;
    private static BiFunction<Player, ColorObject, Object> callback = (player, colorObject) -> null;

    private static InventoryBuilder builder = Creator.inventory()
            .setTitle("Create your ARGB color")
            .setBorder(borderBuilder -> borderBuilder
                    .setProceedButton(proceedButtonBuilder -> proceedButtonBuilder
                            .setCallBack(player ->
                                    ArgbColorChooser.callback.apply(
                                            player,
                                            TextBuilder.argbColor(
                                                    new ArgbColor(
                                                            Integer.parseInt(ArgbColorChooser.alpha),
                                                            Integer.parseInt(ArgbColorChooser.red),
                                                            Integer.parseInt(ArgbColorChooser.green),
                                                            Integer.parseInt(ArgbColorChooser.blue)
                                                    )
                                            )
                                    )
                            )
                    )
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallBack(backCallback)
                    )
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(Heads.Colors.Black.getHead())
                    .setSlot(13)
                    .setCallback(player -> {
                        player.openInventory(ArgbColorChooser.alphaBuilder.build(player));

                        return null;
                    })
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(Heads.Colors.Red.Dark.getHead())
                    .setSlot(20)
                    .setCallback(player -> {
                        player.openInventory(ArgbColorChooser.redBuilder.build(player));

                        return null;
                    })
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(Heads.Colors.Green.Dark.getHead())
                    .setSlot(22)
                    .setCallback(player -> {
                        player.openInventory(ArgbColorChooser.greenBuilder.build(player));

                        return null;
                    })
            )
            .setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setItem(Heads.Colors.Blue.Dark.getHead())
                    .setSlot(24)
                    .setCallback(player -> {
                        player.openInventory(ArgbColorChooser.blueBuilder.build(player));

                        return null;
                    })
            )
            .setRows(5);

    private static String alpha = "255";
    private static InventoryBuilder alphaBuilder = new NumberSelector()
            .setNumberCount(3)
            .setProceedCallback((player, number) -> {
                alpha = number;

                player.openInventory(builder.build(player));

                return null;
            })
            .setBackCallback(player -> {
                player.openInventory(builder.build(player));

                return null;
            })
            .setMaximum("255")
            .setDefaultNumber("255")
            .setTitle(Creator.text("Select your alpha aspect", ChatColor.AQUA))
            .create();

    private static String red = "255";
    private static InventoryBuilder redBuilder = new NumberSelector()
            .setNumberCount(3)
            .setProceedCallback((player, number) -> {
                red = number;

                player.openInventory(builder.build(player));

                return null;
            })
            .setBackCallback(player -> {
                player.openInventory(builder.build(player));

                return null;
            })
            .setMaximum("255")
            .setDefaultNumber("255")
            .setTitle(Creator.text("Select your red aspect", ChatColor.DARK_RED))
            .create();

    private static String green = "255";
    private static InventoryBuilder greenBuilder = new NumberSelector()
            .setNumberCount(3)
            .setProceedCallback((player, number) -> {
                green = number;

                player.openInventory(builder.build(player));

                return null;
            })
            .setBackCallback(player -> {
                player.openInventory(builder.build(player));

                return null;
            })
            .setMaximum("255")
            .setDefaultNumber("255")
            .setTitle(Creator.text("Select your green aspect", ChatColor.DARK_GREEN))
            .create();

    private static String blue = "255";
    private static InventoryBuilder blueBuilder = new NumberSelector()
            .setNumberCount(3)
            .setProceedCallback((player, number) -> {
                blue = number;

                player.openInventory(builder.build(player));

                return null;
            })
            .setBackCallback(player -> {
                player.openInventory(builder.build(player));

                return null;
            })
            .setMaximum("255")
            .setDefaultNumber("255")
            .setTitle(Creator.text("Select your blue aspect", ChatColor.DARK_BLUE))
            .create();

    public ArgbColorChooser setCallback(BiFunction<Player, ColorObject, Object> callback) {
        ArgbColorChooser.callback = callback;

        return this;
    }

    public ArgbColorChooser setBackCallback(Function<Player, Object> backCallback) {
        ArgbColorChooser.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        player.openInventory(builder.build(player));

        return null;
    }
     */
}
