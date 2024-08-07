package vn.com.itechcorp.module.site.repository;

import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.site.model.Site;

@Repository("siteRepository")
public interface SiteRepository extends BaseRepository<Site, Long> {
}
