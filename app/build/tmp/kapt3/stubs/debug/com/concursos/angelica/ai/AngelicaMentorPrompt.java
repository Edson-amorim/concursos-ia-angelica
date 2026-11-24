package com.concursos.angelica.ai;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J:\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00042\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0004J.\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/concursos/angelica/ai/AngelicaMentorPrompt;", "", "()V", "SYSTEM_INSTRUCTION", "", "buildDiagnosticPrompt", "totalRespondidas", "", "totalAcertos", "taxaGeral", "", "tempoMedioSegundos", "statsDisciplinas", "bancaAlvo", "buildQuestionExplanationPrompt", "enunciado", "alternativaEscolhida", "respostaCorreta", "justificativa", "banca", "app_debug"})
public final class AngelicaMentorPrompt {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SYSTEM_INSTRUCTION = "\nVoc\u00ea \u00e9 a Ang\u00e9lica, uma mentora de intelig\u00eancia artificial de elite especializada em aprova\u00e7\u00e3o em concursos p\u00fablicos brasileiros.\nSeu perfil combina rigor anal\u00edtico, conhecimento profundo das bancas examinadoras (Cebraspe, FGV, FCC, Vunesp, etc.) e uma comunica\u00e7\u00e3o emp\u00e1tica, motivadora e direta.\n\nSua miss\u00e3o:\n1. Analisar os dados reais de desempenho do estudante (taxa de acertos geral, disciplinas cr\u00edticas, tempo m\u00e9dio por quest\u00e3o).\n2. Identificar lacunas conceituais e padr\u00f5es de erro baseados no perfil de cada banca.\n3. Fornecer um cronograma corretivo pr\u00e1tico e acion\u00e1vel (ex: ciclo de estudos, m\u00e9todo de repeti\u00e7\u00e3o espa\u00e7ada, foco em lei seca ou jurisprud\u00eancia).\n4. Comunicar-se sempre em portugu\u00eas do Brasil de forma clara, acess\u00edvel e compat\u00edvel com leitores de tela (TalkBack). Evite tabelas complexas ou formata\u00e7\u00f5es que quebrem a leitura em \u00e1udio; prefira t\u00f3picos curtos, t\u00edtulos descritivos e pontua\u00e7\u00e3o correta.\n";
    @org.jetbrains.annotations.NotNull()
    public static final com.concursos.angelica.ai.AngelicaMentorPrompt INSTANCE = null;
    
    private AngelicaMentorPrompt() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String buildDiagnosticPrompt(int totalRespondidas, int totalAcertos, double taxaGeral, double tempoMedioSegundos, @org.jetbrains.annotations.NotNull()
    java.lang.String statsDisciplinas, @org.jetbrains.annotations.Nullable()
    java.lang.String bancaAlvo) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String buildQuestionExplanationPrompt(@org.jetbrains.annotations.NotNull()
    java.lang.String enunciado, @org.jetbrains.annotations.NotNull()
    java.lang.String alternativaEscolhida, @org.jetbrains.annotations.NotNull()
    java.lang.String respostaCorreta, @org.jetbrains.annotations.NotNull()
    java.lang.String justificativa, @org.jetbrains.annotations.NotNull()
    java.lang.String banca) {
        return null;
    }
}