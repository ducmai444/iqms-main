//package vn.com.itechcorp.module.history;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class HistoryLogService {
//
//    @Autowired
//    private HistoryLogRepository historyLogRepository;
//
//    public void log(String username, String method, String endpoint, String requestBody, String responseBody) {
//        HistoryLog historyLog = new HistoryLog();
//
//        historyLog.setUsername(username);
//        historyLog.setMethod(method);
//        historyLog.setEndpoint(endpoint);
//        historyLog.setRequestBody(requestBody);
//        historyLog.setResponseBody(responseBody);
//
//        historyLogRepository.save(historyLog);
//    }
//}
