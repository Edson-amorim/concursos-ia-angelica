package com.concursos.angelica.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.concursos.angelica.data.model.StudySession
import com.concursos.angelica.data.model.UserAnswer
import kotlinx.coroutines.flow.Flow

data class DisciplinaStat(
    val disciplina: String,
    val total: Int,
    val acertos: Int
)

data class TopicoStat(
    val topico: String,
    val total: Int,
    val acertos: Int
)

@Dao
interface UserProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(userAnswer: UserAnswer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: StudySession): Long

    @Query("SELECT COUNT(*) FROM respostas_usuario")
    fun getTotalAnswersCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM respostas_usuario WHERE acertou = 1")
    fun getTotalCorrectAnswersCount(): Flow<Int>

    @Query("SELECT AVG(tempoSegundos) FROM respostas_usuario")
    suspend fun getAverageTimeSeconds(): Double?

    @Query("""
        SELECT q.disciplina as disciplina, 
               COUNT(r.id) as total, 
               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos
        FROM questoes q
        INNER JOIN respostas_usuario r ON q.id = r.questaoId
        GROUP BY q.disciplina
    """)
    fun getStatsByDisciplina(): Flow<List<DisciplinaStat>>

    @Query("""
        SELECT q.topico as topico, 
               COUNT(r.id) as total, 
               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos
        FROM questoes q
        INNER JOIN respostas_usuario r ON q.id = r.questaoId
        WHERE q.disciplina = :disciplina
        GROUP BY q.topico
    """)
    suspend fun getStatsByTopico(disciplina: String): List<TopicoStat>

    @Query("SELECT * FROM sessao_estudos ORDER BY timestampCriacao DESC LIMIT 10")
    fun getRecentSessions(): Flow<List<StudySession>>

    @Query("SELECT * FROM respostas_usuario ORDER BY timestampResposta DESC LIMIT :limit")
    suspend fun getRecentAnswers(limit: Int): List<UserAnswer>
}
