package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class RecipeGUIEvent(private val plugin: Plugin) : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked as? Player? ?: return
        val gui = e.view
        val guiTitle = gui.title
        val clickInventory = e.clickedInventory ?: return
        val slot = e.slot

        if (guiTitle != Data.GUI_NAME) return
        if (clickInventory == player.inventory) return // プレイヤーのインベントリでキャンセル発生を阻止

        when (slot) {
            RecipeGUIManager.RESULT_ITEM_SLOT, RecipeGUIManager.IN_ITEM_SLOT -> {} // アイテムが置き外しできるように
            RecipeGUIManager.SETTING_ITEM_SLOT -> {
                e.isCancelled = true
                saveResultItem(gui, player)
            }
            RecipeGUIManager.BAKE_ITEM_SLOT -> {
                e.isCancelled = true
                setResultItem(gui, player)
            }
            else -> e.isCancelled = true
        }
    }

    private fun setResultItem(inventory: InventoryView, player: Player) {
        val item = inventory.getItem(RecipeGUIManager.IN_ITEM_SLOT)
        val material = item?.type ?: Material.AIR
        val resultItemType = RecipeManager.acquisitionRecipe(material)
        inventory.setItem(RecipeGUIManager.RESULT_ITEM_SLOT, ItemStack(resultItemType))

        val sound = Sound.UI_BUTTON_CLICK
        player.playSound(player, sound, 1f, 1f)
    }

    private fun saveResultItem(inventory: InventoryView, player: Player) {
        val inputItem = inventory.getItem(RecipeGUIManager.IN_ITEM_SLOT) ?: return
        val resultItem = inventory.getItem(RecipeGUIManager.RESULT_ITEM_SLOT) ?: ItemStack(Material.AIR)

        RecipeManager.saveRecipe(inputItem, resultItem)

        val sound = Sound.BLOCK_ANVIL_USE
        player.playSound(player, sound, 1f, 1f)
        val message = "${ChatColor.GOLD}${resultItem.type}のレシピを変更しました"
        player.sendMessage(message)
    }
}
