package com.ddanddan.watch.util

enum class PetType {
    DOG, PENGUIN, CAT, HAMSTER;

    companion object {
        fun fromType(type: String): PetType? {
            return entries.find { it.name.equals(type, ignoreCase = true) } // 이름이 일치하는 Enum을 반환
        }
    }
}
