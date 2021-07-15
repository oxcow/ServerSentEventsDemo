package net.iyiguo.html5.sse.service;

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import net.iyiguo.html5.sse.config.CacheProperties;
import net.iyiguo.html5.sse.domain.SystemUser;

@Component
public class CacheDBService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheDBService.class);

    private Map<Integer, SystemUser> loginInUsers;

    private final CacheProperties cacheProperties;

    public CacheDBService(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @PostConstruct
    public void init() {
        loginInUsers = Maps.newConcurrentMap();
    }

    public Optional<SystemUser> getByNameAndPass(String name, String pass) {
        if (cacheProperties.getSystemUsers().isEmpty()) {
            return Optional.empty();
        }
        return cacheProperties.getSystemUsers().stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }

    public boolean addToLogin(SystemUser systemUser) {
        if (systemUser != null && systemUser.getId() != null) {
            if (loginInUsers.containsKey(systemUser.getId())) {
                LOGGER.warn("【{}】是已登录用户. 当前在线人数: {}", systemUser.getName(), loginInUsers.size());
            } else {
                loginInUsers.put(systemUser.getId(), systemUser);
                LOGGER.debug("【{}】被添加到已登录列表中. 当前在线人数: {}", systemUser.getName(), loginInUsers.size());
            }
        }
        return true;
    }

    public boolean removeFromLogin(SystemUser systemUser) {
        if (systemUser != null && systemUser.getId() != null) {
            loginInUsers.remove(systemUser.getId());
            LOGGER.debug("【{}】退出登录. 当前在线人数: {}", systemUser.getName(), loginInUsers.size());
        }
        return true;
    }
}
