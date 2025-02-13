package com.github.ringoame196_s_mcPlugin.data

import org.bukkit.ChatColor
import org.bukkit.Material

object Data {
    val furnaceRecipes = mutableMapOf<Material, RecipeData>()

    val GUI_NAME = "${ChatColor.DARK_GRAY}かまどレシピ"

    const val PLUGIN_NAME = "ChangeFurnaceRecipe"
}
