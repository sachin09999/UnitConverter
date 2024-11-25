package com.project.unitconverter.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ConversionCategory(
    val name: String,
    val icon: ImageVector,
    val route: String
) {
    object Temperature : ConversionCategory(
        name = "Temperature",
        icon = Icons.Outlined.DeviceThermostat,
        route = "temperature"
    )

    object Length : ConversionCategory(
        name = "Length",
        icon = Icons.Outlined.Straighten,
        route = "length"
    )

    object Weight : ConversionCategory(
        name = "Weight",
        icon = Icons.Outlined.FitnessCenter,
        route = "weight"
    )

    object Speed : ConversionCategory(
        name = "Speed",
        icon = Icons.Outlined.Speed,
        route = "speed"
    )

    object Volume : ConversionCategory(
        name = "Volume",
        icon = Icons.Outlined.WaterDrop,
        route = "volume"
    )

    object Area : ConversionCategory(
        name = "Area",
        icon = Icons.Outlined.AspectRatio,
        route = "area"
    )

    object Time : ConversionCategory(
        name = "Time",
        icon = Icons.Outlined.Timer,
        route = "time"
    )

    object DigitalStorage : ConversionCategory(
        name = "Digital Storage",
        icon = Icons.Outlined.Storage,
        route = "digital_storage"
    )

    object Energy : ConversionCategory(
        name = "Energy",
        icon = Icons.Outlined.Bolt,
        route = "energy"
    )

    object Pressure : ConversionCategory(
        name = "Pressure",
        icon = Icons.Outlined.Compress,
        route = "pressure"
    )

    object Data : ConversionCategory(
        name = "Data Transfer",
        icon = Icons.Outlined.DataUsage,
        route = "data"
    )

    object Currency : ConversionCategory(
        name = "Currency",
        icon = Icons.Outlined.AttachMoney,
        route = "currency"
    )

    companion object {
        val categories = listOf(
            Temperature,
            Length,
            Weight,
            Speed,
            Volume,
            Area,
            Time,
            DigitalStorage,
            Energy,
            Pressure,
            Data,
            Currency
        )
    }
}
