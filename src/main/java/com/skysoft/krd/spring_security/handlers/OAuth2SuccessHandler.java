package com.skysoft.krd.spring_security.handlers;

import com.skysoft.krd.spring_security.entities.User;
import com.skysoft.krd.spring_security.services.JwtServiceImpl;
import com.skysoft.krd.spring_security.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtServiceImpl jwtServiceImpl;
    @Value("${deploy.env}")
    private String deployEnv;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
      //  super.onAuthenticationSuccess(request, response, authentication);


        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) token.getPrincipal();
        String email = oauthUser.getAttribute("email");
        log.info("OAuthUser email: {}", email);

        User user = userService.getUserByEmail(email);
        if (user == null) {
            User newUser = User.builder()
                    .email(email)
                    .build();
            user = userService.save(newUser);
        }

        log.info("user: {}", user);

        String accessToken = jwtServiceImpl.generateAccessToken(user);
        String refreshToken = jwtServiceImpl.generateRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure("PROD".equals(deployEnv));
        response.addCookie(cookie);

        String frontendUrl = "http://localhost:9191/home.html?token=" + accessToken;

            log.info("Redirecting to: {}", frontendUrl);



        getRedirectStrategy().sendRedirect(request, response, frontendUrl);

    }

}
