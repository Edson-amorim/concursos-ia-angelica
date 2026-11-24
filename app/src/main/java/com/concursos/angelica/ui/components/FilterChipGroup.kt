package com.concursos.angelica.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterChipGroup(
    title: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T?) -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .semantics { heading() }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedItem == null,
                    onClick = { onItemSelected(null) },
                    label = { Text("Todas") },
                    modifier = Modifier.semantics {
                        contentDescription = "Filtro $title: Opção Todas, ${if (selectedItem == null) "selecionado" else "não selecionado"}"
                    }
                )
            }

            items(items) { item ->
                val isSelected = item == selectedItem
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) onItemSelected(null)
                        else onItemSelected(item)
                    },
                    label = { Text(itemLabel(item)) },
                    modifier = Modifier.semantics {
                        contentDescription = "Filtro $title: ${itemLabel(item)}, ${if (isSelected) "selecionado" else "não selecionado"}"
                    }
                )
            }
        }
    }
}
