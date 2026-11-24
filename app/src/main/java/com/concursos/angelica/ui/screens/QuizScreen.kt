package com.concursos.angelica.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
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
import com.concursos.angelica.data.model.Alternativa
import com.concursos.angelica.data.model.Question
import com.concursos.angelica.ui.components.AccessibleQuestionCard
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    questions: List<Question>,
    currentIndex: Int,
    onAnswerSubmitted: (questaoId: Int, resposta: String, acertou: Boolean, tempoSegundos: Int) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onConsultAngelica: (question: Question, selectedAnswer: String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (questions.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Caderno de Questões") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar aos filtros")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Nenhuma questão encontrada para a combinação de filtros selecionada.",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tente alterar os filtros na tela anterior ou toque no botão abaixo para voltar aos filtros.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onNavigateBack,
                        modifier = Modifier.semantics {
                            contentDescription = "Voltar para ajustar os filtros de questões"
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ajustar Filtros")
                    }
                }
            }
        }
        return
    }

    val currentQuestion = questions.getOrNull(currentIndex) ?: questions.first()
    var selectedAnswer by remember(currentQuestion.id) { mutableStateOf<String?>(null) }
    var isAnswered by remember(currentQuestion.id) { mutableStateOf(false) }
    var elapsedTime by remember(currentQuestion.id) { mutableStateOf(0) }

    // Parse das alternativas armazenadas em JSON
    val alternativas = remember(currentQuestion.alternativasJson) {
        val list = mutableListOf<Alternativa>()
        try {
            val jsonArray = JSONArray(currentQuestion.alternativasJson)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(Alternativa(letra = obj.getString("letra"), texto = obj.getString("texto")))
            }
        } catch (e: Exception) {
            if (currentQuestion.modalidade == "Certo/Errado") {
                list.add(Alternativa("Certo", "Certo"))
                list.add(Alternativa("Errado", "Errado"))
            }
        }
        list
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Questão ${currentIndex + 1} de ${questions.size}",
                        modifier = Modifier.semantics { heading() }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.semantics {
                            contentDescription = "Voltar para configuração de filtros"
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
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
                    if (currentIndex > 0) {
                        OutlinedButton(
                            onClick = onPreviousQuestion,
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp)
                                .semantics {
                                    contentDescription = "Ir para questão anterior número $currentIndex"
                                }
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Anterior")
                        }
                    }

                    if (!isAnswered) {
                        Button(
                            onClick = {
                                selectedAnswer?.let { ans ->
                                    isAnswered = true
                                    val isCorrect = ans.equals(currentQuestion.respostaCorreta, ignoreCase = true)
                                    onAnswerSubmitted(currentQuestion.id, ans, isCorrect, elapsedTime)
                                }
                            },
                            enabled = selectedAnswer != null,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(2f)
                                .height(52.dp)
                                .semantics {
                                    contentDescription = if (selectedAnswer != null) {
                                        "Responder à questão com a alternativa $selectedAnswer"
                                    } else {
                                        "Botão responder desabilitado. Selecione uma alternativa primeiro."
                                    }
                                }
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Responder", style = MaterialTheme.typography.titleMedium)
                        }
                    } else {
                        Button(
                            onClick = onNextQuestion,
                            modifier = Modifier
                                .weight(2f)
                                .height(52.dp)
                                .semantics {
                                    contentDescription = "Ir para a próxima questão número ${currentIndex + 2}"
                                }
                        ) {
                            Text("Próxima")
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null)
                        }
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
            AccessibleQuestionCard(
                question = currentQuestion,
                alternativas = alternativas,
                selectedAnswer = selectedAnswer,
                isAnswered = isAnswered,
                onSelectAnswer = { selectedAnswer = it }
            )

            // Botão acessível para mentoria com a Angélica caso respondido
            AnimatedVisibility(visible = isAnswered) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Face, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Mentoria com Angélica IA",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.semantics { heading() }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ficou com dúvida no gabarito ou quer entender a pegadinha da ${currentQuestion.banca}?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        FilledTonalButton(
                            onClick = {
                                onConsultAngelica(currentQuestion, selectedAnswer ?: "")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics {
                                    contentDescription = "Consultar a mentora Angélica para explicar esta questão em detalhes"
                                }
                        ) {
                            Text("Pedir Orientação da Angélica")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
