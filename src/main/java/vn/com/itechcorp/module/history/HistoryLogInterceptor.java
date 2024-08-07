//package vn.com.itechcorp.module.history;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import vn.com.itechcorp.security.JwtTokenUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class HistoryLogInterceptor implements HandlerInterceptor {
//
//    private static final Logger log = LoggerFactory.getLogger(HistoryLogInterceptor.class);
//    @Autowired
//    private HistoryLogService historyLogService;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
////        String token = request.getHeader("Authorization").substring(7);
////        String username = jwtTokenUtil.getUsernameFromToken(token);
//
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String username = authentication.getName();
////
////        String method = request.getMethod();
////        String endpoint = request.getRequestURI();
////        String requestBody = getRequestBody(request);
////        String responseBody = getResponseBody(response);
////
////        historyLogService.log(username, method, endpoint, requestBody, responseBody);
//    }
//
//    private String getRequestBody(HttpServletRequest request) {
//        return "";
//    }
//
//    private String getResponseBody(HttpServletResponse response) {
//        return "";
//    }
//}