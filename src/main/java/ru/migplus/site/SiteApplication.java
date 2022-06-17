package ru.migplus.site;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.migplus.site.dao.main.repo.*;

@Slf4j
@SpringBootApplication
public class SiteApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Profile("dev")
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, RoleRepository roleRepo, ConsumerRepository consumerRepo
            , EquipRepository equipRepo, EquipOperRepository equipOperRepo, EquipServRepository equipServRepo
            , EnvUnitTypeRepository envUnitTypeRepo, EnvUnitRepository envUnitRepo
            , EnvUnitOperRepository envUnitOperRepo, UserPostRepository userPostRepo
            , ParamTypeRepository paramTypeRepo, RdParamRepository rdParamRepo, RdJobConfRepository rdJobConfRepo
            , PermissionRepository permissionRepo, SafeEventRepository safeEventRepo, MenuItemRepository menuItemRepo) {
        return args -> {
			/*InitDb.init(userRepo, roleRepo, consumerRepo,equipRepo,equipOperRepo,equipServRepo,envUnitTypeRepo,envUnitRepo,envUnitOperRepo,
					userPostRepo,paramTypeRepo,rdParamRepo,rdJobConfRepo,permissionRepo,safeEventRepo,menuItemRepo);*/
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SiteApplication.class);
    }
}
