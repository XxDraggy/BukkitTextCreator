package com.xxdraggy.textcreator.inventories.color.colorchooser;

import com.xxdraggy.datamanager.DataManager;
import com.xxdraggy.datamanager.YMLFile;
import com.xxdraggy.utils.Creator;
import com.xxdraggy.utils.Heads;
import com.xxdraggy.utils.builders.inventory.InventoryBuilder;
import com.xxdraggy.utils.builders.inventory.PagedInventoryBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.data.inventory.border.buttons.ProceedButton;
import com.xxdraggy.utils.inventory.builders.border.buttons.ProceedButtonBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FavoritesColorChooser {
    private BiFunction<Player, ColorObject, Object> callback = (player, colorObject) -> null;
    private Function<Player, Object> backCallback = player -> null;
    private boolean isNested = false;
    private final YMLFile file = DataManager.getYmlFile("prefabColors");

    private final InventoryBuilder base = Creator.inventory()
            .setTitle("Choose a color from your favorites")
            .setRows(5)
            .setBorder(borderBuilder -> borderBuilder
                    .setBackButton(backButtonBuilder -> backButtonBuilder
                            .setCallback(player -> backCallback.apply(player))
                    )
                    .setProceedButton(ProceedButton::remove)
            );

    private final PagedInventoryBuilder builder = Creator.pagedInventory()
            .setBaseInventory(base);

    private List<List<ColorObject>> getColors(Player player) {
        List<ColorObject> allColors = (List<ColorObject>) file.getList(player.getUniqueId().toString());
        List<List<ColorObject>> colors = new ArrayList<>();

        if(allColors == null) return null;

        int listIndex = 0;
        int index = 0;
        List<ColorObject> cache = new ArrayList<>();

        for (int i = 0; i < allColors.size(); i++) {
            cache.add(allColors.get(i));

            index++;

            if(index == 5) {
                colors.add(cache);

                cache = new ArrayList<>();
                index = 0;
                listIndex++;
            }
        }

        return colors;
    }
    private void setUp(Player player) {
        List<List<ColorObject>> colors = getColors(player);

        if(colors == null) {
            base.setItem(inventoryItemBuilder -> inventoryItemBuilder
                    .setSlot(22)
                    .setItem(
                            Creator.item(
                                    Heads.Blocks.Barrier.getHead()
                            ).setName(
                                    Creator.text("No favorites added", ChatColor.DARK_RED)
                            ).build())
            );

            return;
        }

        if(colors.size() == 1) {
            List<ColorObject> list = colors.get(0);

            for (int i = 0; i < list.size(); i++) {
                ColorObject color = list.get(i);
                int index = i;

                ItemStack item = Creator.item(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                meta.setColor(color.getBukkitObject());
                item.setItemMeta(meta);

                base.setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setCallback(player1 -> callback.apply(player, color))
                        .setItem(
                                Creator.item(item)
                                        .setName(color.getColor() + "Color")
                                        .build()
                        )
                        .setSlot(19 + index)
                );
            }

            if(!isNested)
                base.setItem(inventoryItemBuilder -> inventoryItemBuilder
                        .setSlot(8)
                        .setItem(Creator.item(Material.APPLE, "Add favorite"))
                        .setCallback(player1 -> {
                            new ColorChooserInventory()
                                    .setCallback((player2, object) -> {
                                        this.file.addListItem(player.getUniqueId().toString(), object);

                                        open(player);

                                        return null;
                                    })
                                    .setNested(true)
                                    .open(player);

                            return null;
                        })
                );
        }
        else
            for (int i = 0; i < colors.size(); i++) {
                for (int j = 0; i < colors.get(i).size(); i++) {
                    ColorObject color = colors.get(i).get(j);
                    int index = j;

                    ItemStack item = Creator.item(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                    meta.setColor(color.getBukkitObject());
                    item.setItemMeta(meta);

                    builder.addPageItems(i, itemBuilder -> itemBuilder
                            .setCallback(player1 -> callback.apply(player, color))
                            .setItem(
                                    Creator.item(item)
                                            .setName(color.toString())
                                            .build()
                            )
                            .setSlot(15 - index)
                    );
                }

                if(!isNested)
                    builder.addPageItems(i, itemBuilder -> itemBuilder
                            .setSlot(8)
                            .setItem(Creator.item(Material.APPLE, "Add prefab"))
                            .setCallback(player1 -> {
                                new ColorChooserInventory()
                                        .setCallback((player2, number) -> {
                                            this.file.set(player.getUniqueId().toString(), number);

                                            new FavoritesColorChooser().setCallback(callback).setNested(isNested).open(player);

                                            return null;
                                        })
                                        .setNested(true)
                                        .open(player);

                                return null;
                            })
                    );
            }
    }

    public FavoritesColorChooser setNested(boolean isNested) {
        this.isNested = isNested;

        return this;
    }
    public FavoritesColorChooser setCallback(BiFunction<Player, ColorObject, Object> callback) {
        this.callback = callback;

        return this;
    }
    public FavoritesColorChooser setBackCallback(Function<Player, Object> backCallback) {
        this.backCallback = backCallback;

        return this;
    }

    public Object open(Player player) {
        setUp(player);

        if(getColors(player) == null) player.openInventory(base.build(player));
        else if(getColors(player).size() == 1) player.openInventory(base.build(player));
        else player.openInventory(builder.build(player));

        return null;
    }
}
