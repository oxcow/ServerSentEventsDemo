package net.iyiguo.html5.serversendevents.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.iyiguo.html5.serversendevents.domain.User;
import net.iyiguo.html5.serversendevents.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CacheDBService {

    public static final Logger logger = LoggerFactory.getLogger(CacheDBService.class);

    private List<User> allUsers;

    private Map<Integer, User> loginInUsers;

    @PostConstruct
    public void init() {
        allUsers = Lists.newArrayList();
        allUsers.add(new User(1, "admin", null, RoleEnum.ADMINISTRATOR));
        allUsers.add(new User(2, "jack", null, RoleEnum.GUEST));
        allUsers.add(new User(3, "joe", null, RoleEnum.GUEST));
        allUsers.add(new User(4, "grace", null, RoleEnum.GUEST));

        loginInUsers = Maps.newConcurrentMap();
    }

    public ImmutableMap<Integer, User> getAllLoginUsers() {
        return ImmutableMap.copyOf(loginInUsers);
    }

    public ImmutableList<User> getAllUsers() {
        return ImmutableList.copyOf(allUsers);
    }

    public Optional<User> getByNameAndPass(String name, String pass) {
        if (allUsers.isEmpty()) return Optional.empty();
        return allUsers.stream()
                .filter(u -> u.getName().equals(name)).findFirst();
    }


    public boolean addToLogin(User user) {
        if (user != null && user.getId() != null) {
            if (loginInUsers.containsKey(user.getId())) {
                logger.warn("用户已经登录 {}", user);
            } else {
                loginInUsers.put(user.getId(), user);
            }
        }
        return true;
    }

    public boolean removeFromLogin(User user) {
        if (user != null && user.getId() != null) {
            loginInUsers.remove(user.getId());
        }
        return true;
    }
}
