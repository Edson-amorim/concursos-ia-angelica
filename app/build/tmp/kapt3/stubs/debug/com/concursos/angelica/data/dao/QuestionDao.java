package com.concursos.angelica.data.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00030\u00112\u0006\u0010\r\u001a\u00020\u000eH\'J\u000e\u0010\u0013\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u0006\u0010\u0018\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001c\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00120\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006\u001f"}, d2 = {"Lcom/concursos/angelica/data/dao/QuestionDao;", "", "getAllAnos", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllAreas", "", "getAllBancas", "getAllDisciplinas", "getAllModalidades", "getAllTopicos", "getFilteredQuestionCount", "query", "Landroidx/sqlite/db/SupportSQLiteQuery;", "(Landroidx/sqlite/db/SupportSQLiteQuery;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFilteredQuestions", "Lkotlinx/coroutines/flow/Flow;", "Lcom/concursos/angelica/data/model/Question;", "getMaxQuestionId", "getQuestionById", "id", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTopicosByDisciplina", "disciplina", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTotalQuestionCount", "insertAll", "", "questions", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface QuestionDao {
    
    @androidx.room.Query(value = "SELECT * FROM questoes WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQuestionById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.concursos.angelica.data.model.Question> $completion);
    
    @androidx.room.RawQuery(observedEntities = {com.concursos.angelica.data.model.Question.class})
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.concursos.angelica.data.model.Question>> getFilteredQuestions(@org.jetbrains.annotations.NotNull()
    androidx.sqlite.db.SupportSQLiteQuery query);
    
    @androidx.room.RawQuery()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFilteredQuestionCount(@org.jetbrains.annotations.NotNull()
    androidx.sqlite.db.SupportSQLiteQuery query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM questoes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalQuestionCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COALESCE(MAX(id), 0) FROM questoes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMaxQuestionId(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT banca FROM questoes ORDER BY banca ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllBancas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT disciplina FROM questoes ORDER BY disciplina ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllDisciplinas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT topico FROM questoes ORDER BY topico ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllTopicos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT topico FROM questoes WHERE disciplina = :disciplina ORDER BY topico ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTopicosByDisciplina(@org.jetbrains.annotations.NotNull()
    java.lang.String disciplina, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT area FROM questoes ORDER BY area ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllAreas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT ano FROM questoes ORDER BY ano DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllAnos(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Integer>> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT modalidade FROM questoes ORDER BY modalidade ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllModalidades(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertAll(@org.jetbrains.annotations.NotNull()
    java.util.List<com.concursos.angelica.data.model.Question> questions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}