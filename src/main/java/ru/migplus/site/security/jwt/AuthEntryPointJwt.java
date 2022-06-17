package ru.migplus.site.security.jwt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.migplus.site.exceptions.Error;
import ru.migplus.site.payload.response.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.warn("Attempt unauthenticated access");
        response.setContentType("application/json;charset=UTF-8");
        Error error = new Error(new Date(), 401, "UNAUTHENTICATED", "Доступ запрещён", "");
        ErrorResponse errorResponse = new ErrorResponse(false, error);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
        String jsonString = gson.toJson(errorResponse);
        response.getWriter().write(String.valueOf(jsonString));
    }
}
