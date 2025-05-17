package com.myproject.testingframework.model.database

import android.app.Application
import androidx.room.Room
import com.myproject.testingframework.model.database.Dao.MyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun getMyDatabase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            "MyDatabase1"
        ).build()
    }

    @Provides
    fun provideMyDao(database: MyDatabase): MyDao {
        return database.dao()
    }

}