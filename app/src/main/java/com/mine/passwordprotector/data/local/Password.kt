package com.mine.passwordprotector.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

data class Password(
    val id : Long ,
    val custId : String ,
    val category : String ,
    val serviceTitle : String ,
    val serviceUsername : String ,
    val serviceEncryptedHash : String ,
    val createdAt : String ,
)

@Entity(tableName = "password_list")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val custId : String,
    val category : String,
    val serviceTitle : String,
    val serviceUsername : String,
    val serviceEncryptedHash : String,
    val createdAt : String,
)

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity : PasswordEntity)

    @Query("SELECT * FROM password_list WHERE custId=:custID")
    suspend fun getList(custID : String) : List<PasswordEntity>

}