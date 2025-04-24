package co.spribe.testtask.repository;

import co.spribe.testtask.model.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID>, JpaSpecificationExecutor<Unit> {

}
