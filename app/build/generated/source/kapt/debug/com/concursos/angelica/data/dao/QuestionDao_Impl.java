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
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.concursos.angelica.data.model.Question;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  public QuestionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `questoes` (`id`,`banca`,`modalidade`,`disciplina`,`topico`,`area`,`ano`,`enunciado`,`alternativasJson`,`respostaCorreta`,`justificativa`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Question entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getBanca() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBanca());
        }
        if (entity.getModalidade() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getModalidade());
        }
        if (entity.getDisciplina() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDisciplina());
        }
        if (entity.getTopico() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTopico());
        }
        if (entity.getArea() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getArea());
        }
        statement.bindLong(7, entity.getAno());
        if (entity.getEnunciado() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getEnunciado());
        }
        if (entity.getAlternativasJson() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAlternativasJson());
        }
        if (entity.getRespostaCorreta() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getRespostaCorreta());
        }
        if (entity.getJustificativa() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getJustificativa());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<Question> questions,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuestion.insert(questions);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getQuestionById(final int id, final Continuation<? super Question> $completion) {
    final String _sql = "SELECT * FROM questoes WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Question>() {
      @Override
      @Nullable
      public Question call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBanca = CursorUtil.getColumnIndexOrThrow(_cursor, "banca");
          final int _cursorIndexOfModalidade = CursorUtil.getColumnIndexOrThrow(_cursor, "modalidade");
          final int _cursorIndexOfDisciplina = CursorUtil.getColumnIndexOrThrow(_cursor, "disciplina");
          final int _cursorIndexOfTopico = CursorUtil.getColumnIndexOrThrow(_cursor, "topico");
          final int _cursorIndexOfArea = CursorUtil.getColumnIndexOrThrow(_cursor, "area");
          final int _cursorIndexOfAno = CursorUtil.getColumnIndexOrThrow(_cursor, "ano");
          final int _cursorIndexOfEnunciado = CursorUtil.getColumnIndexOrThrow(_cursor, "enunciado");
          final int _cursorIndexOfAlternativasJson = CursorUtil.getColumnIndexOrThrow(_cursor, "alternativasJson");
          final int _cursorIndexOfRespostaCorreta = CursorUtil.getColumnIndexOrThrow(_cursor, "respostaCorreta");
          final int _cursorIndexOfJustificativa = CursorUtil.getColumnIndexOrThrow(_cursor, "justificativa");
          final Question _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpBanca;
            if (_cursor.isNull(_cursorIndexOfBanca)) {
              _tmpBanca = null;
            } else {
              _tmpBanca = _cursor.getString(_cursorIndexOfBanca);
            }
            final String _tmpModalidade;
            if (_cursor.isNull(_cursorIndexOfModalidade)) {
              _tmpModalidade = null;
            } else {
              _tmpModalidade = _cursor.getString(_cursorIndexOfModalidade);
            }
            final String _tmpDisciplina;
            if (_cursor.isNull(_cursorIndexOfDisciplina)) {
              _tmpDisciplina = null;
            } else {
              _tmpDisciplina = _cursor.getString(_cursorIndexOfDisciplina);
            }
            final String _tmpTopico;
            if (_cursor.isNull(_cursorIndexOfTopico)) {
              _tmpTopico = null;
            } else {
              _tmpTopico = _cursor.getString(_cursorIndexOfTopico);
            }
            final String _tmpArea;
            if (_cursor.isNull(_cursorIndexOfArea)) {
              _tmpArea = null;
            } else {
              _tmpArea = _cursor.getString(_cursorIndexOfArea);
            }
            final int _tmpAno;
            _tmpAno = _cursor.getInt(_cursorIndexOfAno);
            final String _tmpEnunciado;
            if (_cursor.isNull(_cursorIndexOfEnunciado)) {
              _tmpEnunciado = null;
            } else {
              _tmpEnunciado = _cursor.getString(_cursorIndexOfEnunciado);
            }
            final String _tmpAlternativasJson;
            if (_cursor.isNull(_cursorIndexOfAlternativasJson)) {
              _tmpAlternativasJson = null;
            } else {
              _tmpAlternativasJson = _cursor.getString(_cursorIndexOfAlternativasJson);
            }
            final String _tmpRespostaCorreta;
            if (_cursor.isNull(_cursorIndexOfRespostaCorreta)) {
              _tmpRespostaCorreta = null;
            } else {
              _tmpRespostaCorreta = _cursor.getString(_cursorIndexOfRespostaCorreta);
            }
            final String _tmpJustificativa;
            if (_cursor.isNull(_cursorIndexOfJustificativa)) {
              _tmpJustificativa = null;
            } else {
              _tmpJustificativa = _cursor.getString(_cursorIndexOfJustificativa);
            }
            _result = new Question(_tmpId,_tmpBanca,_tmpModalidade,_tmpDisciplina,_tmpTopico,_tmpArea,_tmpAno,_tmpEnunciado,_tmpAlternativasJson,_tmpRespostaCorreta,_tmpJustificativa);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalQuestionCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM questoes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMaxQuestionId(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(MAX(id), 0) FROM questoes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllBancas(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT banca FROM questoes ORDER BY banca ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllDisciplinas(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT disciplina FROM questoes ORDER BY disciplina ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllTopicos(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT topico FROM questoes ORDER BY topico ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTopicosByDisciplina(final String disciplina,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT topico FROM questoes WHERE disciplina = ? ORDER BY topico ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (disciplina == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, disciplina);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllAreas(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT area FROM questoes ORDER BY area ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllAnos(final Continuation<? super List<Integer>> $completion) {
    final String _sql = "SELECT DISTINCT ano FROM questoes ORDER BY ano DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Integer>>() {
      @Override
      @NonNull
      public List<Integer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Integer> _result = new ArrayList<Integer>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Integer _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getInt(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllModalidades(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT modalidade FROM questoes ORDER BY modalidade ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Question>> getFilteredQuestions(final SupportSQLiteQuery query) {
    return CoroutinesRoom.createFlow(__db, false, new String[] {"questoes"}, new Callable<List<Question>>() {
      @Override
      @NonNull
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, query, false, null);
        try {
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Question _item;
            _item = __entityCursorConverter_comConcursosAngelicaDataModelQuestion(_cursor);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }
    });
  }

  @Override
  public Object getFilteredQuestionCount(final SupportSQLiteQuery query,
      final Continuation<? super Integer> $completion) {
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, query, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getInt(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private Question __entityCursorConverter_comConcursosAngelicaDataModelQuestion(
      @NonNull final Cursor cursor) {
    final Question _entity;
    final int _cursorIndexOfId = CursorUtil.getColumnIndex(cursor, "id");
    final int _cursorIndexOfBanca = CursorUtil.getColumnIndex(cursor, "banca");
    final int _cursorIndexOfModalidade = CursorUtil.getColumnIndex(cursor, "modalidade");
    final int _cursorIndexOfDisciplina = CursorUtil.getColumnIndex(cursor, "disciplina");
    final int _cursorIndexOfTopico = CursorUtil.getColumnIndex(cursor, "topico");
    final int _cursorIndexOfArea = CursorUtil.getColumnIndex(cursor, "area");
    final int _cursorIndexOfAno = CursorUtil.getColumnIndex(cursor, "ano");
    final int _cursorIndexOfEnunciado = CursorUtil.getColumnIndex(cursor, "enunciado");
    final int _cursorIndexOfAlternativasJson = CursorUtil.getColumnIndex(cursor, "alternativasJson");
    final int _cursorIndexOfRespostaCorreta = CursorUtil.getColumnIndex(cursor, "respostaCorreta");
    final int _cursorIndexOfJustificativa = CursorUtil.getColumnIndex(cursor, "justificativa");
    final int _tmpId;
    if (_cursorIndexOfId == -1) {
      _tmpId = 0;
    } else {
      _tmpId = cursor.getInt(_cursorIndexOfId);
    }
    final String _tmpBanca;
    if (_cursorIndexOfBanca == -1) {
      _tmpBanca = null;
    } else {
      if (cursor.isNull(_cursorIndexOfBanca)) {
        _tmpBanca = null;
      } else {
        _tmpBanca = cursor.getString(_cursorIndexOfBanca);
      }
    }
    final String _tmpModalidade;
    if (_cursorIndexOfModalidade == -1) {
      _tmpModalidade = null;
    } else {
      if (cursor.isNull(_cursorIndexOfModalidade)) {
        _tmpModalidade = null;
      } else {
        _tmpModalidade = cursor.getString(_cursorIndexOfModalidade);
      }
    }
    final String _tmpDisciplina;
    if (_cursorIndexOfDisciplina == -1) {
      _tmpDisciplina = null;
    } else {
      if (cursor.isNull(_cursorIndexOfDisciplina)) {
        _tmpDisciplina = null;
      } else {
        _tmpDisciplina = cursor.getString(_cursorIndexOfDisciplina);
      }
    }
    final String _tmpTopico;
    if (_cursorIndexOfTopico == -1) {
      _tmpTopico = null;
    } else {
      if (cursor.isNull(_cursorIndexOfTopico)) {
        _tmpTopico = null;
      } else {
        _tmpTopico = cursor.getString(_cursorIndexOfTopico);
      }
    }
    final String _tmpArea;
    if (_cursorIndexOfArea == -1) {
      _tmpArea = null;
    } else {
      if (cursor.isNull(_cursorIndexOfArea)) {
        _tmpArea = null;
      } else {
        _tmpArea = cursor.getString(_cursorIndexOfArea);
      }
    }
    final int _tmpAno;
    if (_cursorIndexOfAno == -1) {
      _tmpAno = 0;
    } else {
      _tmpAno = cursor.getInt(_cursorIndexOfAno);
    }
    final String _tmpEnunciado;
    if (_cursorIndexOfEnunciado == -1) {
      _tmpEnunciado = null;
    } else {
      if (cursor.isNull(_cursorIndexOfEnunciado)) {
        _tmpEnunciado = null;
      } else {
        _tmpEnunciado = cursor.getString(_cursorIndexOfEnunciado);
      }
    }
    final String _tmpAlternativasJson;
    if (_cursorIndexOfAlternativasJson == -1) {
      _tmpAlternativasJson = null;
    } else {
      if (cursor.isNull(_cursorIndexOfAlternativasJson)) {
        _tmpAlternativasJson = null;
      } else {
        _tmpAlternativasJson = cursor.getString(_cursorIndexOfAlternativasJson);
      }
    }
    final String _tmpRespostaCorreta;
    if (_cursorIndexOfRespostaCorreta == -1) {
      _tmpRespostaCorreta = null;
    } else {
      if (cursor.isNull(_cursorIndexOfRespostaCorreta)) {
        _tmpRespostaCorreta = null;
      } else {
        _tmpRespostaCorreta = cursor.getString(_cursorIndexOfRespostaCorreta);
      }
    }
    final String _tmpJustificativa;
    if (_cursorIndexOfJustificativa == -1) {
      _tmpJustificativa = null;
    } else {
      if (cursor.isNull(_cursorIndexOfJustificativa)) {
        _tmpJustificativa = null;
      } else {
        _tmpJustificativa = cursor.getString(_cursorIndexOfJustificativa);
      }
    }
    _entity = new Question(_tmpId,_tmpBanca,_tmpModalidade,_tmpDisciplina,_tmpTopico,_tmpArea,_tmpAno,_tmpEnunciado,_tmpAlternativasJson,_tmpRespostaCorreta,_tmpJustificativa);
    return _entity;
  }
}
