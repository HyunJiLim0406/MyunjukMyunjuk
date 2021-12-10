package jpa.myunjuk.infra.exception.component;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");
        System.out.println("exception = " + exception);


//        if(exception == null) {
//            setResponse(response, ExceptionCode.UNKNOWN_ERROR);
//        }
//        //잘못된 타입의 토큰인 경우
//        else if(exception.equals(ExceptionCode.WRONG_TYPE_TOKEN.getCode())) {
//            setResponse(response, ExceptionCode.WRONG_TYPE_TOKEN);
//        }
        //토큰 만료된 경우
        if (exception.equals("EXPIRED_TOKEN")) {
            System.out.println("hihi");
            setResponse(response, "EXPIRED_TOKEN");
        }
//        //지원되지 않는 토큰인 경우
//        else if(exception.equals(ExceptionCode.UNSUPPORTED_TOKEN.getCode())) {
//            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
//        }
//        else {
//            setResponse(response, ExceptionCode.ACCESS_DENIED);
//        }
    }

    private void setResponse(HttpServletResponse response, String exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", "EXPIRED_TOKEN");
        responseJson.put("code", 400);

        response.getWriter().print(responseJson);
    }
}