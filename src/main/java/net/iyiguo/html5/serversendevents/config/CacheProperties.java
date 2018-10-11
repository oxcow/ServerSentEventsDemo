package net.iyiguo.html5.serversendevents.config;

import com.google.common.collect.Lists;
import net.iyiguo.html5.serversendevents.domain.SystemUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

    private final List<SystemUser> systemUsers = Lists.newArrayList();

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

}
