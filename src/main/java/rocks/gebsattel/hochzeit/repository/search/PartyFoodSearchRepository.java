package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.PartyFood;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PartyFood entity.
 */
public interface PartyFoodSearchRepository extends ElasticsearchRepository<PartyFood, Long> {
}
