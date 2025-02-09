package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.FurnaceRecipe

object RecipeManager {
    fun setRecipe() {
        val recipes = Bukkit.getServer().recipeIterator()
        while (recipes.hasNext()) {
            val recipe = recipes.next()
            if (recipe is FurnaceRecipe) {
                Data.furnaceRecipes[recipe.input.type] = recipe.result.type
            }
        }
    }

    fun acquisitionRecipe(material: Material): Material {
        return Data.furnaceRecipes[material] ?: Material.AIR
    }
}
