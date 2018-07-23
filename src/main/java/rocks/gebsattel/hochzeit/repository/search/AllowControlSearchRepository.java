package rocks.gebsattel.hochzeit.repository.search;

import rocks.gebsattel.hochzeit.domain.AllowControl;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AllowControl entity.
 */
public interface AllowControlSearchRepository extends ElasticsearchRepository<AllowControl, Long> {
}
