package com.example.mastermindgame.utils

class StringUtils {

    fun convertToStringArray(str: String) : Array<String>{
        val randomNumberArray: Array<String> = str.toCharArray().map { it.toString() }.toTypedArray()
        return randomNumberArray
    }
}