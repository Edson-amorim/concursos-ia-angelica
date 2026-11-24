package com.concursos.angelica.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "questoes")
data class Question(
    @PrimaryKey val id: Int,
    val banca: String,
    val modalidade: String, // "Multipla Escolha" ou "Certo/Errado"
    val disciplina: String,
    val topico: String,
    val area: String,
    val ano: Int,
    val enunciado: String,
    val alternativasJson: String, // Lista serializada em JSON das alternativas
    val respostaCorreta: String,  // Letra (A, B, C, D, E) ou "Certo"/"Errado"
    val justificativa: String
)

data class Alternativa(
    val letra: String,
    val texto: String
)

data class FilterCriteria(
    val bancas: Set<String> = emptySet(),
    val modalidades: Set<String> = emptySet(),
    val disciplinas: Set<String> = emptySet(),
    val assuntos: Set<String> = emptySet(),
    val areas: Set<String> = emptySet(),
    val anos: Set<Int> = emptySet()
) {
    val isEmpty: Boolean
        get() = bancas.isEmpty() && modalidades.isEmpty() && disciplinas.isEmpty() &&
                assuntos.isEmpty() && areas.isEmpty() && anos.isEmpty()
}
