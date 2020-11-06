package edu.ktu.roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import io.reactivex.Completable

@Dao
interface UserDao
{
    @Insert
    fun insert(user:User) : Completable

    @Delete
    fun delete(user:User) : Completable

    @Query("SELECT * FROM user")
    fun getAllUsers() : Single<List<User>>

    @Query("SELECT * FROM user WHERE uid IN (:userId)")
    fun getUserById(userId:Int) : Single<User>
}