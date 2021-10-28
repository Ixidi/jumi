package xyz.ixidi.jumi.bot

import kotlinx.coroutines.delay
import mu.KotlinLogging
import xyz.ixidi.jumi.bot.autojoin.AutoJoinFeature
import xyz.ixidi.jumi.bot.autojoin.AutoJoinFeatureConfig
import xyz.ixidi.jumi.bot.feature.FeatureManager
import xyz.ixidi.jumi.riotapi.LeagueClientApi
import xyz.ixidi.jumi.riotapi.startLeagueClientApiFromProcess
import xyz.ixidi.jumi.riotapi.util.ProcessNotFoundException

internal val logger = KotlinLogging.logger {  }

class JumiBotException(message: String) : Exception(message)

class JumiBot {

    private lateinit var lcu: LeagueClientApi
    private lateinit var featureManager: FeatureManager

    suspend fun start() {
        lcu = getLeagueProcess(4)
        featureManager = FeatureManager(lcu)

        featureManager.addAndEnable(AutoJoinFeature())
    }

    private suspend fun getLeagueProcess(maxTries: Int): LeagueClientApi {
        var lcu: LeagueClientApi? = null
        var tries = 0
        do {
            tries++
            try {
                lcu = startLeagueClientApiFromProcess()
            } catch (ex: ProcessNotFoundException) {
                logger.warn { "League of Legends is not open, trying again in 5 second. ($tries/$maxTries)" }
            }

            if (tries >= maxTries) throw JumiBotException("League of Legends is not open.")
            delay(5000)
        } while (lcu == null)

        return lcu
    }

}