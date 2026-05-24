package ir.armin.mindnest.utils

import ir.armin.mindnest.R
import ir.armin.mindnest.data.model.BackgroundOption

object BackgroundResources {

    val defaultId = R.color.white

    val allOptions = listOf(

        BackgroundOption(R.drawable.background1),
        BackgroundOption(R.drawable.background2),
        BackgroundOption(R.drawable.background3),
        BackgroundOption(R.drawable.background4),
        BackgroundOption(R.drawable.background5),
        BackgroundOption(R.drawable.background6),
        BackgroundOption(R.drawable.background8),
        BackgroundOption(R.drawable.background7),
    )


    val optionsWithDefault = listOf(
        BackgroundOption(defaultId)
    ) + allOptions
}