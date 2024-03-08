package com.github.Ringoame196

import com.github.Ringoame196.Entitys.EnderEye
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class SearchGUI {
    val guiName = "${ChatColor.DARK_GREEN}プレイヤー検索GUI"
    fun makeGUI(): Inventory {
        val gui = Bukkit.createInventory(null, 18, guiName)
        for (player in Bukkit.getOnlinePlayers()) {
            val headItem = makePlayerHead(player)
            gui.addItem(headItem)
        }
        return gui
    }
    private fun makePlayerHead(targetPlayer: Player): ItemStack {
        val item = ItemStack(Material.PLAYER_HEAD)
        val meta = item.itemMeta as SkullMeta
        meta.setOwningPlayer(targetPlayer)
        meta.setDisplayName(targetPlayer.name)
        item.setItemMeta(meta)
        return item
    }
    fun click(item: ItemStack, player: Player) {
        playClickSound(player)
        val itemType = Material.PLAYER_HEAD
        if (item.type != itemType) { return }
        player.closeInventory()
        val searchPlayerName = item.itemMeta?.displayName ?: ""
        val searchPlayer = getSearchPlayer(searchPlayerName, player) ?: return
        if (!checkWorldIdentical(player, searchPlayer)) {
            sendErrorMessage(player, "${ChatColor.RED}検索プレイヤーが別ワールドにいます")
            return
        }
        summonEnderEye(player, searchPlayer)
    }
    private fun getSearchPlayer(searchPlayerName: String, player: Player): Player? {
        val searchPlayer = Bukkit.getPlayer(searchPlayerName)
        if (searchPlayer == null) {
            sendErrorMessage(player, "${ChatColor.RED}プレイヤーが見つかりませんでした")
            return null
        }
        return searchPlayer
    }
    private fun summonEnderEye(player: Player, searchPlayer: Player) {
        val enderEye = EnderEye()
        val playerLocation = player.location
        val playerOverheadLocal = playerLocation.clone().add(0.0, 1.0, 0.0)
        val searchPlayerLocation = searchPlayer.location
        val searchPlayerOverheadLocation = searchPlayerLocation.clone().add(0.0, 1.0, 0.0)
        player.sendMessage("${ChatColor.AQUA}${searchPlayer.name}の方向へエンダーアイを飛ばしました")
        player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
        enderEye.summon(playerOverheadLocal, searchPlayerOverheadLocation)
    }
    private fun playClickSound(player: Player) {
        val sound = Sound.UI_BUTTON_CLICK
        player.playSound(player, sound, 1f, 1f)
    }
    private fun sendErrorMessage(player: Player, message: String) {
        val errorSound = Sound.BLOCK_NOTE_BLOCK_BELL
        player.sendMessage(message)
        player.playSound(player, errorSound, 1f, 1f)
    }
    private fun checkWorldIdentical(player: Player, searchPlayer: Player): Boolean {
        val playerWorld = player.world
        val searchPlayerWorld = searchPlayer.world
        return playerWorld == searchPlayerWorld
    }
}
