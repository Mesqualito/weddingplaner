package rocks.gebsattel.hochzeit.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.gebsattel.hochzeit.domain.AllowControl;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Spring Data JPA repository for the AllowControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllowControlRepository extends JpaRepository<AllowControl, Long> {
    @Query("select distinct allow_control from AllowControl allow_control left join fetch allow_control.controlledGroups")
    List<AllowControl> findAllWithEagerRelationships();

    @Query("select allow_control from AllowControl allow_control left join fetch allow_control.controlledGroups where allow_control.id =:id")
    AllowControl findOneWithEagerRelationships(@Param("id") Long id);

    List<AllowControl> findAllByControlGroupUserId(Long userId);

}
