package com.ddanddan.watch.util

enum class PetType(val type: String) {
    DOG("DOG"),
    PENGUIN("PENGUIN"),
    CAT("CAT"),
    HAMSTER("HAMSTER");

    companion object {
        fun fromType(type: String): PetType? {
            return PetType.values().find { it.type.equals(type, ignoreCase = true) } // 이름이 일치하는 enum을 반환
        }
    }
}