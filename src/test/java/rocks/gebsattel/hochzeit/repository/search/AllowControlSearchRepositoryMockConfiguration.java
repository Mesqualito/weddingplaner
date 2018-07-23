package rocks.gebsattel.hochzeit.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AllowControlSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AllowControlSearchRepositoryMockConfiguration {

    @MockBean
    private AllowControlSearchRepository mockAllowControlSearchRepository;

}
