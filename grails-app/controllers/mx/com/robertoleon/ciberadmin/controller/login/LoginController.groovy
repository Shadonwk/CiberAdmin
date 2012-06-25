package mx.com.robertoleon.ciberadmin.controller.login

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import grails.converters.JSON
import org.springframework.web.servlet.ModelAndView
import org.springframework.security.web.WebAttributes

class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

/**
 * Dependency injection for the springSecurityService.
 */
    def springSecurityService

/**
 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
 */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

/**
 * Show the login page.
 */
    def auth = {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect uri: config.successHandler.defaultTargetUrl

            return
        }
        String view = 'IniciarSesion'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                rememberMeParameter: config.rememberMe.parameter]
    }

/**
 * The redirect action for Ajax requests.
 */
    def authAjax = {
        println "location aca" + SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

/**
 * Show denied page.
 */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SecurityContextHolder.context?.authentication)) {
// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

/**
 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
 */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'IniciarSesion', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

/**
 * Callback after a failed login. Redirects to the auth page with a warning message.
 */
    def authfail = {

        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = "Su cuenta ha expirado"
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = "Su contraseña ha expirado"
            }
            else if (exception instanceof DisabledException) {
                msg = "Su cienta esta inhabilitada"
            }
            else if (exception instanceof LockedException) {
                msg = "Su cuenta esta bloqueada"
            }
            else {
                msg = "Error Usuario y/o Contraseña incorrectos"
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

/**
 * The Ajax success redirect url.
 */
    def ajaxSuccess = {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

/**
 * The Ajax denied redirect url.
 */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }

    def errorAcceso = {
        def model = [:]
        return new ModelAndView("/login/Recuperar", model)
    }
}
