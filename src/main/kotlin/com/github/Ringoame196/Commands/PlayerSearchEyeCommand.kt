package com.github.Ringoame196.Commands

import com.github.Ringoame196.Item
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class PlayerSearchEyeCommand : CommandExecutor, TabExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.size != 1) { return false }
        val subCommand = args[0]
        if (sender !is Player) {
            return true
        }
        makeTab()[subCommand]?.invoke(sender)
        return true
    }
    private fun makeTab(): Map<String, (player: Player)->Unit> {
        return mapOf(
            "give" to { player: Player ->
                val item = Item().playerSearchEyeItem()
                player.inventory.addItem(item)
            }
        )
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> makeTab().keys.toMutableList()
            else -> mutableListOf()
        }
    }
}
