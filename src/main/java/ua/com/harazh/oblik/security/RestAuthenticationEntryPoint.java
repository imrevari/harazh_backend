package ua.com.harazh.oblik.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint  extends BasicAuthenticationEntryPoint{

		private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

		private Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
	
	 	@Override
	    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
	            throws IOException, ServletException {
			logger.error(request.getRemoteAddr());
			logger.error(request.getHeader("X-FORWARDED-FOR"));
			logger.error(request.getRemoteHost());
			logger.error(LocalDateTime.now().toString());

	        response.addHeader("WWW-Authenticate", "Auth realm=\"" + getRealmName() + "\"");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	        String json = objectMapper.writeValueAsString("{\"error\":\"you're unauthorized\"}");
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	    }
	 
	 
	 	@Override
	    public void afterPropertiesSet() throws Exception {
	        setRealmName("Candy land");
	        super.afterPropertiesSet();
	    }
	
	
	
}
