package com.ddanddan.watch.util

import androidx.compose.runtime.Composable
import com.ddanddan.ddanddan.R
import com.ddanddan.watch.domain.model.MainPet
import com.ddanddan.watch.presentation.DDanDDanColorPalette

object PetUtils {

    fun getDrawableResourceId(mainPet: MainPet): Int {
        val petType = PetType.fromType(mainPet.type) ?: return R.drawable.ic_dog_purple_lev1 //todo - null 시 대체할 drawable은 논의 필요

        /**
         * 런타임 오류 가능성 및 성능 문제로 getIdentifier를 사용하지 않음
         */
        return when (petType) {
            PetType.DOG -> when (mainPet.level) {
                1 -> R.drawable.ic_dog_purple_lev1
                2 -> R.drawable.ic_dog_purple_lev2
                3 -> R.drawable.ic_dog_purple_lev3
                4 -> R.drawable.ic_dog_purple_lev4
                else -> R.drawable.ic_dog_purple_lev1 //todo - else에 해당되는 drawable은 논의 필요
            }
            PetType.PENGUIN -> when (mainPet.level) {
                1 -> R.drawable.ic_penguin_blue_lev1
                2 -> R.drawable.ic_penguin_blue_lev2
                3 -> R.drawable.ic_penguin_blue_lev3
                4 -> R.drawable.ic_penguin_blue_lev4
                else -> R.drawable.ic_penguin_blue_lev1
            }
            PetType.CAT -> when (mainPet.level) {
                1 -> R.drawable.ic_cat_pink_lev1
                2 -> R.drawable.ic_cat_pink_lev2
                3 -> R.drawable.ic_cat_pink_lev3
                4 -> R.drawable.ic_cat_pink_lev4
                else -> R.drawable.ic_cat_pink_lev1
            }
            PetType.HAMSTER -> when (mainPet.level) {
                1 -> R.drawable.ic_hamster_green_lev1
                2 -> R.drawable.ic_hamster_green_lev2
                3 -> R.drawable.ic_hamster_green_lev3
                4 -> R.drawable.ic_hamster_green_lev4
                else -> R.drawable.ic_hamster_green_lev1
            }
        }
    }

    @Composable
    fun getProgressBarColor(mainPet: MainPet): androidx.compose.ui.graphics.Color {
        val colorPalette = DDanDDanColorPalette.current
        val petType = PetType.fromType(mainPet.type) ?: return colorPalette.color_graphic_purple

        return when (petType) {
            PetType.DOG -> colorPalette.color_graphic_purple
            PetType.PENGUIN -> colorPalette.color_graphic_blue
            PetType.CAT -> colorPalette.color_graphic_pink
            PetType.HAMSTER -> colorPalette.color_graphic_green
        }
    }
}
