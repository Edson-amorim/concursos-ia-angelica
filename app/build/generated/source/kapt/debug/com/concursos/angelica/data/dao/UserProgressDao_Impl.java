package com.concursos.angelica.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.concursos.angelica.data.model.StudySession;
import com.concursos.angelica.data.model.UserAnswer;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProgressDao_Impl implements UserProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserAnswer> __insertionAdapterOfUserAnswer;

  private final EntityInsertionAdapter<StudySession> __insertionAdapterOfStudySession;

  public UserProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserAnswer = new EntityInsertionAdapter<UserAnswer>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `respostas_usuario` (`id`,`questaoId`,`respostaEscolhida`,`acertou`,`tempoSegundos`,`timestampResposta`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserAnswer entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuestaoId());
        if (entity.getRespostaEscolhida() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getRespostaEscolhida());
        }
        final int _tmp = entity.getAcertou() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getTempoSegundos());
        statement.bindLong(6, entity.getTimestampResposta());
      }
    };
    this.__insertionAdapterOfStudySession = new EntityInsertionAdapter<StudySession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sessao_estudos` (`id`,`disciplina`,`totalQuestoes`,`totalAcertos`,`duracaoSegundos`,`feedbackAngelica`,`timestampCriacao`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySession entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDisciplina() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDisciplina());
        }
        statement.bindLong(3, entity.getTotalQuestoes());
        statement.bindLong(4, entity.getTotalAcertos());
        statement.bindLong(5, entity.getDuracaoSegundos());
        if (entity.getFeedbackAngelica() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFeedbackAngelica());
        }
        statement.bindLong(7, entity.getTimestampCriacao());
      }
    };
  }

  @Override
  public Object insertAnswer(final UserAnswer userAnswer, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfUserAnswer.insertAndReturnId(userAnswer);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertSession(final StudySession session, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfStudySession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<Integer> getTotalAnswersCount() {
    final String _sql = "SELECT COUNT(*) FROM respostas_usuario";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"respostas_usuario"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalCorrectAnswersCount() {
    final String _sql = "SELECT COUNT(*) FROM respostas_usuario WHERE acertou = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"respostas_usuario"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAverageTimeSeconds(final Continuation<? super Double> arg0) {
    final String _sql = "SELECT AVG(tempoSegundos) FROM respostas_usuario";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Flow<List<DisciplinaStat>> getStatsByDisciplina() {
    final String _sql = "\n"
            + "        SELECT q.disciplina as disciplina, \n"
            + "               COUNT(r.id) as total, \n"
            + "               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos\n"
            + "        FROM questoes q\n"
            + "        INNER JOIN respostas_usuario r ON q.id = r.questaoId\n"
            + "        GROUP BY q.disciplina\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"questoes",
        "respostas_usuario"}, new Callable<List<DisciplinaStat>>() {
      @Override
      @NonNull
      public List<DisciplinaStat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDisciplina = 0;
          final int _cursorIndexOfTotal = 1;
          final int _cursorIndexOfAcertos = 2;
          final List<DisciplinaStat> _result = new ArrayList<DisciplinaStat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DisciplinaStat _item;
            final String _tmpDisciplina;
            if (_cursor.isNull(_cursorIndexOfDisciplina)) {
              _tmpDisciplina = null;
            } else {
              _tmpDisciplina = _cursor.getString(_cursorIndexOfDisciplina);
            }
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            final int _tmpAcertos;
            _tmpAcertos = _cursor.getInt(_cursorIndexOfAcertos);
            _item = new DisciplinaStat(_tmpDisciplina,_tmpTotal,_tmpAcertos);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getStatsByTopico(final String disciplina,
      final Continuation<? super List<TopicoStat>> arg1) {
    final String _sql = "\n"
            + "        SELECT q.topico as topico, \n"
            + "               COUNT(r.id) as total, \n"
            + "               SUM(CASE WHEN r.acertou = 1 THEN 1 ELSE 0 END) as acertos\n"
            + "        FROM questoes q\n"
            + "        INNER JOIN respostas_usuario r ON q.id = r.questaoId\n"
            + "        WHERE q.disciplina = ?\n"
            + "        GROUP BY q.topico\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (disciplina == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, disciplina);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TopicoStat>>() {
      @Override
      @NonNull
      public List<TopicoStat> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTopico = 0;
          final int _cursorIndexOfTotal = 1;
          final int _cursorIndexOfAcertos = 2;
          final List<TopicoStat> _result = new ArrayList<TopicoStat>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TopicoStat _item;
            final String _tmpTopico;
            if (_cursor.isNull(_cursorIndexOfTopico)) {
              _tmpTopico = null;
            } else {
              _tmpTopico = _cursor.getString(_cursorIndexOfTopico);
            }
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            final int _tmpAcertos;
            _tmpAcertos = _cursor.getInt(_cursorIndexOfAcertos);
            _item = new TopicoStat(_tmpTopico,_tmpTotal,_tmpAcertos);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<StudySession>> getRecentSessions() {
    final String _sql = "SELECT * FROM sessao_estudos ORDER BY timestampCriacao DESC LIMIT 10";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sessao_estudos"}, new Callable<List<StudySession>>() {
      @Override
      @NonNull
      public List<StudySession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDisciplina = CursorUtil.getColumnIndexOrThrow(_cursor, "disciplina");
          final int _cursorIndexOfTotalQuestoes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestoes");
          final int _cursorIndexOfTotalAcertos = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAcertos");
          final int _cursorIndexOfDuracaoSegundos = CursorUtil.getColumnIndexOrThrow(_cursor, "duracaoSegundos");
          final int _cursorIndexOfFeedbackAngelica = CursorUtil.getColumnIndexOrThrow(_cursor, "feedbackAngelica");
          final int _cursorIndexOfTimestampCriacao = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampCriacao");
          final List<StudySession> _result = new ArrayList<StudySession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDisciplina;
            if (_cursor.isNull(_cursorIndexOfDisciplina)) {
              _tmpDisciplina = null;
            } else {
              _tmpDisciplina = _cursor.getString(_cursorIndexOfDisciplina);
            }
            final int _tmpTotalQuestoes;
            _tmpTotalQuestoes = _cursor.getInt(_cursorIndexOfTotalQuestoes);
            final int _tmpTotalAcertos;
            _tmpTotalAcertos = _cursor.getInt(_cursorIndexOfTotalAcertos);
            final int _tmpDuracaoSegundos;
            _tmpDuracaoSegundos = _cursor.getInt(_cursorIndexOfDuracaoSegundos);
            final String _tmpFeedbackAngelica;
            if (_cursor.isNull(_cursorIndexOfFeedbackAngelica)) {
              _tmpFeedbackAngelica = null;
            } else {
              _tmpFeedbackAngelica = _cursor.getString(_cursorIndexOfFeedbackAngelica);
            }
            final long _tmpTimestampCriacao;
            _tmpTimestampCriacao = _cursor.getLong(_cursorIndexOfTimestampCriacao);
            _item = new StudySession(_tmpId,_tmpDisciplina,_tmpTotalQuestoes,_tmpTotalAcertos,_tmpDuracaoSegundos,_tmpFeedbackAngelica,_tmpTimestampCriacao);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getRecentAnswers(final int limit,
      final Continuation<? super List<UserAnswer>> arg1) {
    final String _sql = "SELECT * FROM respostas_usuario ORDER BY timestampResposta DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<UserAnswer>>() {
      @Override
      @NonNull
      public List<UserAnswer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuestaoId = CursorUtil.getColumnIndexOrThrow(_cursor, "questaoId");
          final int _cursorIndexOfRespostaEscolhida = CursorUtil.getColumnIndexOrThrow(_cursor, "respostaEscolhida");
          final int _cursorIndexOfAcertou = CursorUtil.getColumnIndexOrThrow(_cursor, "acertou");
          final int _cursorIndexOfTempoSegundos = CursorUtil.getColumnIndexOrThrow(_cursor, "tempoSegundos");
          final int _cursorIndexOfTimestampResposta = CursorUtil.getColumnIndexOrThrow(_cursor, "timestampResposta");
          final List<UserAnswer> _result = new ArrayList<UserAnswer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserAnswer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpQuestaoId;
            _tmpQuestaoId = _cursor.getInt(_cursorIndexOfQuestaoId);
            final String _tmpRespostaEscolhida;
            if (_cursor.isNull(_cursorIndexOfRespostaEscolhida)) {
              _tmpRespostaEscolhida = null;
            } else {
              _tmpRespostaEscolhida = _cursor.getString(_cursorIndexOfRespostaEscolhida);
            }
            final boolean _tmpAcertou;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAcertou);
            _tmpAcertou = _tmp != 0;
            final int _tmpTempoSegundos;
            _tmpTempoSegundos = _cursor.getInt(_cursorIndexOfTempoSegundos);
            final long _tmpTimestampResposta;
            _tmpTimestampResposta = _cursor.getLong(_cursorIndexOfTimestampResposta);
            _item = new UserAnswer(_tmpId,_tmpQuestaoId,_tmpRespostaEscolhida,_tmpAcertou,_tmpTempoSegundos,_tmpTimestampResposta);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
