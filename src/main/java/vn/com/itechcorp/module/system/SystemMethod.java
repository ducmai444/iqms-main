package vn.com.itechcorp.module.system;

import org.springframework.stereotype.Service;

@Service
public class SystemMethod {

    public String getCurrentTime(){
        Long nanoSecond = System.nanoTime();
        return nanoSecond.toString();
    }
}
