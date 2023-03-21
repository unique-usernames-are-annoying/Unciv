package com.unciv.logic.multiplayer.storage

import com.unciv.logic.multiplayer.api.Api
import java.util.*

/**
 * Transition helper that emulates file storage behavior using the API v2
 */
class ApiV2FileStorageEmulator(private val api: Api): FileStorage {

    override suspend fun saveGameData(gameId: String, data: String) {
        val uuid = UUID.fromString(gameId.lowercase())
        api.games.upload(uuid, data)
    }

    override suspend fun savePreviewData(gameId: String, data: String) {
        // Not implemented for this API
        throw NotImplementedError("Outdated API")
    }

    override suspend fun loadGameData(gameId: String): String {
        val uuid = UUID.fromString(gameId.lowercase())
        return api.games.get(uuid).gameData
    }

    override suspend fun loadPreviewData(gameId: String): String {
        // Not implemented for this API
        throw NotImplementedError("Outdated API")
    }

    override suspend fun getFileMetaData(fileName: String): FileMetaData {
        TODO("Not implemented for this API")
    }

    override suspend fun deleteGameData(gameId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePreviewData(gameId: String) {
        // Not implemented for this API
        throw NotImplementedError("Outdated API")
    }

    override suspend fun authenticate(userId: String, password: String): Boolean {
        return api.auth.loginOnly(userId, password)
    }

    override suspend fun setPassword(newPassword: String): Boolean {
        api.accounts.setPassword("", newPassword)
        TODO("Not yet implemented")
    }

}

/**
 * Workaround to "just get" the file storage handler and the API, but without initializing
 *
 * TODO: This wrapper should be replaced by better file storage initialization handling.
 *
 * This object keeps references which are populated during program startup at runtime.
 */
object ApiV2FileStorageWrapper {
    var api: Api? = null
    var storage: ApiV2FileStorageEmulator? = null
}