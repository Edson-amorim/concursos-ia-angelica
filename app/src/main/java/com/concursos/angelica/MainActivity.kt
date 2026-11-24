package com.concursos.angelica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.concursos.angelica.ai.GeminiClient
import com.concursos.angelica.data.AppDatabase
import com.concursos.angelica.data.repository.AngelicaMentorRepository
import com.concursos.angelica.data.repository.QuestionRepository
import com.concursos.angelica.ui.screens.*
import com.concursos.angelica.ui.theme.AngelicaTheme
import com.concursos.angelica.ui.viewmodel.AngelicaMentorViewModel
import com.concursos.angelica.ui.viewmodel.PerformanceViewModel
import com.concursos.angelica.ui.viewmodel.QuizViewModel

enum class AppDestination(val title: String, val accessibilityLabel: String) {
    FILTROS("Filtros", "Aba Filtros de questões e bancas"),
    SIMULADO("Simulado", "Aba de resolução de questões"),
    DESEMPENHO("Progresso", "Aba de progresso e estatísticas do estudante"),
    MENTORA("Angélica IA", "Aba da mentora com inteligência artificial Angélica")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Previne falhas continuas capturando excecoes nao tratadas
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            android.util.Log.e("ConcursosAngelica", "Erro nao tratado prevenido: ${throwable.message}", throwable)
        }

        // Inicializacao do Banco de Dados Room e Repositorios
        val database = AppDatabase.getInstance(applicationContext)
        val geminiClient = GeminiClient(apiKey = "") // Pode ser configurada via buildConfig ou Settings
        val questionRepository = QuestionRepository(database.questionDao(), database.userProgressDao(), geminiClient)
        val mentorRepository = AngelicaMentorRepository(database.userProgressDao(), geminiClient)

        val quizViewModel = QuizViewModel(questionRepository)
        val performanceViewModel = PerformanceViewModel(database.userProgressDao())
        val mentorViewModel = AngelicaMentorViewModel(mentorRepository)

        setContent {
            AngelicaTheme {
                var currentDestination by remember { mutableStateOf(AppDestination.FILTROS) }

                val currentFilters by quizViewModel.currentFilters.collectAsState()
                val filterOptions by quizViewModel.filterOptions.collectAsState()
                val questions by quizViewModel.questions.collectAsState()
                val isLoadingQuestions by quizViewModel.isLoadingQuestions.collectAsState()
                val currentIndex by quizViewModel.currentIndex.collectAsState()
                val isGeneratingQuestions by quizViewModel.isGeneratingQuestions.collectAsState()
                val updateMessage by quizViewModel.updateMessage.collectAsState()
                val matchingQuestionsCount by quizViewModel.matchingQuestionsCount.collectAsState()

                val totalRespondidas by performanceViewModel.totalRespondidas.collectAsState()
                val totalAcertos by performanceViewModel.totalAcertos.collectAsState()
                val tempoMedio by performanceViewModel.tempoMedio.collectAsState()
                val statsDisciplinas by performanceViewModel.statsPorDisciplina.collectAsState()

                val mentorAdvice by mentorViewModel.mentorAdvice.collectAsState()
                val isMentorLoading by mentorViewModel.isLoading.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier.semantics {
                                contentDescription = "Menu principal de navegação do aplicativo"
                            }
                        ) {
                            NavigationBarItem(
                                selected = currentDestination == AppDestination.FILTROS,
                                onClick = { currentDestination = AppDestination.FILTROS },
                                icon = { Icon(Icons.Default.FilterList, contentDescription = null) },
                                label = { Text(AppDestination.FILTROS.title) },
                                modifier = Modifier.semantics {
                                    contentDescription = "${AppDestination.FILTROS.accessibilityLabel}, ${if (currentDestination == AppDestination.FILTROS) "selecionada" else "não selecionada"}"
                                }
                            )
                            NavigationBarItem(
                                selected = currentDestination == AppDestination.SIMULADO,
                                onClick = { currentDestination = AppDestination.SIMULADO },
                                icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
                                label = { Text(AppDestination.SIMULADO.title) },
                                modifier = Modifier.semantics {
                                    contentDescription = "${AppDestination.SIMULADO.accessibilityLabel}, ${if (currentDestination == AppDestination.SIMULADO) "selecionada" else "não selecionada"}"
                                }
                            )
                            NavigationBarItem(
                                selected = currentDestination == AppDestination.DESEMPENHO,
                                onClick = { currentDestination = AppDestination.DESEMPENHO },
                                icon = { Icon(Icons.Default.Assessment, contentDescription = null) },
                                label = { Text(AppDestination.DESEMPENHO.title) },
                                modifier = Modifier.semantics {
                                    contentDescription = "${AppDestination.DESEMPENHO.accessibilityLabel}, ${if (currentDestination == AppDestination.DESEMPENHO) "selecionada" else "não selecionada"}"
                                }
                            )
                            NavigationBarItem(
                                selected = currentDestination == AppDestination.MENTORA,
                                onClick = { currentDestination = AppDestination.MENTORA },
                                icon = { Icon(Icons.Default.Face, contentDescription = null) },
                                label = { Text(AppDestination.MENTORA.title) },
                                modifier = Modifier.semantics {
                                    contentDescription = "${AppDestination.MENTORA.accessibilityLabel}, ${if (currentDestination == AppDestination.MENTORA) "selecionada" else "não selecionada"}"
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    when (currentDestination) {
                        AppDestination.FILTROS -> {
                            FilterScreen(
                                filterOptions = filterOptions,
                                currentFilters = currentFilters,
                                matchingQuestionsCount = matchingQuestionsCount,
                                isGeneratingQuestions = isGeneratingQuestions,
                                updateMessage = updateMessage,
                                onCriteriaChanged = { quizViewModel.onCriteriaChanged(it) },
                                onApplyFilters = { criteria, category ->
                                    quizViewModel.applyFilters(criteria, category)
                                },
                                onResetFilters = { quizViewModel.resetFilters() },
                                onGenerateWithAi = { banca, disc, ass ->
                                    quizViewModel.generateQuestionsWithAi(banca, disc, ass)
                                },
                                onNavigateToQuiz = { currentDestination = AppDestination.SIMULADO },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppDestination.SIMULADO -> {
                            QuizScreen(
                                questions = questions,
                                currentIndex = currentIndex,
                                isLoading = isLoadingQuestions,
                                onAnswerSubmitted = { id, ans, isCorrect, tempo ->
                                    quizViewModel.submitAnswer(id, ans, isCorrect, tempo)
                                },
                                onNextQuestion = { quizViewModel.nextQuestion() },
                                onPreviousQuestion = { quizViewModel.previousQuestion() },
                                onConsultAngelica = { question, selectedAnswer ->
                                    mentorViewModel.explainQuestion(
                                        enunciado = question.enunciado,
                                        respostaEscolhida = selectedAnswer,
                                        correta = question.respostaCorreta,
                                        justificativa = question.justificativa,
                                        banca = question.banca
                                    )
                                    currentDestination = AppDestination.MENTORA
                                },
                                onNavigateBack = { currentDestination = AppDestination.FILTROS },
                                onResetFilters = { quizViewModel.resetFilters() },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppDestination.DESEMPENHO -> {
                            PerformanceScreen(
                                totalRespondidas = totalRespondidas,
                                totalAcertos = totalAcertos,
                                tempoMedioSegundos = tempoMedio,
                                statsPorDisciplina = statsDisciplinas,
                                onNavigateToAngelica = { currentDestination = AppDestination.MENTORA },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppDestination.MENTORA -> {
                            AngelicaMentorScreen(
                                mentorAdvice = mentorAdvice,
                                isLoading = isMentorLoading,
                                onRefreshDiagnosis = { banca ->
                                    mentorViewModel.fetchDiagnosis(banca)
                                },
                                onNavigateBack = { currentDestination = AppDestination.DESEMPENHO },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
