package me.kingtux.tmvc.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * This is just a wrapper(Honestly it doesnt need to get wrapped)
 */
public class Password {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
