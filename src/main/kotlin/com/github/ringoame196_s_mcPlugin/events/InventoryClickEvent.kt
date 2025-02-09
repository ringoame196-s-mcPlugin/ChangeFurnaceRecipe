package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickEvent : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked as? Player? ?: return
        val gui = e.view
        val guiTitle = gui.title
        val clickInventory = e.clickedInventory
        val slot = e.slot

        if (guiTitle != Data.GUI_NAME) return
        if (clickInventory == player.inventory) return // プレイヤーのインベントリでキャンセル発生を阻止

        when (slot) {
            RecipeGUIManager.inItemSlot, RecipeGUIManager.resultItemSlot -> {} // アイテムが置いたりできるように
            RecipeGUIManager.clickItemSlot -> {
                e.isCancelled = true
            }
            else -> e.isCancelled = true
        }
    }
}
