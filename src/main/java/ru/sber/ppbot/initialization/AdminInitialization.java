package ru.sber.ppbot.initialization;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import ru.sber.ppbot.model.Admin;
import ru.sber.ppbot.service.AdminService;

import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminInitialization implements InitializingBean {

    private AdminService adminService;

    @Value("${telegram.admins}")
    private List<String> admins;

    @Override
    public void afterPropertiesSet() {
        log.info("Инициализация администраторов. Список = {}", admins);

        admins.forEach(adminName -> adminService.addAdmin(Admin.builder()
                .name(adminName)
                .build()));
    }
}
