package ru.migplus.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import ru.migplus.site.security.AuthenticationFacadeImpl;
import ru.migplus.site.security.services.UserDetailsImpl;

@RestController
public class BaseController {

    @Autowired
    protected AuthenticationFacadeImpl authenticationFacade;

    protected long getAuthUserId() {

        Authentication auth = authenticationFacade.getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            return userDetails.getId();
        }

        return -1;

    }

    protected UserDetailsImpl getCurUserInfo() {

        Authentication auth = authenticationFacade.getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            return userDetails;
        }

        return null;

    }
}
