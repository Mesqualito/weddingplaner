package rocks.gebsattel.hochzeit.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PartyFoodSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PartyFoodSearchRepositoryMockConfiguration {

    @MockBean
    private PartyFoodSearchRepository mockPartyFoodSearchRepository;

}
