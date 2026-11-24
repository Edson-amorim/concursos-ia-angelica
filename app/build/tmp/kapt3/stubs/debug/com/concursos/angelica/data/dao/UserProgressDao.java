package com.concursos.angelica.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00060\fH\'J\u0014\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00060\fH\'J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u001d\u00a8\u0006\u001e"}, d2 = {"Lcom/concursos/angelica/data/dao/UserProgressDao;", "", "getAverageTimeSeconds", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentAnswers", "", "Lcom/concursos/angelica/data/model/UserAnswer;", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentSessions", "Lkotlinx/coroutines/flow/Flow;", "Lcom/concursos/angelica/data/model/StudySession;", "getStatsByDisciplina", "Lcom/concursos/angelica/data/dao/DisciplinaStat;", "getStatsByTopico", "Lcom/concursos/angelica/data/dao/TopicoStat;", "disciplina", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalAnswersCount", "getTotalCorrectAnswersCount", "insertAnswer", "", "userAnswer", "(Lcom/concursos/angelica/data/model/UserAnswer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSession", "session", "(Lcom/concursos/angelica/data/model/StudySession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserProgressDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAnswer(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.UserAnswer userAnswer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSession(@org.jetbrains.annotations.NotNull()
    com.concursos.angelica.data.model.StudySession session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM respostas_usuario")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalAnswersCount();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM respostas_usuario WHERE acertou = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalCorrectAnswersCount();
    
    @androidx.room.Query(value = "SELECT AVG(tempoSegundos) FROM respostas_usuario")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAverageTimeSeconds(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion);
    
    @androidx.room.Query(value = "\n        SELECT q.disciplina as disciplina, \n               COUNT(r.id) as total, \n               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos\n        FROM questoes q\n        INNER JOIN respostas_usuario r ON q.id = r.questaoId\n        GROUP BY q.disciplina\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.concursos.angelica.data.dao.DisciplinaStat>> getStatsByDisciplina();
    
    @androidx.room.Query(value = "\n        SELECT q.topico as topico, \n               COUNT(r.id) as total, \n               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos\n        FROM questoes q\n        INNER JOIN respostas_usuario r ON q.id = r.questaoId\n        WHERE q.disciplina = :disciplina\n        GROUP BY q.topico\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStatsByTopico(@org.jetbrains.annotations.NotNull()
    java.lang.String disciplina, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.concursos.angelica.data.dao.TopicoStat>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM sessao_estudos ORDER BY timestampCriacao DESC LIMIT 10")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.concursos.angelica.data.model.StudySession>> getRecentSessions();
    
    @androidx.room.Query(value = "SELECT * FROM respostas_usuario ORDER BY timestampResposta DESC LIMIT :limit")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecentAnswers(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.concursos.angelica.data.model.UserAnswer>> $completion);
}