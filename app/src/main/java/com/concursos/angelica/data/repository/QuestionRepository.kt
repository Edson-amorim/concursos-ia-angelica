package com.concursos.angelica.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.concursos.angelica.data.dao.QuestionDao
import com.concursos.angelica.data.dao.UserProgressDao
import com.concursos.angelica.data.model.FilterCriteria
import com.concursos.angelica.data.model.Question
import com.concursos.angelica.data.model.UserAnswer
import kotlinx.coroutines.flow.Flow

class QuestionRepository(
    private val questionDao: QuestionDao,
    private val userProgressDao: UserProgressDao
) {

    fun getQuestions(filters: FilterCriteria): Flow<List<Question>> {
        val queryBuilder = StringBuilder("SELECT * FROM questoes WHERE 1=1")
        val args = mutableListOf<Any>()

        filters.banca?.let {
            queryBuilder.append(" AND banca = ?")
            args.add(it)
        }
        filters.modalidade?.let {
            queryBuilder.append(" AND modalidade = ?")
            args.add(it)
        }
        filters.disciplina?.let {
            queryBuilder.append(" AND disciplina = ?")
            args.add(it)
        }
        filters.assunto?.let {
            queryBuilder.append(" AND topico = ?")
            args.add(it)
        }
        filters.area?.let {
            queryBuilder.append(" AND area = ?")
            args.add(it)
        }
        filters.ano?.let {
            queryBuilder.append(" AND ano = ?")
            args.add(it)
        }

        queryBuilder.append(" ORDER BY id ASC LIMIT 100")
        val query = SimpleSQLiteQuery(queryBuilder.toString(), args.toTypedArray())
        return questionDao.getFilteredQuestions(query)
    }

    suspend fun saveAnswer(questaoId: Int, respostaEscolhida: String, acertou: Boolean, tempoSegundos: Int) {
        val answer = UserAnswer(
            questaoId = questaoId,
            respostaEscolhida = respostaEscolhida,
            acertou = acertou,
            tempoSegundos = tempoSegundos
        )
        userProgressDao.insertAnswer(answer)
    }

    suspend fun getAvailableFilters(disciplinaSelecionada: String? = null): FilterOptions {
        val topicos = if (disciplinaSelecionada != null) {
            questionDao.getTopicosByDisciplina(disciplinaSelecionada)
        } else {
            questionDao.getAllTopicos()
        }

        return FilterOptions(
            bancas = questionDao.getAllBancas(),
            modalidades = questionDao.getAllModalidades(),
            disciplinas = questionDao.getAllDisciplinas(),
            assuntos = topicos,
            areas = questionDao.getAllAreas(),
            anos = questionDao.getAllAnos()
        )
    }
}

data class FilterOptions(
    val bancas: List<String>,
    val modalidades: List<String>,
    val disciplinas: List<String>,
    val assuntos: List<String>,
    val areas: List<String>,
    val anos: List<Int>
)
