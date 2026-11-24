package com.concursos.angelica.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> MultiSelectFilterChipGroup(
    title: String,
    items: List<T>,
    selectedItems: Set<T>,
    onToggleItem: (T) -> Unit,
    onClearSelection: () -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val titleText = if (selectedItems.isNotEmpty()) {
            "$title (${selectedItems.size} selecionado${if (selectedItems.size > 1) "s" else ""})"
        } else {
            title
        }

        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .semantics {
                    heading()
                    contentDescription = "$title. ${if (selectedItems.isNotEmpty()) "${selectedItems.size} opções selecionadas" else "Nenhum filtro aplicado, todas as opções ativas."}"
                }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Opcao "Todas"
            item {
                val isAllSelected = selectedItems.isEmpty()
                FilterChip(
                    selected = isAllSelected,
                    onClick = onClearSelection,
                    label = { Text("Todas") },
                    leadingIcon = if (isAllSelected) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null,
                    modifier = Modifier.semantics {
                        contentDescription = "Filtro $title: Opção Todas, ${if (isAllSelected) "selecionada. Toque para manter todas." else "não selecionada. Toque para limpar seleções e selecionar todas."}"
                    }
                )
            }

            // Itens individuais selecionáveis
            items(items) { item ->
                val isSelected = selectedItems.contains(item)
                val label = itemLabel(item)
                FilterChip(
                    selected = isSelected,
                    onClick = { onToggleItem(item) },
                    label = { Text(label) },
                    leadingIcon = if (isSelected) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null,
                    modifier = Modifier.semantics {
                        contentDescription = "Filtro $title: Opção $label, ${if (isSelected) "marcada. Toque para desmarcar." else "desmarcada. Toque para marcar."}"
                    }
                )
            }
        }
    }
}
