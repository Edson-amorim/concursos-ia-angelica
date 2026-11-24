package com.concursos.angelica.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\\\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u00112\b\b\u0002\u0010\u0015\u001a\u00020\u00112\b\b\u0002\u0010\u0016\u001a\u00020\n2\b\b\u0002\u0010\u0017\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001a\u0010\u001a\u001a\u00020\u001b2\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u0011H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0 0\u001f2\u0006\u0010\u000b\u001a\u00020\fJB\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nH\u0002J.\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010,R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006-"}, d2 = {"Lcom/concursos/angelica/data/repository/QuestionRepository;", "", "questionDao", "Lcom/concursos/angelica/data/dao/QuestionDao;", "userProgressDao", "Lcom/concursos/angelica/data/dao/UserProgressDao;", "geminiClient", "Lcom/concursos/angelica/ai/GeminiClient;", "(Lcom/concursos/angelica/data/dao/QuestionDao;Lcom/concursos/angelica/data/dao/UserProgressDao;Lcom/concursos/angelica/ai/GeminiClient;)V", "countQuestions", "", "filters", "Lcom/concursos/angelica/data/model/FilterCriteria;", "(Lcom/concursos/angelica/data/model/FilterCriteria;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateAndInsertAiQuestions", "Lkotlin/Result;", "banca", "", "disciplina", "assunto", "modalidade", "area", "ano", "quantidade", "generateAndInsertAiQuestions-eH_QyT8", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAvailableFilters", "Lcom/concursos/angelica/data/repository/FilterOptions;", "disciplinaSelecionada", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/concursos/angelica/data/model/Question;", "parseOrGenerateQuestionsJson", "Lorg/json/JSONArray;", "rawResponse", "saveAnswer", "", "questaoId", "respostaEscolhida", "acertou", "", "tempoSegundos", "(ILjava/lang/String;ZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class QuestionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.QuestionDao questionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.UserProgressDao userProgressDao = null;
    @org.jetbrains.annotations.Nullable()
    private final com.concursos.angelica.ai.GeminiClient geminiClient = null;
    
    public QuestionRepository(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.QuestionDao questionDao, @org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.UserProgressDao userProgressDao, @org.jetbrains.annotations.Nullable()
    com.concursos.angelica.ai.GeminiClient geminiClient) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.concursos.angelica.data.model.Question>> getQuestions(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.FilterCriteria filters) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object countQuestions(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.FilterCriteria filters, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveAnswer(int questaoId, @org.jetbrains.annotations.NotNull()
    java.lang.String respostaEscolhida, boolean acertou, int tempoSegundos, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAvailableFilters(@org.jetbrains.annotations.Nullable()
    java.lang.String disciplinaSelecionada, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.concursos.angelica.data.repository.FilterOptions> $completion) {
        return null;
    }
    
    private final org.json.JSONArray parseOrGenerateQuestionsJson(java.lang.String rawResponse, java.lang.String banca, java.lang.String disciplina, java.lang.String assunto, java.lang.String modalidade, int ano, int quantidade) {
        return null;
    }
}