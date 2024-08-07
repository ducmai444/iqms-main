package vn.com.itechcorp.module.mwl.service;

import vn.com.itechcorp.module.mwl.dto.MwlDTO;
import vn.com.itechcorp.module.mwl.dto.MwlFilter;
import java.util.List;

public interface MwlService {

    List<MwlDTO> search(MwlFilter filter);

}
