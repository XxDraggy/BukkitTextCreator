package com.xxdraggy.textcreator.data;

import com.xxdraggy.utils.builders.text.TextBuilder;
import com.xxdraggy.utils.data.color.ColorObject;
import com.xxdraggy.utils.data.text.TextPart;
import org.bukkit.ChatColor;

public class TextCreatorPart extends TextPart {
    public TextCreatorPart() {
        for (int i = 0; i < 5; i++) {
            this.gradientColors[i] = TextBuilder.getColorObject(ChatColor.WHITE);
        }
    }

    public ColorObject[] gradientColors = new ColorObject[5];

    public boolean useGlobalColor = false;

    public int gradientColorsLength = 2;

    public boolean useGlobalMagic = false;
    public boolean useGlobalUnderlined = false;
    public boolean useGlobalStroke = false;
    public boolean useGlobalBold = false;
    public boolean useGlobalItalic = false;
}
