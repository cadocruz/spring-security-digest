package br.com.cadocruz.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.util.DigestUtils;

public class RestDigestAuthenticationEntryPoint extends
		DigestAuthenticationEntryPoint {

	private static final Log logger = LogFactory
			.getLog(RestDigestAuthenticationEntryPoint.class);

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// compute a nonce (do not use remote IP address due to proxy farms)
		// format of nonce is:
		// base64(expirationTime + ":" + md5Hex(expirationTime + ":" + key))
		long expiryTime = System.currentTimeMillis()
				+ (super.getNonceValiditySeconds() * 1000);
		String signatureValue = DigestUtils.md5DigestAsHex(new String(
				expiryTime + ":" + super.getKey()).getBytes());
		String nonceValue = expiryTime + ":" + signatureValue;
		String nonceValueBase64 = new String(Base64.encode(nonceValue
				.getBytes()));

		// qop is quality of protection, as defined by RFC 2617.
		// we do not use opaque due to IE violation of RFC 2617 in not
		// representing opaque on subsequent requests in same session.
		String authenticateHeader = "DigestCustom realm=\"" + super.getRealmName()
				+ "\", " + "qop=\"auth\", nonce=\"" + nonceValueBase64 + "\"";

		if (authException instanceof NonceExpiredException) {
			authenticateHeader = authenticateHeader + ", stale=\"true\"";
		}

		if (logger.isDebugEnabled()) {
			logger.debug("WWW-Authenticate header sent to user agent: "
					+ authenticateHeader);
		}

		httpResponse.addHeader("WWW-Authenticate", authenticateHeader);
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());
	}

}
