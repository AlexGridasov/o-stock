package com.gri.alex.license.controller;

import com.gri.alex.license.model.License;
import com.gri.alex.license.service.LicenseService;
import com.gri.alex.license.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    private static final Logger logger = LoggerFactory.getLogger(LicenseController.class);

    @Autowired
    LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
                                              @PathVariable("licenseId") String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId);
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId()))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(license))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId()))
                        .withRel("deleteLicense")
        );

        return ResponseEntity.ok(license);
    }

    @GetMapping(value="/{licenseId}/{clientType}")
    public License getLicensesWithClient( @PathVariable("organizationId") String organizationId,
                                          @PathVariable("licenseId") String licenseId,
                                          @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(licenseId, organizationId, clientType);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request));
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
    }

    @GetMapping(value="/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        logger.debug("LicenseServiceController Correlation id: {}",
                     UserContextHolder.getContext().getCorrelationId());

        return licenseService.getLicensesByOrganization(organizationId);
    }
}
