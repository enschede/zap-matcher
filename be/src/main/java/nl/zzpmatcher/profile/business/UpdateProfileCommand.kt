package nl.zzpmatcher.profile.business

class UpdateProfileCommand {
    var username: String? = null
    var tags: Array<String>? = null

    constructor() {}

    private constructor(username: String, tags: Array<String>) {
        this.username = username
        this.tags = tags
    }

    companion object {

        fun of(username: String, tags: Array<String>): UpdateProfileCommand {
            return UpdateProfileCommand(username, tags)
        }
    }
}
