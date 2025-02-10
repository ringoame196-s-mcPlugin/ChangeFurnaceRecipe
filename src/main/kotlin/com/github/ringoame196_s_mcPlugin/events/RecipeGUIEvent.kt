package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.Data
import com.github.ringoame196_s_mcPlugin.managers.RecipeGUIManager
import com.github.ringoame196_s_mcPlugin.managers.RecipeManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

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
            RecipeGUIManager.IN_ITEM_SLOT -> {
                object : BukkitRunnable() { // 設置後のインベントリを取得するため 1tick遅延
                    override fun run() {
                        setResultItem(gui)
                    }
                }.runTaskLater(plugin, 1L) // 1 tick 遅延実行
            }
            RecipeGUIManager.RESULT_ITEM_SLOT -> {} // アイテムが置き外しできるように
            RecipeGUIManager.CLICK_ITEM_SLOT -> {
                e.isCancelled = true
                saveResultItem(gui)
            }
            else -> e.isCancelled = true
        }
    }

    private fun setResultItem(inventory: InventoryView) {
        val item = inventory.getItem(RecipeGUIManager.IN_ITEM_SLOT)
        val material = item?.type ?: Material.AIR
        val resultItemType = RecipeManager.acquisitionRecipe(material)
        inventory.setItem(RecipeGUIManager.RESULT_ITEM_SLOT, ItemStack(resultItemType))
    }

    private fun saveResultItem(inventory: InventoryView) {
        val inputItem = inventory.getItem(RecipeGUIManager.IN_ITEM_SLOT) ?: return
        val resultItem = inventory.getItem(RecipeGUIManager.RESULT_ITEM_SLOT) ?: ItemStack(Material.AIR)

        RecipeManager.saveRecipe(inputItem, resultItem)
    }
}
