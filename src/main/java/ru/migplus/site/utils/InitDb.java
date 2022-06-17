package ru.migplus.site.utils;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.migplus.site.dao.main.repo.*;
import ru.migplus.site.domain.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
@Transactional
public class InitDb {
    final static Faker faker = new Faker(Locale.ENGLISH);

    public static final void init(UserRepository userRepo, RoleRepository roleRepo, ConsumerRepository consumerRepo
            , EquipRepository equipRepo, EquipOperRepository equipOperRepo, EquipServRepository equipServRepo
            , EnvUnitTypeRepository envUnitTypeRepo
            , EnvUnitRepository envUnitRepo, EnvUnitOperRepository envUnitOperRepo
            , UserPostRepository userPostRepo
            , ParamTypeRepository paramTypeRepo, RdParamRepository rdParamRepo, RdJobConfRepository rdJobConfRepo
            , PermissionRepository permissionRepo, SafeEventRepository safeEventRepo, MenuItemRepository menuItemRepo) {

        envUnitOperRepo.deleteAll();
        envUnitRepo.deleteAll();
        envUnitTypeRepo.deleteAll();
        safeEventRepo.deleteAll();
        rdParamRepo.deleteAll();
        rdJobConfRepo.deleteAll();
        equipServRepo.deleteAll();
        equipOperRepo.deleteAll();
        equipRepo.deleteAll();
        consumerRepo.deleteAll();
        paramTypeRepo.deleteAll();
        userRepo.deleteAll();
        roleRepo.deleteAll();
        userPostRepo.deleteAll();
        menuItemRepo.deleteAll();
        permissionRepo.deleteAll();

        userPostRepo.resetSeq();

        // права
        List<Permission> perms = permissions(permissionRepo);

        //меню
        List<MenuItem> menuItems = menuItems(menuItemRepo, perms);

        // роли
        List<Role> roles = userRoles(roleRepo, perms, "Все");
        List<Role> adminRoles = userRoles(roleRepo, Arrays.asList(perms.get(14), perms.get(15), perms.get(16), perms.get(17), perms.get(24)), "Администратор");
        List<Role> servRoles = userRoles(roleRepo, Arrays.asList(perms.get(0), perms.get(1), perms.get(3), perms.get(4)
                , perms.get(6), perms.get(8), perms.get(9), perms.get(10)
                , perms.get(12), perms.get(13), perms.get(17), perms.get(18)), "Инженер-наладчик кот. обор-я");

        List<Role> servGasRoles = userRoles(roleRepo, Arrays.asList(perms.get(0), perms.get(1), perms.get(3), perms.get(4)
                , perms.get(6), perms.get(8), perms.get(9), perms.get(10)
                , perms.get(12), perms.get(13), perms.get(17), perms.get(18)), "Инженер-наладчик газ. обор-я");

        List<Role> mainGasRoles = userRoles(roleRepo, Arrays.asList(perms.get(0), perms.get(1), perms.get(2), perms.get(3), perms.get(4)
                , perms.get(5), perms.get(6), perms.get(7), perms.get(8), perms.get(9), perms.get(10)
                , perms.get(11), perms.get(12), perms.get(13), perms.get(17), perms.get(18), perms.get(25), perms.get(26)), "Начальник газовой службы");

        List<Role> mainServRoles = userRoles(roleRepo, Arrays.asList(perms.get(0), perms.get(1), perms.get(2), perms.get(3), perms.get(4)
                , perms.get(5), perms.get(6), perms.get(7), perms.get(8), perms.get(9), perms.get(10)
                , perms.get(11), perms.get(12), perms.get(13), perms.get(17), perms.get(18), perms.get(25), perms.get(26)), "Начальник сервисной службы");

        List<Role> dirRoles = userRoles(roleRepo, Arrays.asList(perms.get(0), perms.get(1), perms.get(2), perms.get(3), perms.get(4)
                , perms.get(5), perms.get(6), perms.get(7), perms.get(8), perms.get(9), perms.get(10)
                , perms.get(11), perms.get(12), perms.get(13), perms.get(14), perms.get(15), perms.get(16), perms.get(17), perms.get(17), perms.get(18)
                , perms.get(24), perms.get(25), perms.get(26)), "Директор СЦ");

        //roleRepo.saveAll(roles);
        //roleRepo.saveAll(adminRoles);
        //roleRepo.saveAll(servRoles);
        //roleRepo.saveAll(mainGasRoles);


        //должности
        List<UserPost> posts = userPosts(userPostRepo);

        // пользователи
        User supus = new User();
        supus.setUsername("root");
        supus.setPassword(new BCryptPasswordEncoder().encode("142213"));
        supus.setPost(posts.get(0));
        supus.setRoles(roles);
        //supus.setUpdated(ZonedDateTime.now());
        userRepo.save(supus);

        User user1 = new User();
        user1.setUsername("zaytsev_aa");
        user1.setSurname("Зайцев");
        user1.setName("Александр");
        user1.setPatronymic("Александрович");
        user1.setPassword(new BCryptPasswordEncoder().encode("77865"));
        user1.setEmail("zsid87@gmail.com");
        user1.setPost(posts.get(0));
        user1.setRoles(adminRoles);
        user1.setUser(supus);
        //user1.setUpdated(ZonedDateTime.now());
        userRepo.save(user1);

        User user3 = new User();
        user3.setUsername("onishuk_va");
        user3.setSurname("Онищук");
        user3.setName("Вячеслав");
        user3.setPatronymic("Александрович");
        user3.setPost(posts.get(3));
        user3.setPassword(new BCryptPasswordEncoder().encode("onishuk_va"));
        user3.setRoles(servRoles);
        user3.setUser(supus);
        //user3.setUpdated(ZonedDateTime.now());
        userRepo.save(user3);

        User user4 = new User();
        user4.setUsername("prokopchuk_vv");
        user4.setSurname("Прокопчук");
        user4.setName("Владимир");
        user4.setPatronymic("Викторович");
        user4.setPost(posts.get(6));
        user4.setPassword(new BCryptPasswordEncoder().encode("prokopchuk_vv"));
        user4.setRoles(mainGasRoles);
        user4.setUser(supus);
        //user4.setUpdated(ZonedDateTime.now());
        userRepo.save(user4);

        User user5 = new User();
        user5.setUsername("karpov_vv");
        user5.setSurname("Карпов");
        user5.setName("Владислав");
        user5.setPatronymic("Владимирович");
        user5.setPost(posts.get(5));
        user5.setPassword(new BCryptPasswordEncoder().encode("karpov_vv"));
        user5.setRoles(mainServRoles);
        user5.setUser(supus);
        //user5.setUpdated(ZonedDateTime.now());
        userRepo.save(user5);

        User user6 = new User();
        user6.setUsername("zhmyhov_vv");
        user6.setSurname("Жмыхов");
        user6.setName("Виталий");
        user6.setPatronymic("Андреевич");
        user6.setPost(posts.get(3));
        user6.setPassword(new BCryptPasswordEncoder().encode("zhmyhov_vv"));
        user6.setRoles(servRoles);
        user6.setUser(supus);
        //user6.setUpdated(ZonedDateTime.now());
        userRepo.save(user6);

        /*List<User> users = new ArrayList<>();
        for(int i = 0; i<100; i++)
            users.addAll(users(userRepo,posts,servRoles,supus));*/

        // потребители
        List<Consumer> consumers = consumers(consumerRepo, supus);

        // оборудование
        List<Equip> equips = equips(equipRepo, equipOperRepo, consumers.get(0));

        //типы единиц запчастей
        List<EnvUnitType> envUnitTypes = envUnitTypes(envUnitTypeRepo);

        //единицы запчастей
        List<EnvUnit> envUnits = envUnits(envUnitRepo, envUnitOperRepo, envUnitTypes, consumers, supus);

        //потоки потребителей
        List<RdJobConf> rdJobConfs = rdJobConfs(rdJobConfRepo, consumers, supus);

        //типы параметров
        List<ParamType> paramTypes = paramTypes(paramTypeRepo, supus);

        //типы параметров
        List<RdParam> rdParams = rdParams(rdParamRepo, rdJobConfs, paramTypes, supus);

        //события безопасности
        List<SafeEvent> safeEvents = safeEvents(safeEventRepo, rdParams);

        /*Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();*/



        /*SafeEvent safeEvent1 = new SafeEvent();
        safeEvent1.setNotifyDate(dt);
        safeEvent1.setRdParam(rdParam13);
        safeEvent1.setRepairDate(dt);
        safeEvent1.setUser(supus);
        safeEvent1.setComment("Неисправость");
        safeEventRepo.save(safeEvent1);

        SafeEvent safeEvent2 = new SafeEvent();
        safeEvent2.setNotifyDate(new Date());
        safeEvent2.setRdParam(rdParam14);
        safeEvent2.setRepairDate(dt);
        safeEvent2.setUser(supus);
        safeEvent2.setComment("Неисправость");
        safeEventRepo.save(safeEvent2);

        SafeEvent safeEvent3 = new SafeEvent();
        safeEvent3.setNotifyDate(new Date());
        safeEvent3.setRdParam(rdParam15);
        safeEventRepo.save(safeEvent3);*/
    }


    private static List<Permission> permissions(PermissionRepository repo) {
        List<Permission> items = new ArrayList<>(
                Arrays.asList(
                        // m{N} - меню с номером уровня, dr - просмотр таблицы-справочника, dw - запись в таблицу-справочник,
                        // de - редактирование таблицы-справочника, dd - удаление из таблицы-справочника
                        // r - просмотр таблицы, arepair - действие
                        new Permission("safe:m1", "Меню: Безопасность") // 0
                        , new Permission("safe:m21", "Меню: Безопасность::Статусы")
                        , new Permission("safe:m22", "Меню: Безопасность::График")
                        , new Permission("safe:m23", "Меню: Безопасность::История")
                        , new Permission("equip:m1", "Меню: Оборудование")
                        , new Permission("equip:m21", "Меню: Оборудование::Добавление") // 5
                        , new Permission("equip:m22", "Меню: Оборудование::Регл. работы")
                        , new Permission("equip:m23", "Меню: Оборудование::История работ")
                        , new Permission("envunit:m1", "Меню: Запчасти")
                        , new Permission("envunit:m21", "Меню: Запчасти>Установка")
                        , new Permission("envunit:m22", "Меню: Запчасти>Поверка") // 10
                        , new Permission("envunit:m23", "Меню: Запчасти>Замена")
                        , new Permission("envunit:m24", "Меню: Запчасти>История замен")
                        , new Permission("params:m1", "Меню: Показания")
                        , new Permission("params:m2", "Меню: Показания::Датчики")
                        , new Permission("catalog:m1", "Меню: Справочники") //15
                        , new Permission("catalog:m2", "Меню: Справочники::Пользователи")
                        , new Permission("consumers:dr", "Справочник потребителей: просмотр")
                        , new Permission("consumers:dw", "Справочник потребителей: добавлнеие")
                        , new Permission("consumers:de", "Справочник потребителей: редактирование")
                        , new Permission("consumers:dd", "Справочник потребителей: удаление") //20
                        , new Permission("safe-param-states:r", "События безопасности: просмотр состояний")
                        , new Permission("safe-param-states:repair", "События безопасности: наладка состояния")
                        , new Permission("safe-param-states-hist:r", "События безопасности: просмотр истории состояний")
                        , new Permission("roles:dr", "Справочник ролей: просмотр")
                        , new Permission("equip-serve:d", "Оборудование: удалить")
                        , new Permission("equip-serve-hist:d", "История ремонта обороудования: удалить")
                        , new Permission("env-setup:d", "Устнаовка зап. части: удалить")
                        , new Permission("env-setup-hist:d", "История установки зап. частей: удалить")
                        , new Permission("env-change-hist:d", "История замен зап. частей: удалить")

                )
        );
        return repo.saveAll(items);
    }

    private static List<MenuItem> menuItems(MenuItemRepository repo, List<Permission> perms) {
        List<MenuItem> items = new ArrayList<>();

        MenuItem itemParent = null;
        MenuItem item = null;

        itemParent = new MenuItem();
        itemParent.setName("Безопасность");
        itemParent.setLevel((short) 1);
        itemParent.setOrd(5);
        itemParent.setPermission(perms.get(0));
        items.add(itemParent);

        item = new MenuItem();
        item.setName("Статусы");
        item.setLevel((short) 2);
        item.setPath("/safe-params/state");
        item.setIconName("cil-lightbulb");
        item.setMenuItem(itemParent);
        item.setOrd(5);
        item.setPermission(perms.get(1));
        items.add(item);

        item = new MenuItem();
        item.setName("График");
        item.setLevel((short) 2);
        item.setPath("/safe-params/graph");
        item.setIconName("cil-chart-line");
        item.setMenuItem(itemParent);
        item.setOrd(10);
        item.setPermission(perms.get(3));
        items.add(item);

        item = new MenuItem();
        item.setName("История");
        item.setLevel((short) 2);
        item.setPath("/safe-params/hist");
        item.setIconName("cil-layers");
        item.setMenuItem(itemParent);
        item.setOrd(15);
        item.setPermission(perms.get(2));
        items.add(item);

        itemParent = new MenuItem();
        itemParent.setName("Оборудование");
        itemParent.setLevel((short) 1);
        itemParent.setOrd(10);
        itemParent.setPermission(perms.get(4));
        items.add(itemParent);

        item = new MenuItem();
        item.setName("Добавление");
        item.setLevel((short) 2);
        item.setPath("/equip-serv/equip-add");
        item.setIconName("cil-library-add");
        item.setMenuItem(itemParent);
        item.setOrd(5);
        item.setPermission(perms.get(5));
        items.add(item);

        item = new MenuItem();
        item.setName("Регл. работы");
        item.setLevel((short) 2);
        item.setPath("/equip-serv/serv");
        item.setIconName("cil-pencil");
        item.setMenuItem(itemParent);
        item.setOrd(10);
        item.setPermission(perms.get(6));
        items.add(item);

        item = new MenuItem();
        item.setName("История работ");
        item.setLevel((short) 2);
        item.setPath("/equip-serv/hist");
        item.setIconName("cil-layers");
        item.setMenuItem(itemParent);
        item.setOrd(15);
        item.setPermission(perms.get(7));
        items.add(item);

        itemParent = new MenuItem();
        itemParent.setName("Запчасти");
        itemParent.setLevel((short) 1);
        itemParent.setOrd(15);
        itemParent.setPermission(perms.get(8));
        items.add(itemParent);

        item = new MenuItem();
        item.setName("Установка");
        item.setLevel((short) 2);
        item.setPath("/repair-parts/env-setup");
        item.setIconName("cil-cursor");
        item.setMenuItem(itemParent);
        item.setOrd(5);
        item.setPermission(perms.get(9));
        items.add(item);

        item = new MenuItem();
        item.setName("Замена");
        item.setLevel((short) 2);
        item.setPath("/repair-parts/env-change");
        item.setIconName("cil-av-timer");
        item.setMenuItem(itemParent);
        item.setOrd(10);
        item.setPermission(perms.get(10));
        items.add(item);

        item = new MenuItem();
        item.setName("Поверка");
        item.setLevel((short) 2);
        item.setPath("/repair-parts/env-verify");
        item.setIconName("cil-av-timer");
        item.setMenuItem(itemParent);
        item.setOrd(12);
        item.setPermission(perms.get(10));
        items.add(item);

        item = new MenuItem();
        item.setName("История замен");
        item.setLevel((short) 2);
        item.setPath("/repair-parts/env-change-history");
        item.setIconName("cil-layers");
        item.setMenuItem(itemParent);
        item.setOrd(15);
        item.setPermission(perms.get(11));
        items.add(item);

        itemParent = new MenuItem();
        itemParent.setName("Показания");
        itemParent.setLevel((short) 1);
        itemParent.setOrd(25);
        itemParent.setPermission(perms.get(12));
        items.add(itemParent);

        item = new MenuItem();
        item.setName("Датчики");
        item.setLevel((short) 2);
        item.setPath("/indications/env-sensors");
        item.setIconName("cil-cast");
        item.setMenuItem(itemParent);
        item.setOrd(5);
        item.setPermission(perms.get(13));
        items.add(item);

        itemParent = new MenuItem();
        itemParent.setName("Справочники");
        itemParent.setLevel((short) 1);
        itemParent.setOrd(5);
        itemParent.setPermission(perms.get(14));
        items.add(itemParent);

        item = new MenuItem();
        item.setName("Пользователи");
        item.setLevel((short) 2);
        item.setPath("/catalogs/users");
        item.setIconName("cil-user");
        item.setMenuItem(itemParent);
        item.setOrd(5);
        item.setPermission(perms.get(15));
        items.add(item);

        return repo.saveAll(items);
    }

    private static List<UserPost> userPosts(UserPostRepository repo) {
        List<UserPost> items = new ArrayList<>(
                Arrays.asList(new UserPost("Администратор")
                        , new UserPost("Директор СЦ")
                        , new UserPost("Зам. директора СЦ")
                        , new UserPost("Инженер-наладчик котельного оборудования")
                        , new UserPost("Инженер-наладчик газового оборудования")
                        , new UserPost("Начальник сервисной службы")
                        , new UserPost("Начальник газовой службы")
                )
        );
        return repo.saveAll(items);
    }

    private static List<Role> userRoles(RoleRepository repo, List<Permission> perms, String name) {
        List<Role> items = new ArrayList<>(
                Arrays.asList(
                        new Role(name, perms)
                )
        );
        return repo.saveAll(items);
    }

    private static List<User> users(UserRepository repo, List<UserPost> posts, List<Role> roles, User supus) {
        List<User> items = null;

        /*

        User user3 = new User();
        user3.setUsername("ivanov_ii");
        user3.setSurname("Иванов");
        user3.setName("Иван");
        user3.setPatronymic("Иванович");
        user3.setPost(posts.get(2));
        user3.setPassword(new BCryptPasswordEncoder().encode("142213"));
        user3.setEmail("berest@yandex.ru");
        user3.setRoles(servRoles);
        user3.setUser(supus);
        user3.setUpdated(ZonedDateTime.now());
        userRepo.save(user3);*/

        /*List<User> items = new ArrayList<>();
        User user = null;
        String userName = null;
        for(int i=0; i<100; i++) {
            userName = "user_"+i;
            user = new User();
            user.setUsername(userName);
            user.setSurname(faker.letterify("??????"));
            user.setName(faker.letterify("????????"));
            user.setPatronymic(faker.letterify("??????????"));
            user.setPost(posts.get(1));
            user.setPassword(new BCryptPasswordEncoder().encode(userName));
            user.setEmail(userName+"@mail.ru");
            user.setRoles(roles);
            user.setUser(supus);
            user.setUpdated(ZonedDateTime.now());
            items.add(user);
        }*/
        return repo.saveAll(items);
    }

    private static List<Consumer> consumers(ConsumerRepository repo, User user) {

        List<Consumer> items = new ArrayList<>();

        Consumer item = new Consumer();
        item.setName("Калужская 127");
        item.setRdDstName("rd_consumer_1");
        item.setUser(user);
        items.add(item);

        item = new Consumer();
        item.setName("Котельная 2");
        item.setRdDstName("rd_consumer_2");
        item.setUser(user);
        items.add(item);

        return repo.saveAll(items);
    }

    private static List<EnvUnitType> envUnitTypes(EnvUnitTypeRepository repo) {
        List<EnvUnitType> items = new ArrayList<>();

        EnvUnitType envUnitType = new EnvUnitType();
        envUnitType.setName("Подшипник");
        //envUnitType.setCategory(EnvUnitCategory_old.DETAIL);
        //envUnitType.setCategory(EnvUnitCategory_old.DETAIL);
        items.add(envUnitType);

        envUnitType = new EnvUnitType();
        envUnitType.setName("Датчик");
        //envUnitType.setCategory(EnvUnitCategory_old.INDICATOR);
        items.add(envUnitType);

        return repo.saveAll(items);
    }

    private static List<EnvUnit> envUnits(EnvUnitRepository envUnitRepo, EnvUnitOperRepository envUnitOperRepo, List<EnvUnitType> envUnitTypes, List<Consumer> consumers, User supus) {
        List<EnvUnit> items = new ArrayList<>();

        EnvUnit envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("AL388698");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);

        EnvUnitOper opIncome = new EnvUnitOper();
        opIncome.setEnvUnit(envUnit);
        opIncome.setDateSt(LocalDateTime.now());
        //opIncome.setName(EnvUnitOperName_old.INCOME);
        opIncome.setUser(supus);
        envUnitOperRepo.save(opIncome);

        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("AK876525");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);

        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(1));
        envUnit.setNumber("BI856588");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);

        opIncome = new EnvUnitOper();
        opIncome.setEnvUnit(envUnit);
        opIncome.setDateSt(LocalDateTime.now());
        //opIncome.setName(EnvUnitOperName_old.INCOME);
        opIncome.setUser(supus);
        envUnitOperRepo.save(opIncome);

        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(1));
        envUnit.setNumber("Б/Н");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);

        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(1));
        envUnit.setNumber("BK003456");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("CL856588");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(1));
        envUnit.setNumber("Б/Н");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("CP565858");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(1));
        envUnit.setNumber("CG963258");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("DK568585");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        envUnit = new EnvUnit();
        envUnit.setEnvUnitType(envUnitTypes.get(0));
        envUnit.setNumber("DL963654");
        envUnit.setConsumer(consumers.get(0));
        envUnitRepo.save(envUnit);
        items.add(envUnit);

        return items;
    }

    private static List<Equip> equips(EquipRepository repo, EquipOperRepository operRepo, Consumer consumer) {

        List<Equip> items = new ArrayList<>();

        Equip equip1 = new Equip();
        equip1.setName("Газовый котел RIELLO Beretta");
        equip1.setNumber("1");
        equip1.setConsumer(consumer);
        items.add(repo.save(equip1));

        List<EquipOper> equipOpers = new ArrayList() {{
            add(new EquipOper("РНИ", equip1));
            add(new EquipOper("ТО", equip1));
            add(new EquipOper("ТР", equip1));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip2 = new Equip();
        equip2.setName("Газовый котел RIELLO Beretta");
        equip2.setNumber("2");
        equip2.setConsumer(consumer);
        items.add(repo.save(equip2));

        equipOpers = new ArrayList() {{
            add(new EquipOper("РНИ", equip2));
            add(new EquipOper("ТО", equip2));
            add(new EquipOper("ТР", equip2));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip3 = new Equip();
        equip3.setName("Болер RIELLO 60 kW");
        equip3.setNumber("4030206");
        equip3.setYear("2008");
        equip3.setConsumer(consumer);
        items.add(repo.save(equip3));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip3));
            add(new EquipOper("ТР", equip3));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip4 = new Equip();
        equip4.setName("Насос Grundfos UPS 25-40");
        equip4.setNumber("96281375");
        equip4.setYear("2010");
        equip4.setConsumer(consumer);
        items.add(repo.save(equip4));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip4));
            add(new EquipOper("ТР", equip4));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip5 = new Equip();
        equip5.setName("Насос Grundfos UPS 25-60");
        equip5.setNumber("96281477");
        equip5.setYear("2010");
        equip5.setConsumer(consumer);
        items.add(repo.save(equip5));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip5));
            add(new EquipOper("ТР", equip5));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip6 = new Equip();
        equip6.setName("Насос Grundfos UPS 25-120");
        equip6.setNumber("52588336");
        equip6.setYear("2010");
        equip6.setConsumer(consumer);
        items.add(repo.save(equip6));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip6));
            add(new EquipOper("ТР", equip6));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip7 = new Equip();
        equip7.setName("Насос Grundfos UPS 25-80");
        equip7.setNumber("95906440");
        equip7.setYear("2010");
        equip7.setConsumer(consumer);
        items.add(repo.save(equip7));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip7));
            add(new EquipOper("ТР", equip7));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip8 = new Equip();
        equip8.setName("Насос Grundfos UPS 40-180");
        equip8.setNumber("96401979");
        equip8.setYear("2014");
        equip8.setConsumer(consumer);
        items.add(repo.save(equip8));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip8));
            add(new EquipOper("ТР", equip8));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip9 = new Equip();
        equip9.setName("Насос Grundfos UPS 40-80");
        equip9.setNumber("95906440");
        equip9.setYear("2013");
        equip9.setConsumer(consumer);
        items.add(repo.save(equip9));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip9));
            add(new EquipOper("ТР", equip9));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip10 = new Equip();
        equip10.setName("Регулятор ESBE");
        equip10.setNumber("12001400");
        equip10.setConsumer(consumer);
        items.add(repo.save(equip10));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip10));
            add(new EquipOper("ТР", equip10));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip11 = new Equip();
        equip11.setName("Регулятор ESBE");
        equip11.setNumber("12101316");
        equip11.setConsumer(consumer);
        items.add(repo.save(equip11));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip11));
            add(new EquipOper("ТР", equip11));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip12 = new Equip();
        equip12.setName("Счетчик электроэнергии");
        equip12.setNumber("05409059");
        equip12.setConsumer(consumer);
        items.add(repo.save(equip12));

        equipOpers = new ArrayList() {{
            add(new EquipOper("Поверка", equip12));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip13 = new Equip();
        equip13.setName("Счетчик электроэнергии");
        equip13.setNumber("00243791");
        equip13.setYear("2010");
        equip13.setConsumer(consumer);
        items.add(repo.save(equip13));

        equipOpers = new ArrayList() {{
            add(new EquipOper("Поверка", equip13));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip14 = new Equip();
        equip14.setName("Расходомер ПРЭМ Ду 20");
        equip14.setNumber("521361");
        equip14.setConsumer(consumer);
        items.add(repo.save(equip14));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip14));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip15 = new Equip();
        equip15.setName("Расходомер ПРЭМ Ду 20");
        equip15.setNumber("587779");
        equip15.setConsumer(consumer);
        items.add(repo.save(equip15));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip15));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip16 = new Equip();
        equip16.setName("Газоанализатор Seitron");
        equip16.setConsumer(consumer);
        items.add(repo.save(equip16));

        equipOpers = new ArrayList() {{
            add(new EquipOper("Поверка", equip16));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip17 = new Equip();
        equip17.setName("Шкаф управления котельной");
        equip17.setNumber("1");
        equip17.setConsumer(consumer);
        items.add(repo.save(equip17));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip17));
        }};
        operRepo.saveAll(equipOpers);

        Equip equip18 = new Equip();
        equip18.setName("Бак расширительный Reflex");
        equip18.setNumber("60186");
        equip18.setConsumer(consumer);
        items.add(repo.save(equip18));

        equipOpers = new ArrayList() {{
            add(new EquipOper("ТО", equip18));
            add(new EquipOper("ТР", equip18));
        }};
        operRepo.saveAll(equipOpers);

        return repo.saveAll(items);
    }

    private static List<RdJobConf> rdJobConfs(RdJobConfRepository repo, List<Consumer> consumers, User supus) {
        List<RdJobConf> items = new ArrayList<>();
        RdJobConf item = null;

        item = new RdJobConf(); //1-й потребитель
        item.setInterval((short) 20);
        item.setBatchCnt((short) 100);
        item.setState(RdJobConfState.STARTED);
        item.setType(RdJobConfType.DATA);
        item.setUser(supus);
        item.setConsumer(consumers.get(0));
        items.add(item);

        item = new RdJobConf(); //1-й потребитель безопастность
        item.setInterval((short) 20);
        item.setBatchCnt((short) 100);
        item.setState(RdJobConfState.STARTED);
        item.setType(RdJobConfType.SAFE);
        item.setUser(supus);
        item.setConsumer(consumers.get(0));
        items.add(item);

        return repo.saveAll(items);
    }

    private static List<ParamType> paramTypes(ParamTypeRepository repo, User supus) {
        List<ParamType> items = new ArrayList<>();
        ParamType item = null;
        item = new ParamType();
        item.setName("int");
        item.setDescr("Целочисленный");
        item.setUser(supus);
        item.setUser(supus);
        items.add(item);

        item.setName("decimal");
        item.setDescr("Действительный");
        item.setUser(supus);
        items.add(item);

        item.setName("bool");
        item.setDescr("Логи́ческий");
        item.setUser(supus);
        items.add(item);

        return repo.saveAll(items);
    }

    private static List<RdParam> rdParams(RdParamRepository repo, List<RdJobConf> rdJobConfs, List<ParamType> paramTypes, User supus) {
        List<RdParam> items = new ArrayList<>();
        RdParam item = null;
        //String srcPath = "77.73.91.70:1829";
        String srcPath = "192.168.1.227:1829";


        item = new RdParam();
        item.setName("Т прямой контур № 1");
        item.setTransName("t_pod_contur_1");
        item.setParamType(paramTypes.get(1));
        //item.setPathSrc(srcPath);
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т обратки контур № 1");
        item.setTransName("t_obr_contur_1");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т прямой контур № 2");
        item.setTransName("t_pod_contur_2");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т обратки контур № 2");
        item.setTransName("t_obr_contur_2");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т прямая котел № 1");
        item.setTransName("t_pod_kotel_1");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т обратки котел № 1");
        item.setTransName("t_obr_kotel_1");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т прямая котел № 2");
        item.setTransName("t_pod_kotel_2");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т обратки котел № 2");
        item.setTransName("t_obr_kotel_2");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Давление после насоса");
        item.setTransName("p_posle_nasosa");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Давление до насоса");
        item.setTransName("p_do_nasosa");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Частота насоса");
        item.setTransName("Frequency_nasosa");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Т наружного воздуха");
        item.setTransName("t_nar_vozduha");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(0));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Безоп. 1");
        item.setTransName("safe_1");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(1));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Безоп. 2");
        item.setTransName("safe_2");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(1));
        item.setUser(supus);
        items.add(item);

        item = new RdParam();
        item.setName("Безоп. 3");
        item.setTransName("safe_3");
        item.setParamType(paramTypes.get(1));
        item.setPathSrc(srcPath);
        item.setRdJobConf(rdJobConfs.get(1));
        item.setUser(supus);
        items.add(item);

        return repo.saveAll(items);
    }

    private static List<SafeEvent> safeEvents(SafeEventRepository repo, List<RdParam> rdParams) {
        //List<SafeEvent> items = new ArrayList<>();
        //SafeEvent item = null;

        ZonedDateTime dt0 = ZonedDateTime.of(2021, 2, 19, 10, 00, 00, 0, ZoneId.of("Europe/Moscow"));

        ZonedDateTime p1Ok1 = dt0;
        ZonedDateTime p1Er1 = p1Ok1.plusMinutes(20);
        ZonedDateTime p1Ok2 = p1Er1.plusMinutes(30);

        ZonedDateTime p2Ok1 = dt0;
        ZonedDateTime p2Er1 = p2Ok1.plusMinutes(10);
        ZonedDateTime p2Ok2 = p2Er1.plusMinutes(50);

        ZonedDateTime p3Ok1 = dt0;
        ZonedDateTime p3Er1 = p3Ok1.plusMinutes(20);
        ZonedDateTime p3Ok2 = p3Er1.plusMinutes(30);

        List<SafeEvent> items = new ArrayList<>(
                Arrays.asList(
                        new SafeEvent(SafeEventType.OK, p1Ok1, p1Ok1.plusMinutes(20), rdParams.get(12), null, null),
                        new SafeEvent(SafeEventType.ERR, p1Er1, p1Er1.plusMinutes(30), rdParams.get(12), null, null),
                        new SafeEvent(SafeEventType.OK, p1Ok2, p1Ok2.plusMinutes(10), rdParams.get(12), null, null),

                        new SafeEvent(SafeEventType.OK, p2Ok1, p2Ok1.plusMinutes(10), rdParams.get(13), null, null),
                        new SafeEvent(SafeEventType.ERR, p2Er1, p2Er1.plusMinutes(50), rdParams.get(13), null, null),
                        //new SafeEvent(SafeEventType.OK,p2Ok2,p2Ok2.plusMinutes(10),rdParams.get(13),null,null),

                        new SafeEvent(SafeEventType.OK, p3Ok1, p3Ok1.plusMinutes(20), rdParams.get(14), null, null),
                        new SafeEvent(SafeEventType.ERR, p3Er1, p3Er1.plusMinutes(30), rdParams.get(14), null, null),
                        new SafeEvent(SafeEventType.OK, p3Ok2, p3Ok2.plusMinutes(10), rdParams.get(14), null, null)
                )
        );


        return repo.saveAll(items);
    }
}
