package com.concursos.angelica.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.concursos.angelica.data.dao.DisciplinaStat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformanceScreen(
    totalRespondidas: Int,
    totalAcertos: Int,
    tempoMedioSegundos: Double,
    statsPorDisciplina: List<DisciplinaStat>,
    onNavigateToAngelica: () -> Unit,
    modifier: Modifier = Modifier
) {
    val taxaGeral = if (totalRespondidas > 0) (totalAcertos.toDouble() / totalRespondidas) * 100 else 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meu Desempenho",
                        modifier = Modifier.semantics { heading() }
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToAngelica,
                icon = { Icon(Icons.Default.Face, contentDescription = null) },
                text = { Text("Consultar Angélica") },
                modifier = Modifier.semantics {
                    contentDescription = "Abrir mentoria pedagógica com a inteligência artificial Angélica"
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cartão Resumo Geral
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics(mergeDescendants = true) {
                            contentDescription = "Resumo geral: $totalRespondidas questões respondidas, " +
                                    "$totalAcertos acertos, aproveitamento de ${"%.1f".format(taxaGeral)}%, " +
                                    "tempo médio de ${"%.0f".format(tempoMedioSegundos)} segundos por questão."
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Aproveitamento Geral",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${"%.1f".format(taxaGeral)}%",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "Respondidas", style = MaterialTheme.typography.labelMedium)
                                Text(text = "$totalRespondidas", style = MaterialTheme.typography.titleLarge)
                            }
                            Column {
                                Text(text = "Acertos", style = MaterialTheme.typography.labelMedium)
                                Text(text = "$totalAcertos", style = MaterialTheme.typography.titleLarge)
                            }
                            Column {
                                Text(text = "Tempo Médio", style = MaterialTheme.typography.labelMedium)
                                Text(text = "${"%.0f".format(tempoMedioSegundos)}s", style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }

            // Título Seção Disciplinas
            item {
                Text(
                    text = "Desempenho por Disciplina",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() }
                )
            }

            if (statsPorDisciplina.isEmpty()) {
                item {
                    Text(
                        text = "Você ainda não resolveu questões suficientes para gerar estatísticas por matéria.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(statsPorDisciplina) { stat ->
                    val taxa = if (stat.total > 0) (stat.acertos.toDouble() / stat.total) * 100 else 0.0
                    val barraCor = when {
                        taxa >= 80 -> Color(0xFF2E7D32)
                        taxa >= 60 -> Color(0xFFF57C00)
                        else -> Color(0xFFC62828)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics(mergeDescendants = true) {
                                contentDescription = "Disciplina ${stat.disciplina}: ${stat.acertos} acertos em ${stat.total} questões. Taxa de ${"%.1f".format(taxa)}%."
                            },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = stat.disciplina, style = MaterialTheme.typography.titleMedium)
                                Text(text = "${"%.1f".format(taxa)}%", style = MaterialTheme.typography.titleMedium, color = barraCor)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${stat.acertos} acertos de ${stat.total} questões",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Barra de progresso visual
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth((taxa / 100).toFloat().coerceIn(0f, 1f))
                                        .fillMaxHeight()
                                        .background(barraCor)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
