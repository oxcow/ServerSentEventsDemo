package net.iyiguo.html5.serversendevents.web;

import net.iyiguo.html5.serversendevents.domain.User;
import net.iyiguo.html5.serversendevents.enums.RoleEnum;
import net.iyiguo.html5.serversendevents.service.CacheDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/login/")
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CacheDBService cacheDBService;

    @RequestMapping("/index")
    public String login() {
        return "login";
    }

    @RequestMapping("/in")
    public String loginIn(String name, Model model) {

        Optional<User> optionalUser = cacheDBService.getByNameAndPass(name, null);

        logger.info("User: {} login result: {}", name, optionalUser.isPresent());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            cacheDBService.addToLogin(user);

            model.addAttribute("user", user);

            if (RoleEnum.ADMINISTRATOR == user.getRole()) {
                return "publish_notice";
            }

            return "broadcast";
        }

        model.addAttribute("errorMessage", "用户不存在或密码不正确");

        return "login";
    }

    @RequestMapping("/out/{name}")
    public boolean loginOut(@PathVariable String name) {
        Optional<User> optionalUser = cacheDBService.getByNameAndPass(name, null);
        if (optionalUser.isPresent()) {
            cacheDBService.removeFromLogin(optionalUser.get());
        }
        return true;
    }
}
