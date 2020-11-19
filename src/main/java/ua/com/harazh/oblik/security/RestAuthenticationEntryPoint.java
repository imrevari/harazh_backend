package ua.com.harazh.oblik.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint  extends BasicAuthenticationEntryPoint{

		private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
	
	
	
	 	@Override
	    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
	            throws IOException, ServletException {
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
