package rocks.gebsattel.hochzeit.repository;

import org.springframework.data.repository.query.Param;
import rocks.gebsattel.hochzeit.domain.AllowControl;
import rocks.gebsattel.hochzeit.domain.UserExtra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserExtra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long> {

    // @Query("select user_extra from UserExtra user_extra left join fetch user_extra.user u where user_extra.id = u.id and u.login =:login")
    // UserExtra findOneByUserExtraUserLogin(@Param("login") String login);

    UserExtra findOneByUserId(Long id);
}
