package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.commands.CommandConst
import com.github.ringoame196_s_mcPlugin.data.Data
import com.github.ringoame196_s_mcPlugin.events.RecipeGUIEvent
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    override fun onEnable() {
        super.onEnable()
        if (!plugin.dataFolder.exists()) plugin.dataFolder.mkdirs()
        saveResource("recipe.yml", false)
        Data.pluginDataFolderPath = plugin.dataFolder.path

        val recipeManager = RecipeManager()
        recipeManager.setRecipeMap()
        recipeManager.loadRecipe()
        server.pluginManager.registerEvents(RecipeGUIEvent(), plugin)
        val command = getCommand(CommandConst.COMMAND_NAME)
        command!!.setExecutor(Command())
    }
}
