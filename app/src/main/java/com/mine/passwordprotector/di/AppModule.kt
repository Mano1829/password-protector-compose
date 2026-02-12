package com.mine.passwordprotector.di

import android.content.Context
import androidx.room.Room
import com.mine.passwordprotector.data.local.AppDatabase
import com.mine.passwordprotector.data.local.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context ,
            AppDatabase::class.java ,
            "password_manager"
        ).build()
    }

    @Provides
    fun providePasswordDao(db: AppDatabase) = db.passwordDao()

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context) = SessionManager(context)

}

