package com.mine.passwordprotector.data.repository

import com.mine.passwordprotector.data.local.PasswordDao
import com.mine.passwordprotector.data.local.PasswordEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PasswordRepository @Inject constructor(
    private val dao: PasswordDao , custID : String
) {
    val passwords: Flow<List<PasswordEntity>> = dao.getList(custID)

    suspend fun insert(password: PasswordEntity) {
        dao.insert(password)
    }
}

