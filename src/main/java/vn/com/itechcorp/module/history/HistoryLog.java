//package vn.com.itechcorp.module.history;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "history_log")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class HistoryLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "user_name", nullable = false)
//    private String username;
//
//    @Column(name = "method", nullable = false)
//    private String method;
//
//    @Column(name = "endpoint", nullable = false)
//    private String endpoint;
//
//    @Column(name = "request_body")
//    private String requestBody;
//
//    @Column(name = "response_body")
//    private String responseBody;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//}