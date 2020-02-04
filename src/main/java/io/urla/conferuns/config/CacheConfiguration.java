package io.urla.conferuns.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.urla.conferuns.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, io.urla.conferuns.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, io.urla.conferuns.domain.User.class.getName());
            createCache(cm, io.urla.conferuns.domain.Authority.class.getName());
            createCache(cm, io.urla.conferuns.domain.User.class.getName() + ".authorities");
            createCache(cm, io.urla.conferuns.domain.Conference.class.getName());
            createCache(cm, io.urla.conferuns.domain.Conference.class.getName() + ".scheduleItems");
            createCache(cm, io.urla.conferuns.domain.Conference.class.getName() + ".places");
            createCache(cm, io.urla.conferuns.domain.Conference.class.getName() + ".talks");
            createCache(cm, io.urla.conferuns.domain.Place.class.getName());
            createCache(cm, io.urla.conferuns.domain.Place.class.getName() + ".rooms");
            createCache(cm, io.urla.conferuns.domain.Place.class.getName() + ".conferences");
            createCache(cm, io.urla.conferuns.domain.Room.class.getName());
            createCache(cm, io.urla.conferuns.domain.Talk.class.getName());
            createCache(cm, io.urla.conferuns.domain.Talk.class.getName() + ".files");
            createCache(cm, io.urla.conferuns.domain.Talk.class.getName() + ".participants");
            createCache(cm, io.urla.conferuns.domain.Talk.class.getName() + ".conferences");
            createCache(cm, io.urla.conferuns.domain.Talk.class.getName() + ".tags");
            createCache(cm, io.urla.conferuns.domain.TalkTag.class.getName());
            createCache(cm, io.urla.conferuns.domain.TalkTag.class.getName() + ".talks");
            createCache(cm, io.urla.conferuns.domain.File.class.getName());
            createCache(cm, io.urla.conferuns.domain.File.class.getName() + ".reviews");
            createCache(cm, io.urla.conferuns.domain.FileReview.class.getName());
            createCache(cm, io.urla.conferuns.domain.Topic.class.getName());
            createCache(cm, io.urla.conferuns.domain.Subject.class.getName());
            createCache(cm, io.urla.conferuns.domain.ScheduleItem.class.getName());
            createCache(cm, io.urla.conferuns.domain.Fee.class.getName());
            createCache(cm, io.urla.conferuns.domain.TalkParticipant.class.getName());
            createCache(cm, io.urla.conferuns.domain.TalkParticipant.class.getName() + ".talks");
            createCache(cm, io.urla.conferuns.domain.Presenter.class.getName());
            createCache(cm, io.urla.conferuns.domain.Presenter.class.getName() + ".talks");
            createCache(cm, io.urla.conferuns.domain.TalkHistory.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
