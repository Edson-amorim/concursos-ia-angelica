package com.concursos.angelica.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\r2\u0006\u0010\u0010\u001a\u00020\u0011J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/concursos/angelica/data/repository/QuestionRepository;", "", "questionDao", "Lcom/concursos/angelica/data/dao/QuestionDao;", "userProgressDao", "Lcom/concursos/angelica/data/dao/UserProgressDao;", "(Lcom/concursos/angelica/data/dao/QuestionDao;Lcom/concursos/angelica/data/dao/UserProgressDao;)V", "getAvailableFilters", "Lcom/concursos/angelica/data/repository/FilterOptions;", "disciplinaSelecionada", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestions", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/concursos/angelica/data/model/Question;", "filters", "Lcom/concursos/angelica/data/model/FilterCriteria;", "saveAnswer", "", "questaoId", "", "respostaEscolhida", "acertou", "", "tempoSegundos", "(ILjava/lang/String;ZILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class QuestionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.QuestionDao questionDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.UserProgressDao userProgressDao = null;
    
    public QuestionRepository(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.QuestionDao questionDao, @org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.UserProgressDao userProgressDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.concursos.angelica.data.model.Question>> getQuestions(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.FilterCriteria filters) {
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
}