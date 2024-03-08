package com.github.Ringoame196.Entitys

import org.bukkit.Location
import org.bukkit.entity.EnderSignal

class EnderEye {
    fun summon(location: Location, targetLocation: Location) {
        val world = location.world
        val enderEye: EnderSignal = world!!.spawn(location, org.bukkit.entity.EnderSignal::class.java)
        // エンダーアイの飛んでいく先変更
        enderEye.targetLocation = targetLocation
        // エンダーアイをドロップしないように
        enderEye.dropItem = false
    }
}
