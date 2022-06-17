package ru.migplus.site.exceptions;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.migplus.site.payload.response.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle
            (HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
            throws IOException, ServletException {
        log.warn("Attempt unauthorized access");
        response.setContentType("application/json;charset=UTF-8");
        Error error = new Error(new Date(), 403, "UNAUTHORIZED", "Доступ запрещён", "");
        ErrorResponse errorResponse = new ErrorResponse(false, error);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
        String jsonString = gson.toJson(errorResponse);
        response.getWriter().write(String.valueOf(jsonString));
    }
}
