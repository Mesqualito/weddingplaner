package rocks.gebsattel.hochzeit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.gebsattel.hochzeit.domain.Message;
import rocks.gebsattel.hochzeit.domain.UserExtra;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Message entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select distinct message from Message message left join fetch message.tos",
        countQuery = "select count(distinct message) from Message message")
    Page<Message> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct message from Message message left join fetch message.tos")
    List<Message> findAllWithEagerRelationships();

    @Query("select message from Message message left join fetch message.tos where message.id =:id")
    Optional<Message> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Message> findAllByTos(Pageable pageable, UserExtra userExtra);

}
