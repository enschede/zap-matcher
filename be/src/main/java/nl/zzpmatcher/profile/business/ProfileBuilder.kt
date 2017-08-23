package nl.zzpmatcher.profile.business

import java.util.*
import kotlin.streams.toList

object ProfileBuilder {

    fun build(updateProfileCommand: UpdateProfileCommand): Profile {

        val profile = Profile()
        profile.id = updateProfileCommand.username

        val tagList = Arrays.stream(updateProfileCommand.tags).map { tag -> Tag.of(profile, tag) }.toList()

        profile.tags = tagList

        return profile
    }
}
