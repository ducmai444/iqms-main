package vn.com.itechcorp.ris.service;

import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.dto.OrderFilter;

import java.util.Collection;
import java.util.List;

public interface RisService {

    List<OrderDTOGet> search(OrderFilter filter);

    List<OrderDTOGet> getOrders(Collection<Long> orders);

}
