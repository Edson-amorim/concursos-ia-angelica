package com.concursos.angelica

import com.concursos.angelica.data.model.Alternativa
import com.concursos.angelica.data.model.FilterCriteria
import com.concursos.angelica.data.model.Question
import org.json.JSONArray
import org.junit.Assert.*
import org.junit.Test

class FilterCriteriaTest {

    @Test
    fun `dado criteria padrao deve ser vazio`() {
        val criteria = FilterCriteria()
        assertTrue("FilterCriteria padrão deve ser vazio", criteria.isEmpty)
        assertEquals(0, criteria.bancas.size)
        assertEquals(0, criteria.disciplinas.size)
        assertEquals(0, criteria.anos.size)
    }

    @Test
    fun `dado multiplas bancas selecionadas criteria nao deve ser vazio`() {
        val bancasSelecionadas = setOf("FGV", "Cebraspe", "FCC")
        val criteria = FilterCriteria(bancas = bancasSelecionadas)

        assertFalse("Criteria com bancas não pode ser vazio", criteria.isEmpty)
        assertEquals(3, criteria.bancas.size)
        assertTrue(criteria.bancas.contains("FGV"))
        assertTrue(criteria.bancas.contains("Cebraspe"))
    }

    @Test
    fun `dado multiplas disciplinas combinadas com ano 2026 deve preservar filtros`() {
        val disciplinas = setOf("Direito Constitucional", "Direito Administrativo")
        val anos = setOf(2026, 2025)
        val criteria = FilterCriteria(disciplinas = disciplinas, anos = anos)

        assertEquals(2, criteria.disciplinas.size)
        assertEquals(2, criteria.anos.size)
        assertTrue("Deve conter ano 2026", criteria.anos.contains(2026))
        assertTrue("Deve conter Direito Administrativo", criteria.disciplinas.contains("Direito Administrativo"))
    }

    @Test
    fun `dado json de alternativas deve fazer parse correto com gabarito`() {
        val jsonString = """
            [
                {"letra": "A", "texto": "Opção A correta"},
                {"letra": "B", "texto": "Opção B incorreta"},
                {"letra": "C", "texto": "Opção C incorreta"}
            ]
        """.trimIndent()

        val jsonArray = JSONArray(jsonString)
        val alternativas = mutableListOf<Alternativa>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            alternativas.add(Alternativa(letra = obj.getString("letra"), texto = obj.getString("texto")))
        }

        assertEquals(3, alternativas.size)
        assertEquals("A", alternativas[0].letra)
        assertEquals("Opção A correta", alternativas[0].texto)
    }

    @Test
    fun `dado calculo de taxa de aproveitamento deve calcular percentual exato`() {
        val totalQuestoes = 10
        val totalAcertos = 8
        val taxaEsperada = 80.0

        val taxaCalculada = if (totalQuestoes > 0) (totalAcertos.toDouble() / totalQuestoes) * 100 else 0.0
        assertEquals(taxaEsperada, taxaCalculada, 0.001)
    }

    @Test
    fun `dado questao com resposta correta A comparacao de resposta do usuario deve ser valida`() {
        val questao = Question(
            id = 1,
            banca = "FGV",
            modalidade = "Multipla Escolha",
            disciplina = "Direito Constitucional",
            topico = "Controle de Constitucionalidade",
            area = "Tribunais",
            ano = 2026,
            enunciado = "Enunciado teste",
            alternativasJson = "[]",
            respostaCorreta = "A",
            justificativa = "Justificativa teste"
        )

        val respostaCorreta = "A"
        val respostaIncorreta = "B"

        assertTrue("Resposta A deve ser considerada correta", respostaCorreta.equals(questao.respostaCorreta, ignoreCase = true))
        assertFalse("Resposta B deve ser considerada incorreta", respostaIncorreta.equals(questao.respostaCorreta, ignoreCase = true))
    }
}
