package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YamlFileManager(private val filePath: String) {
    private val file = File(filePath)

    fun acquisitionYml(): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }

    fun setValue(key: String, value: Any?) {
        val ymlFile = acquisitionYml()
        ymlFile.set(key, value)
        ymlFile.save(file)
    }
}
