package net.iyiguo.html5.serversentevents.config;

import com.google.common.collect.Lists;
import net.iyiguo.html5.serversentevents.domain.Room;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "poker-cache")
public class PokerCacheProperties {

    private List<Room> rooms = Lists.newArrayList();

    public List<Room> getRooms() {
        return rooms;
    }
}
