package com.spyke.gcpbigquery.domain.repositories;

import com.spyke.gcpbigquery.domain.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
}
