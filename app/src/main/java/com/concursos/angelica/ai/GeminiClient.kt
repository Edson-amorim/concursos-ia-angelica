package com.concursos.angelica.ai

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * Cliente de comunicacao com a API do Google Gemini.
 * Suporta os modelos:
 * - gemini-2.0-flash (recomendado: ultrarrapido, baixa latencia e excelente pedagogia)
 * - gemini-1.5-pro (diagnosticos aprofundados e planejamento semanal complexo)
 * - gemini-1.5-flash (alta eficiencia e baixo consumo)
 */
class GeminiClient(
    private val apiKey: String = "",
    private val model: String = "gemini-2.0-flash"
) {

    suspend fun generateContent(prompt: String): Result<String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            // Modo de demonstracao inteligente da Angelica quando a API key ainda nao foi inserida pelo usuario
            return@withContext Result.success(generateOfflineMentorAdvice(prompt))
        }

        try {
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 25000

            val requestBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().put("text", "${AngelicaMentorPrompt.SYSTEM_INSTRUCTION}\n\n$prompt"))
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(requestBody.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = BufferedReader(InputStreamReader(connection.inputStream)).use { it.readText() }
                val json = JSONObject(responseText)
                val candidates = json.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    val text = parts?.getJSONObject(0)?.optString("text") ?: "Sem resposta gerada."
                    Result.success(text)
                } else {
                    Result.failure(Exception("Nenhum candidato retornado pelo Gemini."))
                }
            } else {
                val errorStream = connection.errorStream?.let { BufferedReader(InputStreamReader(it)).use { r -> r.readText() } }
                Result.failure(Exception("Erro na API ($responseCode): $errorStream"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateOfflineMentorAdvice(prompt: String): String {
        return """
[Angélica - Mentora de Estudos]:
Olá! Analisei seus registros e elaborei um diagnóstico inicial para alavancar seu rendimento:

1. Diagnóstico de Rendimento:
Identifiquei que matérias com enunciados longos e interpretativos exigem maior atenção na gestão do tempo de prova. Manter seu ritmo em até 2 minutos e meio por questão é essencial.

2. Disciplinas Prioritárias:
Recomendo concentrar 60% do seu ciclo de estudos semanal nas disciplinas com aproveitamento abaixo de 70%. Faça baterias curtas de 15 a 20 questões por dia, sempre lendo com atenção o comentário do gabarito.

3. Dica Tática de Bancas:
- Se seu foco for Cebraspe: cuidado com o chute, cada erro anula um acerto; responda com convicção.
- Se seu foco for FGV: redobre a leitura das alternativas, a banca costuma usar termos restritivos como 'exclusivamente' e 'sempre'.

4. Próximo Passo:
Para ativar a análise completa com inteligência artificial generativa em tempo real com o Gemini 2.0 Flash, configure sua chave de API nas configurações do aplicativo.
""".trimIndent()
    }
}
