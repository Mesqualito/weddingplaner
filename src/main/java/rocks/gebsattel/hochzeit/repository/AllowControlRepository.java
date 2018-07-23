package rocks.gebsattel.hochzeit.repository;

<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.enumeration.AllowGroup;
=======
import rocks.gebsattel.hochzeit.domain.AllowControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
>>>>>>> jhipster_upgrade

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AllowControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllowControlRepository extends JpaRepository<AllowControl, Long> {

    @Query(value = "select distinct allow_control from AllowControl allow_control left join fetch allow_control.controlledGroups",
        countQuery = "select count(distinct allow_control) from AllowControl allow_control")
    Page<AllowControl> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct allow_control from AllowControl allow_control left join fetch allow_control.controlledGroups")
    List<AllowControl> findAllWithEagerRelationships();

    @Query("select allow_control from AllowControl allow_control left join fetch allow_control.controlledGroups where allow_control.id =:id")
    Optional<AllowControl> findOneWithEagerRelationships(@Param("id") Long id);

    Page<AllowControl> findAllByControlGroupUserId(Pageable pageable, Long userId);

    List<AllowControl> findAllByControlGroupUserId(Long userId);

    AllowControl findOneByControlGroupUserIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup);
    List<AllowControl> findAllByControlledGroupsIdAndAllowGroup(Long userExtraId, AllowGroup allowGroup);

}
