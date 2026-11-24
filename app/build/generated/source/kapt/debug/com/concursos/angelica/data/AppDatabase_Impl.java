package com.concursos.angelica.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.concursos.angelica.data.dao.QuestionDao;
import com.concursos.angelica.data.dao.QuestionDao_Impl;
import com.concursos.angelica.data.dao.UserProgressDao;
import com.concursos.angelica.data.dao.UserProgressDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile QuestionDao _questionDao;

  private volatile UserProgressDao _userProgressDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `questoes` (`id` INTEGER NOT NULL, `banca` TEXT NOT NULL, `modalidade` TEXT NOT NULL, `disciplina` TEXT NOT NULL, `topico` TEXT NOT NULL, `area` TEXT NOT NULL, `ano` INTEGER NOT NULL, `enunciado` TEXT NOT NULL, `alternativasJson` TEXT NOT NULL, `respostaCorreta` TEXT NOT NULL, `justificativa` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `respostas_usuario` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `questaoId` INTEGER NOT NULL, `respostaEscolhida` TEXT NOT NULL, `acertou` INTEGER NOT NULL, `tempoSegundos` INTEGER NOT NULL, `timestampResposta` INTEGER NOT NULL, FOREIGN KEY(`questaoId`) REFERENCES `questoes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_respostas_usuario_questaoId` ON `respostas_usuario` (`questaoId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sessao_estudos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `disciplina` TEXT NOT NULL, `totalQuestoes` INTEGER NOT NULL, `totalAcertos` INTEGER NOT NULL, `duracaoSegundos` INTEGER NOT NULL, `feedbackAngelica` TEXT, `timestampCriacao` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '77436670c3837de4025e23910ecd7940')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `questoes`");
        db.execSQL("DROP TABLE IF EXISTS `respostas_usuario`");
        db.execSQL("DROP TABLE IF EXISTS `sessao_estudos`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsQuestoes = new HashMap<String, TableInfo.Column>(11);
        _columnsQuestoes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("banca", new TableInfo.Column("banca", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("modalidade", new TableInfo.Column("modalidade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("disciplina", new TableInfo.Column("disciplina", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("topico", new TableInfo.Column("topico", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("area", new TableInfo.Column("area", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("ano", new TableInfo.Column("ano", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("enunciado", new TableInfo.Column("enunciado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("alternativasJson", new TableInfo.Column("alternativasJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("respostaCorreta", new TableInfo.Column("respostaCorreta", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestoes.put("justificativa", new TableInfo.Column("justificativa", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuestoes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuestoes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuestoes = new TableInfo("questoes", _columnsQuestoes, _foreignKeysQuestoes, _indicesQuestoes);
        final TableInfo _existingQuestoes = TableInfo.read(db, "questoes");
        if (!_infoQuestoes.equals(_existingQuestoes)) {
          return new RoomOpenHelper.ValidationResult(false, "questoes(com.concursos.angelica.data.model.Question).\n"
                  + " Expected:\n" + _infoQuestoes + "\n"
                  + " Found:\n" + _existingQuestoes);
        }
        final HashMap<String, TableInfo.Column> _columnsRespostasUsuario = new HashMap<String, TableInfo.Column>(6);
        _columnsRespostasUsuario.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRespostasUsuario.put("questaoId", new TableInfo.Column("questaoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRespostasUsuario.put("respostaEscolhida", new TableInfo.Column("respostaEscolhida", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRespostasUsuario.put("acertou", new TableInfo.Column("acertou", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRespostasUsuario.put("tempoSegundos", new TableInfo.Column("tempoSegundos", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRespostasUsuario.put("timestampResposta", new TableInfo.Column("timestampResposta", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRespostasUsuario = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRespostasUsuario.add(new TableInfo.ForeignKey("questoes", "CASCADE", "NO ACTION", Arrays.asList("questaoId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRespostasUsuario = new HashSet<TableInfo.Index>(1);
        _indicesRespostasUsuario.add(new TableInfo.Index("index_respostas_usuario_questaoId", false, Arrays.asList("questaoId"), Arrays.asList("ASC")));
        final TableInfo _infoRespostasUsuario = new TableInfo("respostas_usuario", _columnsRespostasUsuario, _foreignKeysRespostasUsuario, _indicesRespostasUsuario);
        final TableInfo _existingRespostasUsuario = TableInfo.read(db, "respostas_usuario");
        if (!_infoRespostasUsuario.equals(_existingRespostasUsuario)) {
          return new RoomOpenHelper.ValidationResult(false, "respostas_usuario(com.concursos.angelica.data.model.UserAnswer).\n"
                  + " Expected:\n" + _infoRespostasUsuario + "\n"
                  + " Found:\n" + _existingRespostasUsuario);
        }
        final HashMap<String, TableInfo.Column> _columnsSessaoEstudos = new HashMap<String, TableInfo.Column>(7);
        _columnsSessaoEstudos.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("disciplina", new TableInfo.Column("disciplina", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("totalQuestoes", new TableInfo.Column("totalQuestoes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("totalAcertos", new TableInfo.Column("totalAcertos", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("duracaoSegundos", new TableInfo.Column("duracaoSegundos", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("feedbackAngelica", new TableInfo.Column("feedbackAngelica", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessaoEstudos.put("timestampCriacao", new TableInfo.Column("timestampCriacao", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessaoEstudos = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSessaoEstudos = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSessaoEstudos = new TableInfo("sessao_estudos", _columnsSessaoEstudos, _foreignKeysSessaoEstudos, _indicesSessaoEstudos);
        final TableInfo _existingSessaoEstudos = TableInfo.read(db, "sessao_estudos");
        if (!_infoSessaoEstudos.equals(_existingSessaoEstudos)) {
          return new RoomOpenHelper.ValidationResult(false, "sessao_estudos(com.concursos.angelica.data.model.StudySession).\n"
                  + " Expected:\n" + _infoSessaoEstudos + "\n"
                  + " Found:\n" + _existingSessaoEstudos);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "77436670c3837de4025e23910ecd7940", "4de244aafd337ca48a7ee79370d68c05");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "questoes","respostas_usuario","sessao_estudos");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `questoes`");
      _db.execSQL("DELETE FROM `respostas_usuario`");
      _db.execSQL("DELETE FROM `sessao_estudos`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(QuestionDao.class, QuestionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProgressDao.class, UserProgressDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public QuestionDao questionDao() {
    if (_questionDao != null) {
      return _questionDao;
    } else {
      synchronized(this) {
        if(_questionDao == null) {
          _questionDao = new QuestionDao_Impl(this);
        }
        return _questionDao;
      }
    }
  }

  @Override
  public UserProgressDao userProgressDao() {
    if (_userProgressDao != null) {
      return _userProgressDao;
    } else {
      synchronized(this) {
        if(_userProgressDao == null) {
          _userProgressDao = new UserProgressDao_Impl(this);
        }
        return _userProgressDao;
      }
    }
  }
}
