package rocks.gebsattel.hochzeit.repository;

import rocks.gebsattel.hochzeit.domain.AllowControl;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the AllowControl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AllowControlRepository extends JpaRepository<AllowControl, Long> {
    @Query("select distinct allow_control from AllowControl allow_control left join fetch allow_control.userExtras")
    List<AllowControl> findAllWithEagerRelationships();

    @Query("select allow_control from AllowControl allow_control left join fetch allow_control.userExtras where allow_control.id =:id")
    AllowControl findOneWithEagerRelationships(@Param("id") Long id);

}
