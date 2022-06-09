package fr.loual.spsecjwt.security;

public class JWTUtil {

    public static final String SECRET = "MySecretKey1234";
    public static final String AUTH_HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";
    public static final long EXPIRE_ACCESS_TOKEN = 60000;
    public static final long EXPIRE_REFRESH_TOKEN = 900000;

}
