package com.concursos.angelica.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
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
import com.concursos.angelica.ui.components.FilterChipGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    filterOptions: FilterOptions,
    currentFilters: FilterCriteria,
    isGeneratingQuestions: Boolean = false,
    updateMessage: String? = null,
    onApplyFilters: (FilterCriteria) -> Unit,
    onGenerateWithAi: ((banca: String, disciplina: String, assunto: String) -> Unit)? = null,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBanca by remember(currentFilters) { mutableStateOf(currentFilters.banca) }
    var selectedModalidade by remember(currentFilters) { mutableStateOf(currentFilters.modalidade) }
    var selectedDisciplina by remember(currentFilters) { mutableStateOf(currentFilters.disciplina) }
    var selectedAssunto by remember(currentFilters) { mutableStateOf(currentFilters.assunto) }
    var selectedArea by remember(currentFilters) { mutableStateOf(currentFilters.area) }
    var selectedAno by remember(currentFilters) { mutableStateOf(currentFilters.ano) }

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
                            selectedBanca = null
                            selectedModalidade = null
                            selectedDisciplina = null
                            selectedAssunto = null
                            selectedArea = null
                            selectedAno = null
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
                Button(
                    onClick = {
                        val newCriteria = FilterCriteria(
                            banca = selectedBanca,
                            modalidade = selectedModalidade,
                            disciplina = selectedDisciplina,
                            assunto = selectedAssunto,
                            area = selectedArea,
                            ano = selectedAno
                        )
                        onApplyFilters(newCriteria)
                        onNavigateToQuiz()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp)
                        .semantics {
                            contentDescription = "Iniciar simulado com os filtros configurados"
                        }
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Iniciar Estudo com Filtros", style = MaterialTheme.typography.titleMedium)
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
            Text(
                text = "Personalize o seu caderno de questões escolhendo os critérios desejados:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )

            // Filtro de Bancas
            FilterChipGroup(
                title = "Banca Examinadora",
                items = filterOptions.bancas,
                selectedItem = selectedBanca,
                onItemSelected = { selectedBanca = it },
                itemLabel = { it }
            )

            // Filtro de Modalidade
            FilterChipGroup(
                title = "Modalidade da Prova",
                items = filterOptions.modalidades,
                selectedItem = selectedModalidade,
                onItemSelected = { selectedModalidade = it },
                itemLabel = { it }
            )

            // Filtro de Disciplinas
            FilterChipGroup(
                title = "Disciplina",
                items = filterOptions.disciplinas,
                selectedItem = selectedDisciplina,
                onItemSelected = { selectedDisciplina = it },
                itemLabel = { it }
            )

            // Filtro de Assunto / Tópico
            FilterChipGroup(
                title = "Assunto / Tópico",
                items = filterOptions.assuntos,
                selectedItem = selectedAssunto,
                onItemSelected = { selectedAssunto = it },
                itemLabel = { it }
            )

            // Filtro de Área de Interesse
            FilterChipGroup(
                title = "Área de Interesse",
                items = filterOptions.areas,
                selectedItem = selectedArea,
                onItemSelected = { selectedArea = it },
                itemLabel = { it }
            )

            // Filtro de Ano
            FilterChipGroup(
                title = "Ano do Concurso",
                items = filterOptions.anos,
                selectedItem = selectedAno,
                onItemSelected = { selectedAno = it },
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
                        text = "A IA cria questões inéditas e atualizadas para o assunto selecionado e as grava diretamente no banco SQLite do app.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FilledTonalButton(
                        onClick = {
                            val banca = selectedBanca ?: "FGV"
                            val disc = selectedDisciplina ?: "Direito Constitucional"
                            val ass = selectedAssunto ?: "Direitos e Garantias Fundamentais"
                            onGenerateWithAi?.invoke(banca, disc, ass)
                        },
                        enabled = !isGeneratingQuestions,
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics {
                                contentDescription = "Gerar questões inéditas com a IA Angélica para o assunto selecionado e adicionar ao banco de dados"
                            }
                    ) {
                        if (isGeneratingQuestions) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Angélica está gerando questões...")
                        } else {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Gerar e Adicionar Questões Inéditas")
                        }
                    }

                    updateMessage?.let { msg ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.semantics {
                                liveRegion = LiveRegionMode.Polite
                                contentDescription = msg
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
