package com.lypaka.betterareas.GUIs;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.ItemStackBuilder;
import net.minecraft.item.ItemStack;

public class CommonButtons {

    public static ItemStack getBorderStack (String id) {

        ItemStack stack = ItemStackBuilder.buildFromStringID(id);
        stack.setDisplayName(FancyText.getFormattedText(""));
        return stack;

    }

}
