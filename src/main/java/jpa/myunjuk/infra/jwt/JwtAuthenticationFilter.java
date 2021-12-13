package jpa.myunjuk.infra.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jpa.myunjuk.infra.exception.ExpiredJwtTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 JWT 받아오기
        String token = jwtTokenProvider.resolveToken(request);

        //유효한 토큰인지 확인
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            //토큰이 유효하면 토큰으로부터 유저 정보 반환
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        if (token != null) {
//            jwtTokenProvider.validateToken(token);
//            boolean flag = true;
//            try{
//                flag = jwtTokenProvider.validateToken(token);
//            } catch (ExpiredJwtException e){
//                System.out.println("expired token");
//            }
//            if(flag) {
//                //토큰이 유효하면 토큰으로부터 유저 정보 반환
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                //SecurityContext에 Authentication 객체를 저장
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
        chain.doFilter(request, response);
    }
}