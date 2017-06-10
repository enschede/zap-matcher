package nl.zzpmatcher.controllers.user;

import nl.zzpmatcher.business.User;
import nl.zzpmatcher.security.SecurityController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {
    @Override
    public Resource<User> toResource(User user) {

        Resource<User> userResource = new Resource<>(user);

        addLogoutLinks(userResource);

        return userResource;
    }

    public static void addLogoutLinks(Resource resource) {
        resource.add(ControllerLinkBuilder.linkTo(SecurityController.class).slash("user").slash("logout").withRel("logout"));
    }

    public Resources<User> toResources(Iterable<User> users) {

        Resources<User> userResources = new Resources<>(users);

        addLogoutLinks(userResources);

        return userResources;
    }

    public static void addLogoutLinks(Resources resources) {
        resources.add(ControllerLinkBuilder.linkTo(SecurityController.class).slash("user").slash("logout").withRel("logout"));
    }

    public Resource<UserEmailaddressOnlyProjection> toEmailAddressOnlyProjection(User createdUser) {

        Resource<UserEmailaddressOnlyProjection> userEmailaddressOnlyProjectionResource =
                new Resource<>(new UserEmailaddressOnlyProjection(createdUser.getEmailaddress()));

        addLoginLink(userEmailaddressOnlyProjectionResource);

        return userEmailaddressOnlyProjectionResource;
    }

    public static void addLoginLink(Resource resource) {
        resource.add(ControllerLinkBuilder.linkTo(SecurityController.class).slash("/public/login").withRel("login"));
    }


}
