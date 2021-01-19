package hu.daniel.mvilogindemo

import kotlinx.coroutines.delay

interface IRepository {
    suspend fun validateLoginCredentials(name: String, password: String)
    suspend fun login(name: String, password: String): User
}

class Repository : IRepository {

    override suspend fun validateLoginCredentials(name: String, password: String) {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
        if (name.isBlank() ||
                password.isBlank() ||
                !password.matches(passwordRegex)) {
            throw LoginErrorException()
        }
    }

    override suspend fun login(name: String, password: String): User {
        //API call, business logic, data persist
        delay(3000) //for app intro
        return User(name, password)
    }
}