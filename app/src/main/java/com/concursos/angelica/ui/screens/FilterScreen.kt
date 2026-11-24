package com.concursos.angelica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
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
import com.concursos.angelica.data.model.FilterCatalog
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
    onCriteriaChanged: (FilterCriteria) -> Unit,
    onApplyFilters: (FilterCriteria, String?) -> Unit,
    onResetFilters: () -> Unit,
    onGenerateWithAi: ((banca: String, disciplina: String, assunto: String) -> Unit)? = null,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBancas by remember(currentFilters.bancas) { mutableStateOf(currentFilters.bancas) }
    var selectedModalidades by remember(currentFilters.modalidades) { mutableStateOf(currentFilters.modalidades) }
    var selectedDisciplinas by remember(currentFilters.disciplinas) { mutableStateOf(currentFilters.disciplinas) }
    var selectedAssuntos by remember(currentFilters.assuntos) { mutableStateOf(currentFilters.assuntos) }
    var selectedAreas by remember(currentFilters.areas) { mutableStateOf(currentFilters.areas) }
    var selectedAnos by remember(currentFilters.anos) { mutableStateOf(currentFilters.anos) }

    fun <T> toggle(set: Set<T>, item: T): Set<T> {
        return if (set.contains(item)) set - item else set + item
    }

    // Assuntos estritamente dependentes das disciplinas selecionadas
    val availableAssuntos = remember(selectedDisciplinas, filterOptions.assuntos) {
        if (selectedDisciplinas.isEmpty()) {
            filterOptions.assuntos
        } else {
            FilterCatalog.getAssuntosForDisciplinas(selectedDisciplinas)
        }
    }

    fun buildCurrentCriteria(
        bancas: Set<String> = selectedBancas,
        modalidades: Set<String> = selectedModalidades,
        disciplinas: Set<String> = selectedDisciplinas,
        assuntos: Set<String> = selectedAssuntos,
        areas: Set<String> = selectedAreas,
        anos: Set<Int> = selectedAnos
    ): FilterCriteria {
        return FilterCriteria(
            bancas = bancas,
            modalidades = modalidades,
            disciplinas = disciplinas,
            assuntos = assuntos,
            areas = areas,
            anos = anos
        )
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
                            onResetFilters()
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "Limpar todos os filtros selecionados e restaurar todas as questões"
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
                    // Botão Aplicar Todos os Filtros na barra inferior
                    OutlinedButton(
                        onClick = {
                            val criteria = buildCurrentCriteria()
                            onApplyFilters(criteria, "Todos os filtros")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .semantics {
                                contentDescription = "Aplicar todos os filtros selecionados"
                            }
                    ) {
                        Icon(Icons.Default.FilterList, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Aplicar")
                    }

                    // Botão Resolver Questões
                    Button(
                        onClick = {
                            val criteria = buildCurrentCriteria()
                            onApplyFilters(criteria, null)
                            onNavigateToQuiz()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .weight(1.4f)
                            .height(54.dp)
                            .semantics {
                                contentDescription = "Ir para o simulado resolver questões"
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
            // Card Superior de Status com Contagem em Tempo Real
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .semantics {
                        liveRegion = LiveRegionMode.Polite
                        contentDescription = "Status dos filtros: $matchingQuestionsCount questões encontradas."
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(14.dp)
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
                            text = "Caderno Atual",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "$matchingQuestionsCount questões disponíveis",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    FilledTonalButton(
                        onClick = {
                            val criteria = buildCurrentCriteria()
                            onApplyFilters(criteria, null)
                            onNavigateToQuiz()
                        }
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Resolver")
                    }
                }
            }

            // Mensagem de feedback de atualização
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
                text = "Marque suas opções nas caixas abaixo. Cada caixa possui seu próprio botão 'Aplicar' e os filtros também sincronizam em tempo real:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Caixa 1: Bancas Examinadoras
            MultiSelectFilterChipGroup(
                title = "Banca Examinadora",
                icon = Icons.Default.School,
                items = filterOptions.bancas,
                selectedItems = selectedBancas,
                onToggleItem = { item ->
                    val newSet = toggle(selectedBancas, item)
                    selectedBancas = newSet
                    val criteria = buildCurrentCriteria(bancas = newSet)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedBancas = emptySet()
                    val criteria = buildCurrentCriteria(bancas = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Banca")
                },
                itemLabel = { it }
            )

            // Caixa 2: Disciplinas (Ao mudar, atualiza imediatamente os assuntos pertinentes)
            MultiSelectFilterChipGroup(
                title = "Disciplina",
                icon = Icons.Default.MenuBook,
                items = filterOptions.disciplinas,
                selectedItems = selectedDisciplinas,
                onToggleItem = { item ->
                    val newDisciplinas = toggle(selectedDisciplinas, item)
                    selectedDisciplinas = newDisciplinas

                    // Higieniza assuntos selecionados para remover assuntos de outras matérias
                    val allowedAssuntos = FilterCatalog.getAssuntosForDisciplinas(newDisciplinas)
                    val newAssuntos = if (newDisciplinas.isNotEmpty()) {
                        selectedAssuntos.filter { allowedAssuntos.contains(it) }.toSet()
                    } else {
                        selectedAssuntos
                    }
                    selectedAssuntos = newAssuntos

                    val criteria = buildCurrentCriteria(disciplinas = newDisciplinas, assuntos = newAssuntos)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedDisciplinas = emptySet()
                    val criteria = buildCurrentCriteria(disciplinas = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Disciplina")
                },
                itemLabel = { it }
            )

            // Caixa 3: Assuntos / Tópicos (Estritamente dependente da disciplina escolhida)
            val emptyAssuntosMsg = if (selectedDisciplinas.isEmpty()) {
                "Exibindo todos os tópicos gerais. Selecione uma disciplina na caixa acima para ver estritamente os assuntos dela."
            } else {
                "Nenhum tópico cadastrado para a disciplina selecionada."
            }

            MultiSelectFilterChipGroup(
                title = if (selectedDisciplinas.isNotEmpty()) {
                    "Assuntos (${selectedDisciplinas.joinToString(", ")})"
                } else {
                    "Assuntos / Tópicos da Matéria"
                },
                icon = Icons.Default.Bookmark,
                items = availableAssuntos,
                selectedItems = selectedAssuntos,
                emptyMessage = if (availableAssuntos.isEmpty()) emptyAssuntosMsg else null,
                onToggleItem = { item ->
                    val newSet = toggle(selectedAssuntos, item)
                    selectedAssuntos = newSet
                    val criteria = buildCurrentCriteria(assuntos = newSet)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedAssuntos = emptySet()
                    val criteria = buildCurrentCriteria(assuntos = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Assunto")
                },
                itemLabel = { it }
            )

            // Caixa 4: Modalidade da Prova
            MultiSelectFilterChipGroup(
                title = "Modalidade da Prova",
                icon = Icons.Default.FormatListBulleted,
                items = filterOptions.modalidades,
                selectedItems = selectedModalidades,
                onToggleItem = { item ->
                    val newSet = toggle(selectedModalidades, item)
                    selectedModalidades = newSet
                    val criteria = buildCurrentCriteria(modalidades = newSet)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedModalidades = emptySet()
                    val criteria = buildCurrentCriteria(modalidades = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Modalidade")
                },
                itemLabel = { it }
            )

            // Caixa 5: Área de Concurso
            MultiSelectFilterChipGroup(
                title = "Área de Interesse",
                icon = Icons.Default.AccountBalance,
                items = filterOptions.areas,
                selectedItems = selectedAreas,
                onToggleItem = { item ->
                    val newSet = toggle(selectedAreas, item)
                    selectedAreas = newSet
                    val criteria = buildCurrentCriteria(areas = newSet)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedAreas = emptySet()
                    val criteria = buildCurrentCriteria(areas = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Área")
                },
                itemLabel = { it }
            )

            // Caixa 6: Ano do Concurso
            MultiSelectFilterChipGroup(
                title = "Ano do Concurso",
                icon = Icons.Default.DateRange,
                items = filterOptions.anos,
                selectedItems = selectedAnos,
                onToggleItem = { item ->
                    val newSet = toggle(selectedAnos, item)
                    selectedAnos = newSet
                    val criteria = buildCurrentCriteria(anos = newSet)
                    onCriteriaChanged(criteria)
                },
                onClearSelection = {
                    selectedAnos = emptySet()
                    val criteria = buildCurrentCriteria(anos = emptySet())
                    onCriteriaChanged(criteria)
                },
                onApplyCategory = {
                    val criteria = buildCurrentCriteria()
                    onApplyFilters(criteria, "Filtro de Ano")
                },
                itemLabel = { it.toString() }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // =========================================================================
            // PAINEL DE AÇÃO PRINCIPAL ACIMA DA ANGÉLICA (Requisito explícito do usuário)
            // "o botão aplicar deve ser asima da angelica para facilitar"
            // =========================================================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Pronto para Estudar?",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.semantics { heading() }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$matchingQuestionsCount questões prontas para resolver no caderno atual.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val criteria = buildCurrentCriteria()
                                onApplyFilters(criteria, "Todos os filtros")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .semantics {
                                    contentDescription = "Aplicar todos os filtros selecionados"
                                }
                        ) {
                            Icon(Icons.Default.DoneAll, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Aplicar Filtros")
                        }

                        FilledTonalButton(
                            onClick = {
                                val criteria = buildCurrentCriteria()
                                onApplyFilters(criteria, null)
                                onNavigateToQuiz()
                            },
                            modifier = Modifier
                                .weight(1.2f)
                                .height(52.dp)
                                .semantics {
                                    contentDescription = "Ir direto para resolver as questões filtradas"
                                }
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Resolver Questões")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Card da Mentora IA Angélica (Fica logo abaixo do botão aplicar, conforme solicitado)
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
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
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

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
