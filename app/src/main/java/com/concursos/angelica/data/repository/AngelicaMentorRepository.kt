package com.concursos.angelica.data.repository

import com.concursos.angelica.ai.AngelicaMentorPrompt
import com.concursos.angelica.ai.GeminiClient
import com.concursos.angelica.data.dao.UserProgressDao
import kotlinx.coroutines.flow.first

class AngelicaMentorRepository(
    private val userProgressDao: UserProgressDao,
    private val geminiClient: GeminiClient
) {

    suspend fun getMentorshipDiagnosis(bancaAlvo: String? = null): Result<String> {
        return try {
            val totalRespondidas = userProgressDao.getTotalAnswersCount().first()
            if (totalRespondidas == 0) {
                return Result.success(
                    "Olá! Sou a Angélica, sua mentora de estudos. " +
                    "Você ainda não resolveu nenhuma questão. Que tal aplicar os filtros e resolver sua primeira bateria de 10 questões? " +
                    "Assim que concluir, farei um raio-x completo do seu desempenho!"
                )
            }

            val totalAcertos = userProgressDao.getTotalCorrectAnswersCount().first()
            val taxaGeral = if (totalRespondidas > 0) (totalAcertos.toDouble() / totalRespondidas) * 100 else 0.0
            val tempoMedio = userProgressDao.getAverageTimeSeconds() ?: 0.0

            val statsList = userProgressDao.getStatsByDisciplina().first()
            val statsText = StringBuilder()
            for (stat in statsList) {
                val taxa = if (stat.total > 0) (stat.acertos.toDouble() / stat.total) * 100 else 0.0
                statsText.append("- ${stat.disciplina}: ${stat.acertos}/${stat.total} acertos (${"%.1f".format(taxa)}%)\n")
            }

            val prompt = AngelicaMentorPrompt.buildDiagnosticPrompt(
                totalRespondidas = totalRespondidas,
                totalAcertos = totalAcertos,
                taxaGeral = taxaGeral,
                tempoMedioSegundos = tempoMedio,
                statsDisciplinas = statsText.toString(),
                bancaAlvo = bancaAlvo
            )

            geminiClient.generateContent(prompt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getQuestionExplanation(
        enunciado: String,
        alternativaEscolhida: String,
        respostaCorreta: String,
        justificativa: String,
        banca: String
    ): Result<String> {
        return try {
            val prompt = AngelicaMentorPrompt.buildQuestionExplanationPrompt(
                enunciado = enunciado,
                alternativaEscolhida = alternativaEscolhida,
                respostaCorreta = respostaCorreta,
                justificativa = justificativa,
                banca = banca
            )
            geminiClient.generateContent(prompt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
