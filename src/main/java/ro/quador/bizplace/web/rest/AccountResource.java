package ro.quador.bizplace.web.rest;

import com.codahale.metrics.annotation.Timed;

import ro.quador.bizplace.domain.Authority;
import ro.quador.bizplace.domain.Companie;
import ro.quador.bizplace.domain.PersistentToken;
import ro.quador.bizplace.domain.User;
import ro.quador.bizplace.repository.CompanieRepository;
import ro.quador.bizplace.repository.PersistentTokenRepository;
import ro.quador.bizplace.repository.UserRepository;
import ro.quador.bizplace.security.SecurityUtils;
import ro.quador.bizplace.service.CompanieService;
import ro.quador.bizplace.service.MailService;
import ro.quador.bizplace.service.UserService;
import ro.quador.bizplace.service.util.RandomUtil;
import ro.quador.bizplace.web.rest.dto.CompanieDTO;
import ro.quador.bizplace.web.rest.dto.UserDTO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.context.SpringWebContext;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/app")
public class AccountResource {

	private final Logger log = LoggerFactory.getLogger(AccountResource.class);

	@Inject
	private ServletContext servletContext;

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private SpringTemplateEngine templateEngine;

	@Inject
	private UserRepository userRepository;

	@Inject
	private UserService userService;

	@Inject
	private CompanieService companieService;

	@Inject
	private PersistentTokenRepository persistentTokenRepository;

	@Inject
	private MailService mailService;

	/**
	 * POST /rest/register -> register the user.
	 */
	@RequestMapping(value = "/rest/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> registerAccount(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {

		log.debug("User DTO is {}", userDTO.getCompany());

		User user = userRepository.findOne(userDTO.getLogin());
		if (user != null) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} else {
			user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(), userDTO.getFirstName(), userDTO.getLastName(),
					userDTO.getEmail().toLowerCase(), userDTO.getLangKey());

			// create the company
			Companie newCompanie = companieService.createCompanie(userDTO.getCompany(), userDTO.getLogin());

			final Locale locale = Locale.forLanguageTag(user.getLangKey());
			String content = createHtmlContentFromTemplate(user, locale, request, response);
			mailService.sendActivationEmail(user.getEmail(), content, locale);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}

	/**
	 * GET /rest/activate -> activate the registered user.
	 */
	@RequestMapping(value = "/rest/activate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
		User user = userService.activateRegistration(key);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
	}

	/**
	 * GET /rest/authenticate -> check if the user is authenticated, and return
	 * its login.
	 */
	@RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	/**
	 * GET /rest/account -> get the current user.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<UserDTO> getAccount() {
		User user = userService.getUserWithAuthorities();
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<String> roles = new ArrayList<>();
		for (Authority authority : user.getAuthorities()) {
			roles.add(authority.getName());
		}

		// fac companieDTO
		Companie companie = user.getCompanies().toArray(new Companie[] {})[0];

		return new ResponseEntity<>(new UserDTO(user.getLogin(), null, user.getFirstName(), user.getLastName(), user.getEmail(), user.getLangKey(),
				roles, new CompanieDTO(companie)), HttpStatus.OK);
	}

	/**
	 * POST /rest/account -> update the current user information.
	 */
	@RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void saveAccount(@RequestBody UserDTO userDTO) {
		userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getCompany());
	}

	/**
	 * POST /rest/change_password -> changes the current user's password
	 */
	@RequestMapping(value = "/rest/account/change_password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> changePassword(@RequestBody String password) {
		if (StringUtils.isEmpty(password)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		userService.changePassword(password);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * POST /rest/pwd_reset_email -> changes the current user's password
	 */
	@RequestMapping(value = "/rest/pwdreset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> pwdResetEmail(@RequestBody String email, HttpServletRequest request, HttpServletResponse response) {

		log.debug("!!!!!!!!!! pwdResetEmail !!!!!!!!!");

		if (StringUtils.isEmpty(email)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		// caut userul dupa email.

		// daca e gasit, atunci, trimit email si return httpStatus.OK

		// daca nu, atunci nu trimit email si status... altceva ca sa apara
		// eroare pe pagina aia de pwdReset.html

		User user = userRepository.getUserByEmail(email);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		//salvez cheie random
		user.setPwdResetKey(RandomUtil.generateRandomKey());
		userRepository.save(user);

		final Locale locale = Locale.forLanguageTag(user.getLangKey());
		String content = createHtmlContentFromTemplateForPwdReset(user, locale, request, response);
		mailService.sendEmail(email, "Resetare parola cont BizPlace", content, false, true);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * GET /rest/account/sessions -> get the current open sessions.
	 */
	@RequestMapping(value = "/rest/account/sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
		User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(persistentTokenRepository.findByUser(user), HttpStatus.OK);
	}

	/**
	 * DELETE /rest/account/sessions?series={series} -> invalidate an existing
	 * session.
	 * 
	 * - You can only delete your own sessions, not any other user's session -
	 * If you delete one of your existing sessions, and that you are currently
	 * logged in on that session, you will still be able to use that session,
	 * until you quit your browser: it does not work in real time (there is no
	 * API for that), it only removes the "remember me" cookie - This is also
	 * true if you invalidate your current session: you will still be able to
	 * use it until you close your browser or that the session times out. But
	 * automatic login (the "remember me" cookie) will not work anymore. There
	 * is an API to invalidate the current session, but there is no API to check
	 * which session uses which cookie.
	 */
	@RequestMapping(value = "/rest/account/sessions/{series}", method = RequestMethod.DELETE)
	@Timed
	public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
		String decodedSeries = URLDecoder.decode(series, "UTF-8");
		User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
		List<PersistentToken> persistentTokens = persistentTokenRepository.findByUser(user);
		for (PersistentToken persistentToken : persistentTokens) {
			if (StringUtils.equals(persistentToken.getSeries(), decodedSeries)) {
				persistentTokenRepository.delete(decodedSeries);
			}
		}
	}

	private String createHtmlContentFromTemplate(final User user, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("user", user);
		variables.put("baseUrl", request.getScheme() + "://" + // "http" + "://
				request.getServerName() + // "myhost"
				":" + request.getServerPort());
		IWebContext context = new SpringWebContext(request, response, servletContext, locale, variables, applicationContext);
		return templateEngine.process("activationEmail", context);
	}

	private String createHtmlContentFromTemplateForPwdReset(final User user, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("user", user);
		variables.put("baseUrl", request.getScheme() + "://" + // "http" + "://
				request.getServerName() + // "myhost"
				":" + request.getServerPort());
		IWebContext context = new SpringWebContext(request, response, servletContext, locale, variables, applicationContext);
		return templateEngine.process("pwdResetEmail", context);
	}
}
