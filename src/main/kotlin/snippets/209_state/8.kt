package f_209_state.s_8

import kotlinx.coroutines.*
import java.util.concurrent.Executors

class UserDownloader(
    private val api: NetworkService
) {
    private val users = mutableListOf<User>()
    private val dispatcher = Dispatchers.IO
        .limitedParallelism(1)

    suspend fun downloaded(): List<User> =
        withContext(dispatcher) {
            users.toList()
        }

    suspend fun fetchUser(id: Int) = withContext(dispatcher) {
        val newUser = api.fetchUser(id)
        users += newUser
    }
}


class User(val name: String)

interface NetworkService {
    suspend fun fetchUser(id: Int): User
}

class FakeNetworkService : NetworkService {
    override suspend fun fetchUser(id: Int): User {
        delay(2)
        return User("User$id")
    }
}

suspend fun main() {
    val downloader = UserDownloader(FakeNetworkService())
    coroutineScope {
        repeat(1_000_000) {
            launch {
                downloader.fetchUser(it)
            }
        }
    }
    print(downloader.downloaded().size) // ~1000000
}
