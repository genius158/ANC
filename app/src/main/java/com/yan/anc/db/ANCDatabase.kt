package com.yan.anc.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.yan.anc.module.android.repository.AndroidData
import com.yan.anc.module.android.repository.AndroidDataDao

@Database(entities = arrayOf(AndroidData::class), version = 1)
abstract class ANCDatabase : RoomDatabase() {

    abstract fun androidDataDao(): AndroidDataDao
}