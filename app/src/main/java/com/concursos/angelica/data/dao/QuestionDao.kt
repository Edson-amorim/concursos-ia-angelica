package com.concursos.angelica.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.concursos.angelica.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questoes WHERE id = :id LIMIT 1")
    suspend fun getQuestionById(id: Int): Question?

    @RawQuery(observedEntities = [Question::class])
    fun getFilteredQuestions(query: SupportSQLiteQuery): Flow<List<Question>>

    @Query("SELECT COUNT(*) FROM questoes")
    suspend fun getTotalQuestionCount(): Int

    @Query("SELECT DISTINCT banca FROM questoes ORDER BY banca ASC")
    suspend fun getAllBancas(): List<String>

    @Query("SELECT DISTINCT disciplina FROM questoes ORDER BY disciplina ASC")
    suspend fun getAllDisciplinas(): List<String>

    @Query("SELECT DISTINCT area FROM questoes ORDER BY area ASC")
    suspend fun getAllAreas(): List<String>

    @Query("SELECT DISTINCT ano FROM questoes ORDER BY ano DESC")
    suspend fun getAllAnos(): List<Int>

    @Query("SELECT DISTINCT modalidade FROM questoes ORDER BY modalidade ASC")
    suspend fun getAllModalidades(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)
}
