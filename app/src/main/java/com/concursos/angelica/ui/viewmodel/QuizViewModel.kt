package com.concursos.angelica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.concursos.angelica.data.model.FilterCatalog
import com.concursos.angelica.data.model.FilterCriteria
import com.concursos.angelica.data.model.Question
import com.concursos.angelica.data.repository.FilterOptions
import com.concursos.angelica.data.repository.QuestionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuestionRepository
) : ViewModel() {

    private val _currentFilters = MutableStateFlow(FilterCriteria())
    val currentFilters: StateFlow<FilterCriteria> = _currentFilters.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _isLoadingQuestions = MutableStateFlow(false)
    val isLoadingQuestions: StateFlow<Boolean> = _isLoadingQuestions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private var questionsJob: Job? = null

    private val _filterOptions = MutableStateFlow(
        FilterOptions(
            bancas = listOf("FGV", "Cebraspe", "FCC", "Vunesp", "Cesgranrio", "Instituto AOCP", "IADES"),
            modalidades = listOf("Multipla Escolha", "Certo/Errado"),
            disciplinas = listOf(
                "Direito Constitucional", "Direito Administrativo", "Lingua Portuguesa",
                "Raciocinio Logico", "Informatica", "Direito Penal", "Direito Tributario"
            ),
            assuntos = listOf(
                "Direitos e Garantias Fundamentais", "Organizacao do Estado e dos Poderes",
                "Controle de Constitucionalidade", "Principios da Administracao Publica (LIMPE)",
                "Atos Administrativos: Requisitos e Atributos", "Nova Lei de Licitacoes (Lei 14.133/2021)",
                "Interpretacao e Compreensao de Textos", "Crase: Regras Gerais e Casos Proibitivos",
                "Sintaxe de Concordancia Verbal e Nominal", "Proposicoes Logicas e Conectivos",
                "Tabelas-Verdade e Equivalencias Logicas", "Seguranca da Informacao e Principios (CID)",
                "Redes de Computadores e Protocolos de Internet", "Teoria Geral do Delito",
                "Sistema Tributario Nacional e Principios"
            ),
            areas = listOf("Policial", "Fiscal", "Tribunais", "Administrativa", "Controle", "Juridica"),
            anos = (2018..2026).toList().reversed()
        )
    )
    val filterOptions: StateFlow<FilterOptions> = _filterOptions.asStateFlow()

    private val _matchingQuestionsCount = MutableStateFlow(0)
    val matchingQuestionsCount: StateFlow<Int> = _matchingQuestionsCount.asStateFlow()

    init {
        loadQuestions()
        refreshAvailableFilters()
    }

    fun refreshAvailableFilters() {
        viewModelScope.launch {
            try {
                val options = repository.getAvailableFilters(_currentFilters.value.disciplinas)
                if (options.bancas.isNotEmpty()) {
                    _filterOptions.value = options
                }
                val count = repository.countQuestions(_currentFilters.value)
                _matchingQuestionsCount.value = count
            } catch (e: Exception) {
                // Previne crash caso o banco ainda esteja inicializando
            }
        }
    }

    private val _isGeneratingQuestions = MutableStateFlow(false)
    val isGeneratingQuestions: StateFlow<Boolean> = _isGeneratingQuestions.asStateFlow()

    private val _updateMessage = MutableStateFlow<String?>(null)
    val updateMessage: StateFlow<String?> = _updateMessage.asStateFlow()

    fun clearUpdateMessage() {
        _updateMessage.value = null
    }

    fun generateQuestionsWithAi(
        banca: String,
        disciplina: String,
        assunto: String,
        quantidade: Int = 3
    ) {
        viewModelScope.launch {
            _isGeneratingQuestions.value = true
            _updateMessage.value = "A Angélica está formulando $quantidade novas questões inéditas..."
            try {
                val result = repository.generateAndInsertAiQuestions(
                    banca = banca,
                    disciplina = disciplina,
                    assunto = assunto,
                    quantidade = quantidade
                )
                result.onSuccess { qtd ->
                    _updateMessage.value = "Sucesso! $qtd novas questões foram geradas pela IA e inseridas no banco de dados."
                    loadQuestions()
                }.onFailure { err ->
                    _updateMessage.value = "Falha ao gerar questões com IA: ${err.localizedMessage}"
                }
            } catch (e: Exception) {
                _updateMessage.value = "Erro inesperado: ${e.localizedMessage}"
            } finally {
                _isGeneratingQuestions.value = false
            }
        }
    }

    fun onCriteriaChanged(newFilters: FilterCriteria) {
        viewModelScope.launch {
            try {
                // Atualiza assuntos disponíveis dependendo das disciplinas selecionadas
                val validTopicos = FilterCatalog.getAssuntosForDisciplinas(newFilters.disciplinas)
                _filterOptions.value = _filterOptions.value.copy(assuntos = validTopicos)

                // Remove assuntos que não pertencem às disciplinas selecionadas
                val sanitizedAssuntos = if (newFilters.disciplinas.isNotEmpty()) {
                    newFilters.assuntos.filter { validTopicos.contains(it) }.toSet()
                } else {
                    newFilters.assuntos
                }

                val sanitizedFilters = newFilters.copy(assuntos = sanitizedAssuntos)
                _currentFilters.value = sanitizedFilters

                val count = repository.countQuestions(sanitizedFilters)
                _matchingQuestionsCount.value = count

                // Carrega as questões imediatamente para que ao ir para o simulado já esteja pronto!
                loadQuestions()
            } catch (e: Exception) {
                // Falha silenciosa segura
            }
        }
    }

    fun applyFilters(newFilters: FilterCriteria, categoryFeedback: String? = null) {
        viewModelScope.launch {
            try {
                val validTopicos = FilterCatalog.getAssuntosForDisciplinas(newFilters.disciplinas)
                _filterOptions.value = _filterOptions.value.copy(assuntos = validTopicos)

                val sanitizedAssuntos = if (newFilters.disciplinas.isNotEmpty()) {
                    newFilters.assuntos.filter { validTopicos.contains(it) }.toSet()
                } else {
                    newFilters.assuntos
                }

                val sanitizedFilters = newFilters.copy(assuntos = sanitizedAssuntos)
                _currentFilters.value = sanitizedFilters
                loadQuestions()

                val count = repository.countQuestions(sanitizedFilters)
                _matchingQuestionsCount.value = count
                _updateMessage.value = if (categoryFeedback != null) {
                    "$categoryFeedback aplicado! $count questões encontradas."
                } else if (count > 0) {
                    "Filtros aplicados! $count questões encontradas."
                } else {
                    "Nenhuma questão encontrada com essa combinação de filtros. Tente flexibilizar algum critério."
                }
            } catch (e: Exception) {
                _updateMessage.value = "Erro ao buscar questões com este filtro."
            }
        }
    }

    fun resetFilters() {
        applyFilters(FilterCriteria(), categoryFeedback = "Todos os filtros foram limpos")
    }

    fun updateFilters(newFilters: FilterCriteria) {
        applyFilters(newFilters)
    }

    fun loadQuestions() {
        questionsJob?.cancel()
        questionsJob = viewModelScope.launch {
            _isLoadingQuestions.value = true
            try {
                repository.getQuestions(_currentFilters.value).collect { list ->
                    _questions.value = list
                    _currentIndex.value = 0
                    _isLoadingQuestions.value = false
                }
            } catch (e: Exception) {
                _questions.value = emptyList()
                _isLoadingQuestions.value = false
            }
        }
    }

    fun submitAnswer(questaoId: Int, resposta: String, acertou: Boolean, tempoSegundos: Int) {
        viewModelScope.launch {
            try {
                repository.saveAnswer(questaoId, resposta, acertou, tempoSegundos)
            } catch (e: Exception) {
                // Erro ignorado graciosamente para não travar a UI
            }
        }
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.size - 1) {
            _currentIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
        }
    }
}
