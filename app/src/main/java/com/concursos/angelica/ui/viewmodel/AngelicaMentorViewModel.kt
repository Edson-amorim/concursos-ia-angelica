package com.concursos.angelica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.concursos.angelica.data.repository.AngelicaMentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AngelicaMentorViewModel(
    private val repository: AngelicaMentorRepository
) : ViewModel() {

    private val _mentorAdvice = MutableStateFlow("Carregando orientações da mentora Angélica...")
    val mentorAdvice: StateFlow<String> = _mentorAdvice.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchDiagnosis(null)
    }

    fun fetchDiagnosis(banca: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getMentorshipDiagnosis(banca)
            result.onSuccess { advice ->
                _mentorAdvice.value = advice
            }.onFailure { error ->
                _mentorAdvice.value = "Não foi possível conectar com o servidor no momento: ${error.localizedMessage}\n\nTente novamente mais tarde."
            }
            _isLoading.value = false
        }
    }

    fun explainQuestion(enunciado: String, respostaEscolhida: String, correta: String, justificativa: String, banca: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getQuestionExplanation(enunciado, respostaEscolhida, correta, justificativa, banca)
            result.onSuccess { advice ->
                _mentorAdvice.value = advice
            }.onFailure { error ->
                _mentorAdvice.value = "Erro ao buscar explicação da questão: ${error.localizedMessage}"
            }
            _isLoading.value = false
        }
    }
}
