package xyz.ixidi.jumi.bot

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import xyz.ixidi.jumi.riotapi.startLeagueClientApiFromProcess

 fun main() {
    runBlocking {
        JumiBot().start()

        while (true) {
            delay(10)
        }
    }
}