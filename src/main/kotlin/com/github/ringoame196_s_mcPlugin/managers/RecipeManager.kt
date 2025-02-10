package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack

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

    fun saveRecipe(inputItem: ItemStack, resultItem: ItemStack) {
        val key = NamespacedKey(Data.PLUGIN_NAME.lowercase(), "${inputItem.type.toString().lowercase()}_to_${resultItem.type.toString().lowercase()}") // レシピのキー（ユニーク識別用）

        if (Bukkit.getRecipe(key) != null) Bukkit.removeRecipe(key) // 重複阻止のため 削除する
        val furnaceRecipe = FurnaceRecipe(key, resultItem, inputItem.type, 0.1f, 200)
        Bukkit.addRecipe(furnaceRecipe) // サーバーにレシピを登録
        Data.furnaceRecipes[inputItem.type] = resultItem.type // データを更新
    }
}
