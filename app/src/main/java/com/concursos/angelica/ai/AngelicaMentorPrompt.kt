package com.concursos.angelica.ai

object AngelicaMentorPrompt {

    const val SYSTEM_INSTRUCTION = """
Você é a Angélica, uma mentora de inteligência artificial de elite especializada em aprovação em concursos públicos brasileiros.
Seu perfil combina rigor analítico, conhecimento profundo das bancas examinadoras (Cebraspe, FGV, FCC, Vunesp, etc.) e uma comunicação empática, motivadora e direta.

Sua missão:
1. Analisar os dados reais de desempenho do estudante (taxa de acertos geral, disciplinas críticas, tempo médio por questão).
2. Identificar lacunas conceituais e padrões de erro baseados no perfil de cada banca.
3. Fornecer um cronograma corretivo prático e acionável (ex: ciclo de estudos, método de repetição espaçada, foco em lei seca ou jurisprudência).
4. Comunicar-se sempre em português do Brasil de forma clara, acessível e compatível com leitores de tela (TalkBack). Evite tabelas complexas ou formatações que quebrem a leitura em áudio; prefira tópicos curtos, títulos descritivos e pontuação correta.
"""

    fun buildDiagnosticPrompt(
        totalRespondidas: Int,
        totalAcertos: Int,
        taxaGeral: Double,
        tempoMedioSegundos: Double,
        statsDisciplinas: String,
        bancaAlvo: String? = null
    ): String {
        return """
Olá, Angélica! Aqui estão os dados mais recentes do meu progresso nos estudos:

- Total de questões resolvidas: $totalRespondidas
- Total de acertos: $totalAcertos
- Taxa geral de aproveitamento: ${"%.1f".format(taxaGeral)}%
- Tempo médio por questão: ${"%.0f".format(tempoMedioSegundos)} segundos
- Banca de interesse atual: ${bancaAlvo ?: "Geral (diversas bancas)"}

Desempenho detalhado por disciplina:
$statsDisciplinas

Com base nesses dados, por favor elabore:
1. Meu diagnóstico atual de pontos fortes e vulnerabilidades.
2. Análise específica do meu ritmo (tempo gasto por questão) para a prova.
3. Plano de ação para os próximos 7 dias para elevar minha taxa de acerto nas matérias críticas.
4. Uma dica tática de como a banca examinadora costuma armar 'pegadinhas' nesse tema.
"""
    }

    fun buildQuestionExplanationPrompt(
        enunciado: String,
        alternativaEscolhida: String,
        respostaCorreta: String,
        justificativa: String,
        banca: String
    ): String {
        return """
Angélica, acabei de responder a seguinte questão da banca $banca e preciso da sua mentoria para entender meu erro e fixar o conteúdo:

[Enunciado e Alternativas]
$enunciado

- Minha resposta: $alternativaEscolhida
- Gabarito oficial: $respostaCorreta
- Justificativa do gabarito: $justificativa

Por favor:
1. Explique por que a alternativa correta é a verdadeira e por que a que eu marquei é incorreta.
2. Aponte qual foi a provável 'pegadinha' ou armadilha da banca $banca.
3. Diga qual dispositivo legal, súmula ou conceito eu devo revisar para nunca mais errar isso.
"""
    }
}
