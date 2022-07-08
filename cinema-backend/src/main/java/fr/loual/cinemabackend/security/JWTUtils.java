package fr.loual.cinemabackend.security;

public class JWTUtils {
    public static final String HEADER = "Authorization";
    public static final String  SECRET_KEY = "Alpo087IsutKal";
    public static final String  PREFIX = "Bearer ";
    public static final int  EXPIRE_ACCESS_TOKEN = 900000;
    public static final int  EXPIRE_REFRESH_TOKEN = 3600000;
}
