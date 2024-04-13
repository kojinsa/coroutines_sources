package snippets.`0001_test`

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking {
        test()
    }
}

suspend fun test() = coroutineScope {
    launch {
        delay(1000)
        println("world!!")
    }
    println("hello")
}
