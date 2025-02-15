package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.data.Data
import com.github.ringoame196_s_mcPlugin.data.RecipeData
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack

class RecipeManager {
    private val yamlFileManager = YamlFileManager("${Data.pluginDataFolderPath}/recipe.yml")

    fun loadRecipe() {
        val recipeData = yamlFileManager.acquisitionYml()

        // ファイルに保存されたレシピを登録
        for (input in recipeData.getKeys(false)) {
            val result = recipeData.getString(input) ?: continue
            val inputType = Material.getMaterial(input) ?: continue
            val resultType = Material.getMaterial(result) ?: continue
            saveRecipe(inputType, resultType)
        }
    }

    fun setRecipeMap() {
        val recipes = Bukkit.getServer().recipeIterator()
        while (recipes.hasNext()) {
            val recipe = recipes.next()
            if (recipe is FurnaceRecipe) {
                val recipeData = RecipeData(recipe.key, recipe.result.type)
                Data.furnaceRecipes[recipe.input.type] = recipeData
            }
        }
    }

    fun acquisitionRecipe(material: Material): Material {
        return Data.furnaceRecipes[material]?.resultType ?: Material.AIR
    }

    fun acquisitionKey(material: Material): NamespacedKey? {
        return Data.furnaceRecipes[material]?.key
    }

    fun saveRecipe(inputType: Material, resultType: Material) {
        val oldKey = acquisitionKey(inputType)

        // レシピに変更がない場合 そもそも実行しない
        if (acquisitionRecipe(inputType) == resultType) return

        // 古いレシピデータを削除する
        if (oldKey != null) Bukkit.removeRecipe(oldKey)

        val key = NamespacedKey(Data.PLUGIN_NAME.lowercase(), inputType.toString().lowercase()) // レシピのキー（ユニーク識別用）

        if (Bukkit.getRecipe(key) != null) Bukkit.removeRecipe(key) // 重複阻止のため 削除する
        val furnaceRecipe = FurnaceRecipe(key, ItemStack(resultType), inputType, 0.1f, 200)
        Bukkit.addRecipe(furnaceRecipe) // サーバーにレシピを登録
        yamlFileManager.setValue(inputType.toString(), resultType.toString()) // yamlファイルに書き込み
        val recipeData = RecipeData(key, resultType)
        Data.furnaceRecipes[inputType] = recipeData // データを更新
    }
}
