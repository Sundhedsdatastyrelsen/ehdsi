package dk.sundhedsdatastyrelsen.ncpeh.filters;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.UUID;
/**
* Assigns a requestId to incoming requests for easier tracking in the logs.
*/
@Component
public class RequestIdFilter extends OncePerRequestFilter {
 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
 try {
  MDC.put("requestId", UUID.randomUUID().toString());
  filterChain.doFilter(request, response);
 } finally {
  MDC.remove("requestId");
 }
 }
}
