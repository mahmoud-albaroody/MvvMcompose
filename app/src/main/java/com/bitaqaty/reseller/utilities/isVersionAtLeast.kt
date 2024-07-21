package com.bitaqaty.reseller.utilities

fun isVersionAtLeast(requiredVersion: String, currentVersion: String): Boolean {
    try {
        val requiredParts = requiredVersion.split('.').map { it.toInt() }
        val currentParts = currentVersion.split('.').map { it.toInt() }

        for (i in requiredParts.indices) {
            if ((currentParts.getOrNull(i) ?: 0) < requiredParts[i]) {
                return false
            }
        }
    }catch (e:Exception){
        return true
    }

    return true
}