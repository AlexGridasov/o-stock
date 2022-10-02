package com.gri.alex.license.service;

import java.util.UUID;

import com.gri.alex.license.config.ServiceConfig;
import com.gri.alex.license.model.License;
import com.gri.alex.license.model.Organization;
import com.gri.alex.license.repository.LicenseRepository;
import com.gri.alex.license.service.client.OrganizationDiscoveryClient;
import com.gri.alex.license.service.client.OrganizationFeignClient;
import com.gri.alex.license.service.client.OrganizationRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

    @Autowired
    MessageSource messages;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;

    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(String.format(
                    messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }
        return license.withComment(config.getProperty());
    }

    public License getLicense(String licenseId, String organizationId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(String.format(
                    messages.getMessage("license.search.error.message", null, null), licenseId, organizationId));
        }

        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                System.out.println("I am using the rest client (default)");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
        }

        return organization;
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);

        return String.format(
                messages.getMessage("license.delete.message", null, null), licenseId);
    }
}
