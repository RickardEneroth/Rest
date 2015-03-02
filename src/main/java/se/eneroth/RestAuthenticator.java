package se.eneroth;

import java.util.Map;

import java.util.HashMap;
import java.util.UUID;
import java.security.GeneralSecurityException;
import javax.security.auth.login.LoginException;

public final class RestAuthenticator {

    private static RestAuthenticator authenticator = null;
    private final Map<String, String> usersStorage = new HashMap();
    private final Map<String, String> apiKeyStorage = new HashMap();
    private final Map<String, String> sessionTokensStorage = new HashMap();

    private RestAuthenticator() {
        usersStorage.put("username1", "passwordForUser1");
        usersStorage.put("username2", "passwordForUser2");
        usersStorage.put("username3", "passwordForUser3");

        apiKeyStorage.put("f80ebc87-ad5c-4b29-9366-5359768df5a1", "username1");
        apiKeyStorage.put("3b91cab8-926f-49b6-ba00-920bcf934c2a", "username2");
    }

    public static RestAuthenticator getInstance() {
        if (authenticator == null) {
            authenticator = new RestAuthenticator();
        }
        return authenticator;
    }

    public String login( String serviceKey, String username, String password ) throws LoginException {
        if (apiKeyStorage.containsKey(serviceKey)) {
            String usernameMatch = apiKeyStorage.get(serviceKey);

            if (usernameMatch.equals(username) && usersStorage.containsKey(username)) {
                String passwordMatch = usersStorage.get(username);

                if (passwordMatch.equals(password)) {
                    String authToken = UUID.randomUUID().toString();
                    sessionTokensStorage.put(authToken, username);

                    return authToken;
                }
            }
        }

        throw new LoginException( "Don't Come Here Again!" );
    }

    public boolean isAuthTokenValid( String serviceKey, String authToken ) {
        if ( isServiceKeyValid( serviceKey ) ) {
            String usernameMatch1 = apiKeyStorage.get( serviceKey );

            if ( sessionTokensStorage.containsKey( authToken ) ) {
                String usernameMatch2 = sessionTokensStorage.get( authToken );

                if ( usernameMatch1.equals( usernameMatch2 ) ) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isServiceKeyValid( String serviceKey ) {
        return apiKeyStorage.containsKey( serviceKey );
    }

    public void logout( String serviceKey, String authToken ) throws GeneralSecurityException {
        if ( apiKeyStorage.containsKey( serviceKey ) ) {
            String usernameMatch1 = apiKeyStorage.get( serviceKey );

            if ( sessionTokensStorage.containsKey( authToken ) ) {
                String usernameMatch2 = sessionTokensStorage.get( authToken );

                if ( usernameMatch1.equals( usernameMatch2 ) ) {
                    sessionTokensStorage.remove( authToken );
                    return;
                }
            }
        }

        throw new GeneralSecurityException( "Invalid service key and authorization token match." );
    }
}
