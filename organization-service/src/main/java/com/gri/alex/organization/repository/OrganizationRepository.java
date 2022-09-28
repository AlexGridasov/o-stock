package com.gri.alex.organization.repository;

import com.gri.alex.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    Optional<Organization> findById(String organizationId);
}
