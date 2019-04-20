package com.btocakci.conferuns.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ScheduleItemSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ScheduleItemSearchRepositoryMockConfiguration {

    @MockBean
    private ScheduleItemSearchRepository mockScheduleItemSearchRepository;

}
