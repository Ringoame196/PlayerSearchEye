package com.github.Ringoame196

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Item {
    fun playerSearchEyeItem(): ItemStack {
        val item = ItemStack(Material.COMPASS)
        val meta = item.itemMeta
        meta?.setDisplayName("${ChatColor.AQUA}プレイヤー検索アイ")
        item.setItemMeta(meta)
        return item
    }
}
