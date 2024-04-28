package com.paraskcd.spendingtracker.utils

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

fun imageVectorFromString(iconName: String): ImageVector {
    val cl = Class.forName("androidx.compose.material.icons.filled.${iconName}Kt")
    val method = cl.declaredMethods.first()
    return method.invoke(null, Icons.Filled) as ImageVector
}

fun stringFromImageVector(imageVector: ImageVector): String {
    return imageVector.name.split(".")[1]
}