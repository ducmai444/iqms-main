package vn.com.itechcorp.module.site.controller;

import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.api.method.AsyncBaseDtoAPIMethod;
import vn.com.itechcorp.module.site.dto.SiteDTOGet;
import vn.com.itechcorp.module.site.model.Site;
import vn.com.itechcorp.module.site.service.SiteService;

@Service
public class SiteMethod extends AsyncBaseDtoAPIMethod<SiteDTOGet, Site, Long> {

    public SiteMethod(SiteService service) {
        super(service);
    }

}
