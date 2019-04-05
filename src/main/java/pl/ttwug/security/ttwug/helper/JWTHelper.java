package pl.ttwug.security.ttwug.helper;

import com.auth0.jwt.JWT;

import static pl.ttwug.security.ttwug.config.GeneralConstants.TOKEN_PREFIX;

public class JWTHelper {
    public static String getSubject(String token) {
        return JWT.decode(token.replace(TOKEN_PREFIX, "")).getSubject();
    }
}
