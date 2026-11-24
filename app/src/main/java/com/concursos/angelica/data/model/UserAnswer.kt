package com.concursos.angelica.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "respostas_usuario",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["questaoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["questaoId"])]
)
data class UserAnswer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questaoId: Int,
    val respostaEscolhida: String,
    val acertou: Boolean,
    val tempoSegundos: Int,
    val timestampResposta: Long = System.currentTimeMillis()
)

@Entity(tableName = "sessao_estudos")
data class StudySession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val disciplina: String,
    val totalQuestoes: Int,
    val totalAcertos: Int,
    val duracaoSegundos: Int,
    val feedbackAngelica: String? = null,
    val timestampCriacao: Long = System.currentTimeMillis()
)

data class PerformanceByDisciplina(
    val disciplina: String,
    val totalRespondidas: Int,
    val totalAcertos: Int,
    val taxaAcerto: Double
)

data class GeneralPerformance(
    val totalRespondidas: Int,
    val totalAcertos: Int,
    val taxaGeral: Double,
    val tempoMedioSegundos: Double,
    val materiasMaisCriticas: List<PerformanceByDisciplina>
)
