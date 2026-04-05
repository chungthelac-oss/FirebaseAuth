package com.example.firebaseauth.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModel {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext application: Context): AppDatabase{
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "dbNews"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideTinTucDao(db: AppDatabase): TinTucDao{
        return db.tintucDao
    }
    @Provides
    fun provideTheLoaiDao(db: AppDatabase): TheLoaiDao{
        return db.theloaiDao
    }
}