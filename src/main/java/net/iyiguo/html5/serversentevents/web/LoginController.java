package net.iyiguo.html5.serversentevents.web;

import net.iyiguo.html5.serversentevents.domain.SystemUser;
import net.iyiguo.html5.serversentevents.enums.RoleEnum;
import net.iyiguo.html5.serversentevents.service.CacheDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping("/login/")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CacheDBService cacheDBService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @RequestMapping("/index")
    public String login() {
        return "login";
    }

    @RequestMapping("/in")
    public String loginIn(String name, Model model, HttpServletResponse response) {

        Optional<SystemUser> optionalUser = cacheDBService.getByNameAndPass(name, null);

        LOGGER.debug("【{}】请求登录广播系统. allow? {}", name, optionalUser.isPresent());

        if (optionalUser.isPresent()) {

            SystemUser systemUser = optionalUser.get();

            cacheDBService.addToLogin(systemUser);

            model.addAttribute("systemUser", systemUser);

            if (RoleEnum.ADMINISTRATOR == systemUser.getRole()) {
                return "publish_notice";
            }

            return "broadcast";
        }

        model.addAttribute("errorMessage", "用户不存在或密码不正确");

        return "login";
    }

    @RequestMapping("/out/{name}")
    public String loginOut(@PathVariable String name) {
        Optional<SystemUser> optionalUser = cacheDBService.getByNameAndPass(name, null);
        if (optionalUser.isPresent()) {
            cacheDBService.removeFromLogin(optionalUser.get());
            publisher.publishEvent(optionalUser.get().getName());
            LOGGER.debug("发送【{}】登出系统消息", name);
        }
        return "login";
    }
}
