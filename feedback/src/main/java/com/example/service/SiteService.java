import com.example.model.Site;
import com.example.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Site getSiteById(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    public Site createSite(Site site) {
        return siteRepository.save(site);
    }

    public Site updateSite(Long id, Site site) {
        Site existingSite = getSiteById(id);
        if (existingSite == null) {
            return null;
        }
        existingSite.setName(site.getName());
        existingSite.setUrl(site.getUrl());
        existingSite.setCreatedAt(site.getCreatedAt());
        existingSite.setUpdatedAt(site.getUpdatedAt());
        return siteRepository.save(existingSite);
    }

    public void deleteSite(Long id) {
        siteRepository.deleteById(id);
    }
}