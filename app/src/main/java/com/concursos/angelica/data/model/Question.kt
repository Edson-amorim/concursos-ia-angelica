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
    val banca: String? = null,
    val modalidade: String? = null,
    val disciplina: String? = null,
    val area: String? = null,
    val ano: Int? = null
)
