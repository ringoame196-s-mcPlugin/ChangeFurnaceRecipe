package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.commands.Command
import com.github.ringoame196_s_mcPlugin.commands.CommandConst
import com.github.ringoame196_s_mcPlugin.events.RecipeGUIEvent
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    private val recipeManager = RecipeManager()
    override fun onEnable() {
        super.onEnable()
        recipeManager.setRecipe()
        server.pluginManager.registerEvents(RecipeGUIEvent(), plugin)
        val command = getCommand(CommandConst.COMMAND_NAME)
        command!!.setExecutor(Command())
    }
}
