package api.giybat.uz.util;
import api.giybat.uz.dto.JwtDTO;
import api.giybat.uz.enums.ProfileRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(String username ,Long id, List<ProfileRoles> roles) {
        String strRoles = roles.stream().map(Enum::name).collect(Collectors.joining(","));
        Map<String,String> claims = new HashMap<>();
        claims.put("roles",strRoles);
        claims.put("id",String.valueOf(id));
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();

    }
    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Long id = Long.valueOf((String) claims.get("id"));
        String strRoles = (String) claims.get("roles");
        List<ProfileRoles> roles = Arrays.stream(strRoles.split(","))
                .map(ProfileRoles::valueOf)
                .toList();
        return new JwtDTO(id,username,roles);

    }

    public static String encode(Long id) {
        return Jwts
                .builder()
                .subject(id.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60*60*1000)))
                .signWith(getSignInKey())
                .compact();
    }
    public static Long decodeRegVerToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }


    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
