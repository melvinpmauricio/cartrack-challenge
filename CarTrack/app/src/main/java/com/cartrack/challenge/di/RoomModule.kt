package com.cartrack.challenge.di

import android.content.Context
import androidx.room.Room
import com.cartrack.challenge.Application
import com.cartrack.challenge.models.CarTrackDatabase
import com.cartrack.challenge.models.dao.CustomerDao
import com.cartrack.challenge.models.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): CarTrackDatabase {
        return Room.databaseBuilder(
            context,
            CarTrackDatabase::class.java,
            CarTrackDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: CarTrackDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideCustomerDao(db: CarTrackDatabase): CustomerDao {
        return db.customerDao()
    }
}