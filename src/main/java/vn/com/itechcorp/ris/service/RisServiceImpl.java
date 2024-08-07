package vn.com.itechcorp.ris.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.ris.proxy.AuthProxy;
import vn.com.itechcorp.ris.proxy.RisProxy;
import vn.com.itechcorp.ris.dto.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RisServiceImpl implements RisService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${ris.username:hungna}")
    private String username;

    @Value("${ris.password:123456}")
    private String password;

    @Autowired
    private AuthProxy authProxy;

    @Autowired
    private RisProxy risProxy;

    private UserTokenDTOGet token = null;

    private void resetToken() {
        token = authProxy.getToken(new BasicAuth(username, password));
        token.setExpiredDate(new Date(new Date().getTime() + token.getToken().getExpires_in() * 1000));
    }

    private void getToken() {
        if (token == null) {
            resetToken();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 30); // Adding more time for earlier checking token expiration
            Date time = calendar.getTime();
            // Renew token if it's expired
            if (time.after(token.getExpiredDate())) {
                logger.warn("[TOKEN] Token expired {} : {}", token.getToken().getAccess_token(), yyyyMMddHHmmss.format(token.getExpiredDate().getTime()));
                resetToken();
            }
        }
    }

    @Override
    public List<OrderDTOGet> search(OrderFilter filter) {
        try {
            getToken();
            APIListResponse<List<OrderDTOGet>> response = risProxy.searchOrder("Bearer " + token.getToken().getAccess_token(), filter);
            if (response.getBody() == null || response.getBody().isEmpty()) return new ArrayList<>();
            return response.getBody().stream().filter(o -> o.getServices() != null).collect(Collectors.toList());
        } catch (Exception ex) {
            logger.warn("[RIS] Search order failed {}", ex.getMessage());
        }
        resetToken();
        return new ArrayList<>();
    }

    @Override
    public List<OrderDTOGet> getOrders(Collection<Long> ids) {
        try {
            getToken();
            APIListResponse<List<OrderDTOGet>> response = risProxy.getOrders("Bearer " + token.getToken().getAccess_token(), ids);

            if (response.getBody() == null || response.getBody().isEmpty()) return new ArrayList<>();
            return response.getBody();
        } catch (Exception ex) {
            logger.warn("[RIS] Get order failed {}", ex.getMessage());
        }
        resetToken();
        return new ArrayList<>();
    }

}
