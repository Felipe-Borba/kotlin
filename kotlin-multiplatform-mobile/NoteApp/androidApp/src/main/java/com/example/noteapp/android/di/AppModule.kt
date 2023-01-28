package com.example.noteapp.android.di

import android.app.Application
import com.example.noteapp.data.local.DatabaseDriverFactory
import com.example.noteapp.data.note.SqlDelightNoteDataSource
import com.example.noteapp.database.NoteDatabase
import com.example.noteapp.domain.note.NoteDateSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver):  NoteDateSource{
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}