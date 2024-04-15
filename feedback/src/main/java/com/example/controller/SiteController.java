package com.example.live.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @GetMapping
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteService.getAllSites();
        return new ResponseEntity<>(sites, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Site> createSite(@Valid @RequestBody Site site) {
        Site newSite = siteService.createSite(site);
        return new ResponseEntity<>(newSite, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable("id") Long siteId) {
        Site site = siteService.getSiteById(siteId);
        return new ResponseEntity<>(site, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable("id") Long siteId, @Valid @RequestBody Site siteDetails) {
        Site updatedSite = siteService.updateSite(siteId, siteDetails);
        return new ResponseEntity<>(updatedSite, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable("id") Long siteId) {
        siteService.deleteSite(siteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}