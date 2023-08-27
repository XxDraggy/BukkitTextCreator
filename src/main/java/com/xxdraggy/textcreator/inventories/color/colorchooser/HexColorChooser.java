package com.xxdraggy.textcreator.inventories.color.colorchooser;

import com.xxdraggy.utils.builders.text.TextBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.inventories.Inventories;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;
import java.util.function.Function;

public class HexColorChooser {
    private BiFunction<Player, ColorObject, Object> callback;
    private Function<Player, Object> backCallback = player -> null;

    public HexColorChooser setCallback(BiFunction<Player, ColorObject, Object> callback) {
        this.callback = callback;

        return this;
    }

    public HexColorChooser setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        Inventories.hexNumberSelector()
                .setNumberCount(6)
                .setTitle("Create your HEX color")
                .setCallback((player1, number) -> callback.apply(
                                player,
                                TextBuilder.hexColor(number)
                        )
                )
                .setBackCallback(player1 -> backCallback.apply(player))
                .create()
                .open(player);

        return null;
    }
}
