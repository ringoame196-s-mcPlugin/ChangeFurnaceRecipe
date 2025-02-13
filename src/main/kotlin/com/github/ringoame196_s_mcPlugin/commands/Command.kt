package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class Command : CommandExecutor, TabCompleter {
    private val recipeGUIManager = RecipeGUIManager()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            val message = "${ChatColor.RED}このコマンドは、プレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return true
        }
        val gui = recipeGUIManager.makeGUI()
        sender.openInventory(gui)

        return true
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return mutableListOf()
    }
}
