package com.concursos.angelica.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J(\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJD\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0015"}, d2 = {"Lcom/concursos/angelica/data/repository/AngelicaMentorRepository;", "", "userProgressDao", "Lcom/concursos/angelica/data/dao/UserProgressDao;", "geminiClient", "Lcom/concursos/angelica/ai/GeminiClient;", "(Lcom/concursos/angelica/data/dao/UserProgressDao;Lcom/concursos/angelica/ai/GeminiClient;)V", "getMentorshipDiagnosis", "Lkotlin/Result;", "", "bancaAlvo", "getMentorshipDiagnosis-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQuestionExplanation", "enunciado", "alternativaEscolhida", "respostaCorreta", "justificativa", "banca", "getQuestionExplanation-hUnOzRk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class AngelicaMentorRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.data.dao.UserProgressDao userProgressDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.concursos.angelica.ai.GeminiClient geminiClient = null;
    
    public AngelicaMentorRepository(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.dao.UserProgressDao userProgressDao, @org.jetbrains.annotations.NotNull()
    com.concursos.angelica.ai.GeminiClient geminiClient) {
        super();
    }
}