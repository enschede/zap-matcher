package nl.zzpmatcher.userlogon.controller

import nl.zzpmatcher.userlogon.business.User
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceAssembler
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.stereotype.Component

@Component
class UserResourceAssembler : ResourceAssembler<User, Resource<User>> {
    override fun toResource(user: User): Resource<User> {

        val userResource = Resource(user)

        addLogoutLinks(userResource)

        return userResource
    }

    fun toResources(users: Iterable<User>): Resources<User> {

        val userResources = Resources(users)

        addLogoutLinks(userResources)

        return userResources
    }

    fun toEmailAddressOnlyProjection(createdUser: User): Resource<UserEmailaddressOnlyProjection> {

        val userEmailaddressOnlyProjectionResource = Resource(UserEmailaddressOnlyProjection(createdUser.emailaddress!!))

        addLoginLink(userEmailaddressOnlyProjectionResource)

        return userEmailaddressOnlyProjectionResource
    }

    companion object {

        fun addLogoutLinks(resource: Resource<*>) {
            resource.add(ControllerLinkBuilder.linkTo(SecurityController::class.java).slash("controller").slash("logout").withRel("logout"))
        }

        fun addLogoutLinks(resources: Resources<*>) {
            resources.add(ControllerLinkBuilder.linkTo(SecurityController::class.java).slash("controller").slash("logout").withRel("logout"))
        }

        fun addLoginLink(resource: Resource<*>) {
            resource.add(ControllerLinkBuilder.linkTo(SecurityController::class.java).slash("/public/login").withRel("login"))
        }
    }


}
