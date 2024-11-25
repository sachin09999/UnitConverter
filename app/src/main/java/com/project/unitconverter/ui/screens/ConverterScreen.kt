package com.project.unitconverter.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.unitconverter.model.ConversionCategory
import com.project.unitconverter.model.UnitMeasurement
import com.project.unitconverter.model.UnitConverters
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    category: ConversionCategory,
    onBackClick: () -> Unit
) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }

    val units = when (category) {
        is ConversionCategory.Temperature -> UnitConverters.temperatureUnits
        is ConversionCategory.Length -> UnitConverters.lengthUnits
        is ConversionCategory.Weight -> UnitConverters.weightUnits
        is ConversionCategory.Speed -> UnitConverters.speedUnits
        is ConversionCategory.Volume -> UnitConverters.volumeUnits
        is ConversionCategory.Area -> UnitConverters.areaUnits
        is ConversionCategory.Time -> UnitConverters.timeUnits
        is ConversionCategory.DigitalStorage -> UnitConverters.digitalStorageUnits
        is ConversionCategory.Energy -> UnitConverters.energyUnits
        is ConversionCategory.Pressure -> UnitConverters.pressureUnits
        is ConversionCategory.Data -> UnitConverters.dataTransferUnits
        is ConversionCategory.Currency -> UnitConverters.currencyUnits
    }

    var fromUnit by remember { mutableStateOf(units.first()) }
    var toUnit by remember { mutableStateOf(units[1]) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Convert between different ${category.name.lowercase()} units",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Input Section
            UnitSection(
                value = inputValue,
                onValueChange = {
                    inputValue = it
                    outputValue = convertValue(it, fromUnit, toUnit, category)
                },
                selectedUnit = fromUnit,
                onUnitChange = {
                    fromUnit = it
                    outputValue = convertValue(inputValue, fromUnit, toUnit, category)
                },
                units = units,
                label = "From",
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                )
            )

            // Swap Button with rotation animation
            var rotationState by remember { mutableStateOf(0f) }
            val rotation by animateFloatAsState(
                targetValue = rotationState,
                animationSpec = tween(durationMillis = 300)
            )

            FloatingActionButton(
                onClick = {
                    rotationState += 180f
                    val temp = fromUnit
                    fromUnit = toUnit
                    toUnit = temp
                    outputValue = convertValue(inputValue, fromUnit, toUnit, category)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.rotate(rotation)
            ) {
                Icon(
                    imageVector = Icons.Rounded.SwapVert,
                    contentDescription = "Swap units",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Output Section
            UnitSection(
                value = outputValue,
                onValueChange = { /* Read only */ },
                selectedUnit = toUnit,
                onUnitChange = {
                    toUnit = it
                    outputValue = convertValue(inputValue, fromUnit, toUnit, category)
                },
                units = units,
                label = "To",
                readOnly = true,
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnitSection(
    value: String,
    onValueChange: (String) -> Unit,
    selectedUnit: UnitMeasurement,
    onUnitChange: (UnitMeasurement) -> Unit,
    units: List<UnitMeasurement>,
    label: String,
    readOnly: Boolean = false,
    gradient: Brush
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPressed) 2.dp else 4.dp,
            pressedElevation = 2.dp,
            focusedElevation = 4.dp,
            hoveredElevation = 6.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    readOnly = readOnly,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )

                var expanded by remember { mutableStateOf(false) }
                var searchQuery by remember { mutableStateOf("") }

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = "${selectedUnit.name} (${selectedUnit.symbol})",
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    expanded = !expanded
                                    if (!expanded) searchQuery = ""
                                    isPressed = true
                                    kotlinx.coroutines.GlobalScope.launch {
                                        kotlinx.coroutines.delay(100)
                                        isPressed = false
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (expanded)
                                        Icons.Rounded.KeyboardArrowUp
                                    else
                                        Icons.Rounded.KeyboardArrowDown,
                                    contentDescription = if (expanded) "Close" else "Open"
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                            searchQuery = ""
                        },
                        modifier = Modifier
                            .heightIn(max = 350.dp)
                            .width(IntrinsicSize.Max)
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search units") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        val filteredUnits = remember(searchQuery) {
                            if (searchQuery.isEmpty()) {
                                units
                            } else {
                                units.filter { unit ->
                                    unit.name.contains(searchQuery, ignoreCase = true) ||
                                            unit.symbol.contains(searchQuery, ignoreCase = true)
                                }
                            }
                        }

                        filteredUnits.forEach { unit ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "${unit.name} (${unit.symbol})",
                                        color = if (unit == selectedUnit)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurface,
                                        fontWeight = if (unit == selectedUnit)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    onUnitChange(unit)
                                    expanded = false
                                    searchQuery = ""
                                },
                                leadingIcon = if (unit == selectedUnit) {
                                    {
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                } else null,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun convertValue(
    input: String,
    fromUnit: UnitMeasurement,
    toUnit: UnitMeasurement,
    category: ConversionCategory
): String {
    return try {
        val inputValue = input.toDouble()
        val result = UnitConverters.convert(inputValue, fromUnit, toUnit, category)
        String.format("%.4f", result)
    } catch (e: NumberFormatException) {
        ""
    }
}