package fr.eai.application.rnd.management.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(fr.eai.application.rnd.management.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Sujet.class.getName(), jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Projet.class.getName(), jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Projet.class.getName() + ".sujets", jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Theme.class.getName(), jcacheConfiguration);
            cm.createCache(fr.eai.application.rnd.management.domain.Theme.class.getName() + ".sujets", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
