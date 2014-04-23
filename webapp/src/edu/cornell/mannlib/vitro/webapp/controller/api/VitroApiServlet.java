/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.controller.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vitro.webapp.auth.policy.PolicyHelper;
import edu.cornell.mannlib.vitro.webapp.auth.requestedAction.Actions;
import edu.cornell.mannlib.vitro.webapp.beans.UserAccount;
import edu.cornell.mannlib.vitro.webapp.controller.authenticate.Authenticator;

/**
 * The base class for Vitro servlets that implement the API.
 * 
 * We don't want the API servlets to extend VitroHttpServlet, because we want
 * the following behavior:
 * <ul>
 * <li>No redirecting to the login page if not authorized</li>
 * <li>No redirecting to the home page on insufficient authorization</li>
 * <li>GET and POST requests are not necessarily equivalent.</li>
 * </ul>
 */
public class VitroApiServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(VitroApiServlet.class);

	/**
	 * If they have not provided an email/password combo that will authorize
	 * them for this action, throw an AuthException.
	 */
	protected void confirmAuthorization(HttpServletRequest req,
			Actions requiredActions) throws AuthException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		Authenticator auth = Authenticator.getInstance(req);
		UserAccount account = auth.getAccountForInternalAuth(email);
		if (!auth.isCurrentPassword(account, password)) {
			log.debug("Invalid: '" + email + "'/'" + password + "'");
			throw new AuthException("email/password combination is not valid");
		}

		if (!PolicyHelper.isAuthorizedForActions(req, email, password,
				requiredActions)) {
			log.debug("Not authorized: '" + email + "'");
			throw new AuthException("Account is not authorized");
		}

		log.debug("Authorized for '" + email + "'");
	}

	protected void sendShortResponse(int statusCode, String message,
			HttpServletResponse resp) throws IOException {
		resp.setStatus(statusCode);
		PrintWriter writer = resp.getWriter();
		writer.println("<H1>" + statusCode + " " + message + "</H1>");
	}

	protected void sendShortResponse(int statusCode, String message, Throwable e,
			HttpServletResponse resp) throws IOException {
		sendShortResponse(statusCode, message, resp);
		PrintWriter writer = resp.getWriter();
		writer.println("<pre>");
		e.printStackTrace(writer);
		writer.println("</pre>");
	}

	// ----------------------------------------------------------------------
	// Helper classes
	// ----------------------------------------------------------------------

	protected static class AuthException extends Exception {
		protected AuthException(String message) {
			super(message);
		}
	}

	protected static class BadParameterException extends Exception {
		protected BadParameterException(String message) {
			super(message);
		}
	}
	
}