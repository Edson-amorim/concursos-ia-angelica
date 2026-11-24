package com.concursos.angelica.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.concursos.angelica.data.dao.DisciplinaStat
import com.concursos.angelica.data.dao.UserProgressDao
import kotlinx.coroutines.flow.*

class PerformanceViewModel(
    private val userProgressDao: UserProgressDao
) : ViewModel() {

    val totalRespondidas: StateFlow<Int> = userProgressDao.getTotalAnswersCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalAcertos: StateFlow<Int> = userProgressDao.getTotalCorrectAnswersCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val statsPorDisciplina: StateFlow<List<DisciplinaStat>> = userProgressDao.getStatsByDisciplina()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _tempoMedio = MutableStateFlow(0.0)
    val tempoMedio: StateFlow<Double> = _tempoMedio.asStateFlow()

    init {
        viewModelScope.launchWhenStarted {
            _tempoMedio.value = userProgressDao.getAverageTimeSeconds() ?: 0.0
        }
    }
}
