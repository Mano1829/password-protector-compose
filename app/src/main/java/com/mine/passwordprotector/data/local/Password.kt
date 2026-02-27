package com.mine.passwordprotector.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class Password(
    val id : Long ,
    val custId : String ,
    val category : String ,
    val serviceTitle : String ,
    val serviceUsername : String ,
    val serviceEncryptedHash : String ,
    val createdAt : String ,
)

fun passwordPrint(password: Password) : String {
    return "Password :: ID[${password.id}] , CUSTID[${password.custId}] , Category[${password.category}] " +
            ", ServiceTitle[${password.serviceTitle}] , ServiceUsername[${password.serviceUsername}] " +
            ", ServicePassword[${password.serviceEncryptedHash}] , CreatedAt[${password.createdAt}]"
}

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
    fun getList(custID : String) : Flow<List<PasswordEntity>>

}