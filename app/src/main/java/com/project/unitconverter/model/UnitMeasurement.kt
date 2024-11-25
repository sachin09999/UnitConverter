package com.project.unitconverter.model

data class UnitMeasurement(
    val name: String,
    val symbol: String,
    val conversionFactor: Double
)

object UnitConverters {
    val temperatureUnits = listOf(
        UnitMeasurement("Celsius", "°C", 1.0),
        UnitMeasurement("Fahrenheit", "°F", 1.0),
        UnitMeasurement("Kelvin", "K", 1.0)
    )

    val lengthUnits = listOf(
        UnitMeasurement("Meter", "m", 1.0),
        UnitMeasurement("Kilometer", "km", 1000.0),
        UnitMeasurement("Centimeter", "cm", 0.01),
        UnitMeasurement("Millimeter", "mm", 0.001),
        UnitMeasurement("Mile", "mi", 1609.34),
        UnitMeasurement("Yard", "yd", 0.9144),
        UnitMeasurement("Foot", "ft", 0.3048),
        UnitMeasurement("Inch", "in", 0.0254)
    )

    val weightUnits = listOf(
        UnitMeasurement("Kilogram", "kg", 1.0),
        UnitMeasurement("Gram", "g", 0.001),
        UnitMeasurement("Milligram", "mg", 0.000001),
        UnitMeasurement("Pound", "lb", 0.453592),
        UnitMeasurement("Ounce", "oz", 0.0283495)
    )

    val speedUnits = listOf(
        UnitMeasurement("Meters per second", "m/s", 1.0),
        UnitMeasurement("Kilometers per hour", "km/h", 0.277778),
        UnitMeasurement("Miles per hour", "mph", 0.44704),
        UnitMeasurement("Knots", "kn", 0.514444)
    )

    val volumeUnits = listOf(
        UnitMeasurement("Liter", "L", 1.0),
        UnitMeasurement("Milliliter", "mL", 0.001),
        UnitMeasurement("Cubic meter", "m³", 1000.0),
        UnitMeasurement("Gallon (US)", "gal", 3.78541),
        UnitMeasurement("Fluid ounce (US)", "fl oz", 0.0295735)
    )

    val areaUnits = listOf(
        UnitMeasurement("Square meter", "m²", 1.0),
        UnitMeasurement("Square kilometer", "km²", 1000000.0),
        UnitMeasurement("Square mile", "mi²", 2589988.11),
        UnitMeasurement("Square yard", "yd²", 0.836127),
        UnitMeasurement("Square foot", "ft²", 0.092903),
        UnitMeasurement("Acre", "ac", 4046.86),
        UnitMeasurement("Hectare", "ha", 10000.0)
    )

    // New unit types
    val timeUnits = listOf(
        UnitMeasurement("Second", "s", 1.0),
        UnitMeasurement("Minute", "min", 60.0),
        UnitMeasurement("Hour", "h", 3600.0),
        UnitMeasurement("Day", "d", 86400.0),
        UnitMeasurement("Week", "wk", 604800.0),
        UnitMeasurement("Month (avg)", "mo", 2629746.0),
        UnitMeasurement("Year", "yr", 31556952.0)
    )

    val digitalStorageUnits = listOf(
        UnitMeasurement("Byte", "B", 1.0),
        UnitMeasurement("Kilobyte", "KB", 1024.0),
        UnitMeasurement("Megabyte", "MB", 1048576.0),
        UnitMeasurement("Gigabyte", "GB", 1073741824.0),
        UnitMeasurement("Terabyte", "TB", 1099511627776.0),
        UnitMeasurement("Petabyte", "PB", 1125899906842624.0)
    )

    val energyUnits = listOf(
        UnitMeasurement("Joule", "J", 1.0),
        UnitMeasurement("Kilojoule", "kJ", 1000.0),
        UnitMeasurement("Calorie", "cal", 4.184),
        UnitMeasurement("Kilocalorie", "kcal", 4184.0),
        UnitMeasurement("Watt-hour", "Wh", 3600.0),
        UnitMeasurement("Kilowatt-hour", "kWh", 3600000.0)
    )

    val pressureUnits = listOf(
        UnitMeasurement("Pascal", "Pa", 1.0),
        UnitMeasurement("Kilopascal", "kPa", 1000.0),
        UnitMeasurement("Bar", "bar", 100000.0),
        UnitMeasurement("Atmosphere", "atm", 101325.0),
        UnitMeasurement("PSI", "psi", 6894.76),
        UnitMeasurement("Millimeter of Mercury", "mmHg", 133.322)
    )

    val dataTransferUnits = listOf(
        UnitMeasurement("Bit per second", "bps", 1.0),
        UnitMeasurement("Kilobit per second", "Kbps", 1000.0),
        UnitMeasurement("Megabit per second", "Mbps", 1000000.0),
        UnitMeasurement("Gigabit per second", "Gbps", 1000000000.0),
        UnitMeasurement("Byte per second", "B/s", 8.0),
        UnitMeasurement("Kilobyte per second", "KB/s", 8000.0),
        UnitMeasurement("Megabyte per second", "MB/s", 8000000.0)
    )

    // Currency units will be updated dynamically with real exchange rates
    val currencyUnits = listOf(
        UnitMeasurement("US Dollar", "USD", 1.0),
        UnitMeasurement("Euro", "EUR", 0.85),
        UnitMeasurement("British Pound", "GBP", 0.73),
        UnitMeasurement("Japanese Yen", "JPY", 110.0),
        UnitMeasurement("Indian Rupee", "INR", 74.5),
        UnitMeasurement("Chinese Yuan", "CNY", 6.45)
    )

    fun convert(value: Double, from: UnitMeasurement, to: UnitMeasurement, category: ConversionCategory): Double {
        return when (category) {
            is ConversionCategory.Temperature -> convertTemperature(value, from.name, to.name)
            is ConversionCategory.DigitalStorage -> value * from.conversionFactor / to.conversionFactor
            else -> value * from.conversionFactor / to.conversionFactor
        }
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5/9
            "Kelvin" -> value - 273.15
            else -> value
        }

        return when (to) {
            "Celsius" -> celsius
            "Fahrenheit" -> celsius * 9/5 + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }
}