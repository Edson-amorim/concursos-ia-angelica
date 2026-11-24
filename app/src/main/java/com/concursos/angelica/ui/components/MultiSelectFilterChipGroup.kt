package com.concursos.angelica.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    emptyMessage: String? = null,
    onApplyCategory: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Cabeçalho da Caixa de Filtro
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.semantics { heading() }
                    )
                }

                if (selectedItems.isNotEmpty()) {
                    TextButton(
                        onClick = onClearSelection,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.semantics {
                            contentDescription = "Limpar seleção de $title"
                        }
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Limpar (${selectedItems.size})",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                } else {
                    AssistChip(
                        onClick = {},
                        enabled = false,
                        label = { Text("Todas ativas", style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Conteúdo: Lista de Chips ou Mensagem quando vazia
            if (items.isEmpty() && emptyMessage != null) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = emptyMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Opção "Todas"
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
                                contentDescription = "Filtro $title: Opção Todas, ${if (isAllSelected) "selecionada" else "não selecionada. Toque para selecionar todas"}"
                            }
                        )
                    }

                    // Itens individuais
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
                                contentDescription = "Filtro $title: $label, ${if (isSelected) "marcado" else "desmarcado"}"
                            }
                        )
                    }
                }
            }

            // Botão Aplicar contextual em cada caixa de filtro
            if (onApplyCategory != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalButton(
                        onClick = onApplyCategory,
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.semantics {
                            contentDescription = "Aplicar seleção de $title"
                        }
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Aplicar $title",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
