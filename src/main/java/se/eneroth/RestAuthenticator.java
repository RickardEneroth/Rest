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

    public String login(String apiKey, String username, String password) throws LoginException {
        if (apiKeyStorage.containsKey(apiKey)) {
            String usernameMatch = apiKeyStorage.get(apiKey);

            if (usernameMatch.equals(username) && usersStorage.containsKey(username)) {
                String passwordMatch = usersStorage.get(username);

                if (passwordMatch.equals(password)) {
                    String sessionToken = UUID.randomUUID().toString();
                    sessionTokensStorage.put(sessionToken, username);

                    return sessionToken;
                }
            }
        }

        throw new LoginException("Don't Come Here Again!");
    }

    public boolean isAuthTokenValid(String apiKey, String sessionToken) {
        if (isApiKeyValid(apiKey)) {
            String usernameMatch1 = apiKeyStorage.get(apiKey);

            if (sessionTokensStorage.containsKey(sessionToken)) {
                String usernameMatch2 = sessionTokensStorage.get(sessionToken);

                if (usernameMatch1.equals(usernameMatch2)) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean isApiKeyValid(String apiKey) {
        return apiKeyStorage.containsKey(apiKey);
    }

    public void logout(String apiKey, String sessionToken) throws GeneralSecurityException {
        if (apiKeyStorage.containsKey(apiKey)) {
            String usernameMatch1 = apiKeyStorage.get(apiKey);

            if (sessionTokensStorage.containsKey(sessionToken)) {
                String usernameMatch2 = sessionTokensStorage.get(sessionToken);

                if (usernameMatch1.equals(usernameMatch2)) {
                    sessionTokensStorage.remove(sessionToken);
                    return;
                }
            }
        }

        throw new GeneralSecurityException("Invalid service key and authorization token match.");
    }
}
