package com.concursos.angelica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.concursos.angelica.data.model.FilterCriteria
import com.concursos.angelica.data.model.Question
import com.concursos.angelica.data.repository.FilterOptions
import com.concursos.angelica.data.repository.QuestionRepository
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

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

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
            _isGeneratingQuestions.value = false
        }
    }

    fun updateFilters(newFilters: FilterCriteria) {
        _currentFilters.value = newFilters
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            repository.getQuestions(_currentFilters.value).collect { list ->
                _questions.value = list
                _currentIndex.value = 0
            }
        }
    }

    fun submitAnswer(questaoId: Int, resposta: String, acertou: Boolean, tempoSegundos: Int) {
        viewModelScope.launch {
            repository.saveAnswer(questaoId, resposta, acertou, tempoSegundos)
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
