package com.concursos.angelica.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.concursos.angelica.data.dao.QuestionDao
import com.concursos.angelica.data.dao.UserProgressDao
import com.concursos.angelica.data.model.Question
import com.concursos.angelica.data.model.StudySession
import com.concursos.angelica.data.model.UserAnswer

@Database(
    entities = [Question::class, UserAnswer::class, StudySession::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "concursos_angelica_v2.db"
                )
                .createFromAsset("concursos.db")
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
