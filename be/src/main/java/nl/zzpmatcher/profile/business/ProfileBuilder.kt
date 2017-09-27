package nl.zzpmatcher.profile.business

import java.util.*
import kotlin.streams.toList

object ProfileBuilder {

    fun build(updateProfileCommand: UpdateProfileCommand): Profile {

        val profile = Profile(id = updateProfileCommand.username)

        val tagList = updateProfileCommand.tags?.map { tag -> Tag(id = tag, profile = profile) }?.toList()

        profile.tags = tagList

        return profile
    }
}
