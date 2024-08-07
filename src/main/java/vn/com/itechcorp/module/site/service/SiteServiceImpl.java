package vn.com.itechcorp.module.site.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.service.impl.BaseDtoJpaServiceImpl;
import vn.com.itechcorp.module.modality.service.ModalityService;
import vn.com.itechcorp.module.site.dto.SiteDTOGet;
import vn.com.itechcorp.module.site.model.Site;
import vn.com.itechcorp.module.site.repository.SiteRepository;

@SuppressWarnings("Duplicates")
@Service("siteService")
public class SiteServiceImpl extends BaseDtoJpaServiceImpl<SiteDTOGet, Site, Long> implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ModalityService modalityService;

    @Override
    public SiteRepository getRepository() {
        return siteRepository;
    }

    @Override
    public SiteDTOGet convert(Site site) {
        SiteDTOGet dto = new SiteDTOGet(site);
        if (dto.getModalities() != null) {
            modalityService.fetchLazyInformation(dto.getModalities());
        }
        return dto;
    }

    @Override
    public SiteDTOGet getById(Long id) throws APIException {
        Site site = siteRepository.getOne(id);
        return convert(site);
    }
}

