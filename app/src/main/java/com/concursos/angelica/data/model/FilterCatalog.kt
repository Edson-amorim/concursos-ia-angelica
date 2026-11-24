package com.concursos.angelica.data.model

object FilterCatalog {

    val DISCIPLINA_TOPICOS_MAP: Map<String, List<String>> = mapOf(
        "Direito Administrativo" to listOf(
            "Agentes Publicos e Regime Juridico Unico",
            "Atos Administrativos: Requisitos e Atributos",
            "Nova Lei de Licitacoes (Lei 14.133) e Regimes de IA no Servico Publico em 2026",
            "Nova Lei de Licitacoes (Lei 14.133/2021)",
            "Poderes Administrativos e Poder de Policia",
            "Principios da Administracao Publica (LIMPE)",
            "Responsabilidade Civil do Estado"
        ),
        "Direito Constitucional" to listOf(
            "Acoes Constitucionais (MS, HC, HD, MI)",
            "Controle de Constitucionalidade",
            "Defesa do Estado e das Instituicoes Democraticas",
            "Direitos e Garantias Fundamentais",
            "Ordem Social e Ordem Economica",
            "Organizacao do Estado e dos Poderes",
            "Regulamentacao da Reforma Tributaria e Federalismo Fiscal em 2026"
        ),
        "Direito Penal" to listOf(
            "Aplicacao da Lei Penal no Tempo e no Espaco",
            "Causas Excludentes de Ilicitude",
            "Crimes Digitais Avancados, Fraudes Virtuais e Criptoativos em 2026",
            "Crimes contra a Administracao Publica",
            "Crimes contra a Pessoa e contra o Patrimonio",
            "Teoria Geral do Delito (Fato Tipico, Antijuridico e Culpavel)"
        ),
        "Direito Tributario" to listOf(
            "Competencia Tributaria e Especies de Tributos",
            "Credito Tributario: Constituicao, Suspensao e Extincao",
            "Imunidades e Isencoes Tributarias",
            "Instituicao e Fiscalizacao do IBS e CBS no Sistema Tributario 2026",
            "Sistema Tributario Nacional e Principios"
        ),
        "Informatica" to listOf(
            "Ameacas Ciberneticas e Mecanismos de Defesa",
            "Computacao em Nuvem e Ferramentas Colaborativas",
            "Planilhas Eletronicas e Funcoes Avancadas",
            "Redes de Computadores e Protocolos de Internet",
            "Seguranca Zero Trust, IA Generativa e Criptografia em 2026",
            "Seguranca da Informacao e Principios (CID)",
            "Sistemas Operacionais (Windows e Linux)"
        ),
        "Lingua Portuguesa" to listOf(
            "Coesao e Coerencia Textual",
            "Crase: Regras Gerais e Casos Proibitivos",
            "Interpretacao e Compreensao de Textos",
            "Pontuacao e Emprego dos Sinais Graficos",
            "Regencia Verbal e Nominal",
            "Semantica Textual, Figuras de Sintaxe e Estilo das Bancas em 2026",
            "Sintaxe de Concordancia Verbal e Nominal"
        ),
        "Raciocinio Logico" to listOf(
            "Analise Combinatoria e Probabilidade",
            "Diagramas Logicos e Silogismos",
            "Logica de Argumentacao",
            "Logica de Argumentacao e Teoria dos Grafos em Concursos de 2026",
            "Proposicoes Logicas e Conectivos",
            "Sequencias Logicas e Padroes Numericos",
            "Tabelas-Verdade e Equivalencias Logicas"
        )
    )

    fun getAssuntosForDisciplinas(disciplinas: Set<String>): List<String> {
        if (disciplinas.isEmpty()) {
            return DISCIPLINA_TOPICOS_MAP.values.flatten().distinct().sorted()
        }
        return disciplinas.flatMap { DISCIPLINA_TOPICOS_MAP[it] ?: emptyList() }
            .distinct()
            .sorted()
    }

    val TODAS_DISCIPLINAS: List<String> = DISCIPLINA_TOPICOS_MAP.keys.toList().sorted()
    val TODOS_ASSUNTOS: List<String> = DISCIPLINA_TOPICOS_MAP.values.flatten().distinct().sorted()
    val TODAS_BANCAS: List<String> = listOf("FGV", "Cebraspe", "FCC", "Vunesp", "Cesgranrio", "Instituto AOCP", "IADES")
    val TODAS_MODALIDADES: List<String> = listOf("Multipla Escolha", "Certo/Errado")
    val TODAS_AREAS: List<String> = listOf("Policial", "Fiscal", "Tribunais", "Administrativa", "Controle", "Juridica")
    val TODOS_ANOS: List<Int> = (2018..2026).toList().reversed()
}
