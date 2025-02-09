package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.Data
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object RecipeGUIManager {
    val sideItem = makeSideItem()
    val clickItem = makeClickItem()
    val inItemSlot = 2
    val resultItemSlot = 5
    val clickItemSlot = 8

    fun makeGUI(): Inventory {
        val guiSize = 9
        val gui = Bukkit.createInventory(null, guiSize, Data.GUI_NAME)

        for (i in 0..gui.size - 1) {
            gui.setItem(i, sideItem)
        }
        gui.setItem(inItemSlot, ItemStack(Material.AIR))
        gui.setItem(resultItemSlot, ItemStack(Material.AIR))
        gui.setItem(clickItemSlot, clickItem)

        return gui
    }

    private fun makeSideItem(): ItemStack {
        val item = ItemStack(Material.WHITE_STAINED_GLASS_PANE)
        val itemName = " "
        val meta = item.itemMeta
        meta?.setDisplayName(itemName)
        item.itemMeta = meta
        return item
    }

    private fun makeClickItem(): ItemStack {
        val item = ItemStack(Material.FURNACE)
        val itemName = "${ChatColor.GREEN}更新"
        val meta = item.itemMeta
        meta?.setDisplayName(itemName)
        item.itemMeta = meta
        return item
    }
}
