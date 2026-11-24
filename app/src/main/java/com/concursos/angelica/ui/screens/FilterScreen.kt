package com.concursos.angelica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.concursos.angelica.data.model.FilterCriteria
import com.concursos.angelica.data.repository.FilterOptions
import com.concursos.angelica.ui.components.MultiSelectFilterChipGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    filterOptions: FilterOptions,
    currentFilters: FilterCriteria,
    matchingQuestionsCount: Int = 0,
    isGeneratingQuestions: Boolean = false,
    updateMessage: String? = null,
    onApplyFilters: (FilterCriteria) -> Unit,
    onGenerateWithAi: ((banca: String, disciplina: String, assunto: String) -> Unit)? = null,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBancas by remember(currentFilters) { mutableStateOf(currentFilters.bancas) }
    var selectedModalidades by remember(currentFilters) { mutableStateOf(currentFilters.modalidades) }
    var selectedDisciplinas by remember(currentFilters) { mutableStateOf(currentFilters.disciplinas) }
    var selectedAssuntos by remember(currentFilters) { mutableStateOf(currentFilters.assuntos) }
    var selectedAreas by remember(currentFilters) { mutableStateOf(currentFilters.areas) }
    var selectedAnos by remember(currentFilters) { mutableStateOf(currentFilters.anos) }

    fun <T> toggle(set: Set<T>, item: T): Set<T> {
        return if (set.contains(item)) set - item else set + item
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Filtro de Questões",
                        modifier = Modifier.semantics { heading() }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            selectedBancas = emptySet()
                            selectedModalidades = emptySet()
                            selectedDisciplinas = emptySet()
                            selectedAssuntos = emptySet()
                            selectedAreas = emptySet()
                            selectedAnos = emptySet()
                            onApplyFilters(FilterCriteria())
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "Limpar todos os filtros selecionados"
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Botao Aplicar Filtros: aplica e permite continuar na tela adicionando novos
                    OutlinedButton(
                        onClick = {
                            val newCriteria = FilterCriteria(
                                bancas = selectedBancas,
                                modalidades = selectedModalidades,
                                disciplinas = selectedDisciplinas,
                                assuntos = selectedAssuntos,
                                areas = selectedAreas,
                                anos = selectedAnos
                            )
                            onApplyFilters(newCriteria)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .semantics {
                                contentDescription = "Aplicar os filtros selecionados e continuar na tela de filtros"
                            }
                    ) {
                        Icon(Icons.Default.FilterList, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Aplicar Filtros")
                    }

                    // Botao Resolver Questoes: vai direto para as questoes encontradas
                    Button(
                        onClick = {
                            val newCriteria = FilterCriteria(
                                bancas = selectedBancas,
                                modalidades = selectedModalidades,
                                disciplinas = selectedDisciplinas,
                                assuntos = selectedAssuntos,
                                areas = selectedAreas,
                                anos = selectedAnos
                            )
                            onApplyFilters(newCriteria)
                            onNavigateToQuiz()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(54.dp)
                            .semantics {
                                contentDescription = "Ir para o caderno e resolver as questões com os filtros aplicados"
                            }
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (matchingQuestionsCount > 0) "Resolver ($matchingQuestionsCount)" else "Resolver Questões"
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Card de Status dos Filtros e Contador de Questoes Encontradas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .semantics {
                        liveRegion = LiveRegionMode.Polite
                        contentDescription = "Total de $matchingQuestionsCount questões encontradas com os filtros atuais."
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Questões Disponíveis",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "$matchingQuestionsCount questões no banco",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            updateMessage?.let { msg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .semantics {
                            liveRegion = LiveRegionMode.Polite
                            contentDescription = msg
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Text(
                text = "Marque uma ou mais opções em cada categoria (ex: Constitucional + Administrativo) e toque em 'Aplicar Filtros':",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Filtro Multi-Select de Bancas
            MultiSelectFilterChipGroup(
                title = "Banca Examinadora",
                items = filterOptions.bancas,
                selectedItems = selectedBancas,
                onToggleItem = { selectedBancas = toggle(selectedBancas, it) },
                onClearSelection = { selectedBancas = emptySet() },
                itemLabel = { it }
            )

            // Filtro Multi-Select de Disciplinas
            MultiSelectFilterChipGroup(
                title = "Disciplina",
                items = filterOptions.disciplinas,
                selectedItems = selectedDisciplinas,
                onToggleItem = { selectedDisciplinas = toggle(selectedDisciplinas, it) },
                onClearSelection = { selectedDisciplinas = emptySet() },
                itemLabel = { it }
            )

            // Filtro Multi-Select de Assuntos / Tópicos
            MultiSelectFilterChipGroup(
                title = "Assunto / Tópico",
                items = filterOptions.assuntos,
                selectedItems = selectedAssuntos,
                onToggleItem = { selectedAssuntos = toggle(selectedAssuntos, it) },
                onClearSelection = { selectedAssuntos = emptySet() },
                itemLabel = { it }
            )

            // Filtro Multi-Select de Modalidade
            MultiSelectFilterChipGroup(
                title = "Modalidade da Prova",
                items = filterOptions.modalidades,
                selectedItems = selectedModalidades,
                onToggleItem = { selectedModalidades = toggle(selectedModalidades, it) },
                onClearSelection = { selectedModalidades = emptySet() },
                itemLabel = { it }
            )

            // Filtro Multi-Select de Área de Interesse
            MultiSelectFilterChipGroup(
                title = "Área de Interesse",
                items = filterOptions.areas,
                selectedItems = selectedAreas,
                onToggleItem = { selectedAreas = toggle(selectedAreas, it) },
                onClearSelection = { selectedAreas = emptySet() },
                itemLabel = { it }
            )

            // Filtro Multi-Select de Anos
            MultiSelectFilterChipGroup(
                title = "Ano do Concurso",
                items = filterOptions.anos,
                selectedItems = selectedAnos,
                onToggleItem = { selectedAnos = toggle(selectedAnos, it) },
                onClearSelection = { selectedAnos = emptySet() },
                itemLabel = { it.toString() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card Acessivel: Atualizar Banco com IA Angelica
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Atualizar Banco com IA Angélica",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.semantics { heading() }
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "A IA cria questões inéditas e atualizadas de 2026 para as disciplinas selecionadas e grava direto no banco do celular.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FilledTonalButton(
                        onClick = {
                            val banca = selectedBancas.firstOrNull() ?: "FGV"
                            val disc = selectedDisciplinas.firstOrNull() ?: "Direito Constitucional"
                            val ass = selectedAssuntos.firstOrNull() ?: "Direitos e Garantias Fundamentais"
                            onGenerateWithAi?.invoke(banca, disc, ass)
                        },
                        enabled = !isGeneratingQuestions,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Gerar questões inéditas com a IA Angélica para os filtros selecionados"
                            }
                    ) {
                        if (isGeneratingQuestions) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Angélica está gerando questões...")
                        } else {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Gerar Novas Questões com IA")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
