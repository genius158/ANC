package com.yan.anc.module.android.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.yan.anc.repository.ApiResponse

@Dao
abstract class AndroidDataDao {
    @Insert(onConflict = REPLACE)
    abstract fun insert(androidData: List<AndroidData>)

    @Query("SELECT * FROM AndroidData ")
    abstract fun load(): LiveData<List<AndroidData>>

    fun loadAndroidData(): LiveData<ApiResponse<List<AndroidData>>>? {
        return Transformations.map<List<AndroidData>, ApiResponse<List<AndroidData>>>(load()) { repositories ->
            return@map ApiResponse(repositories)
        }
    }
}