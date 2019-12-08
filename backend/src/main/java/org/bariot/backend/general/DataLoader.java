package org.bariot.backend.general;

import org.bariot.backend.persistence.model.DeviceModel;
import org.bariot.backend.persistence.model.HomeModel;
import org.bariot.backend.persistence.model.RoomModel;
import org.bariot.backend.persistence.model.UserModel;
import org.bariot.backend.service.core.DeviceService;
import org.bariot.backend.service.core.HomeService;
import org.bariot.backend.service.core.RoomService;
import org.bariot.backend.service.core.UserHomeService;
import org.bariot.backend.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private HomeService homeService;
    private UserHomeService userHomeService;
    private RoomService roomService;
    private DeviceService deviceService;

    @Autowired
    public DataLoader(UserService userService, HomeService homeService, UserHomeService userHomeService, RoomService roomService, DeviceService deviceService) {
        this.userService = userService;
        this.homeService = homeService;
        this.userHomeService = userHomeService;
        this.roomService = roomService;
        this.deviceService = deviceService;
    }

    public void run(ApplicationArguments args) {
        UserModel user1 = userService.create(new UserModel("user1", "user1"));
        UserModel user2 = userService.create(new UserModel("user2", "user2"));
        UserModel user3 = userService.create(new UserModel("user3", "user3"));
        UserModel user4 = userService.create(new UserModel("user4", "user4"));

        HomeModel home1 = homeService.create(new HomeModel("home1","broker.cz","flat.jpg"));
        HomeModel home2 = homeService.create(new HomeModel("home2", "broker.com","other.jpg"));
        HomeModel home3 = homeService.create(new HomeModel("home3","house","house.jpg"));
        HomeModel home4 = homeService.create(new HomeModel("home4", "asdfasdf.cz","flat.jpg"));

        home1 = userHomeService.addExistingHome(user1.getID(), home1.getID());
        home2 = userHomeService.addExistingHome(user1.getID(), home2.getID());
        home4 = userHomeService.addExistingHome(user3.getID(), home4.getID());
        home2 = userHomeService.addExistingHome(user3.getID(), home2.getID());
        home2 = userHomeService.addExistingHome(user2.getID(), home2.getID());
        home2 = userHomeService.addExistingHome(user4.getID(), home2.getID());
        home3 = userHomeService.addExistingHome(user4.getID(), home3.getID());

        homeService.update(home1.getID(), home1);
        homeService.update(home2.getID(), home2);
        homeService.update(home3.getID(), home3);
        homeService.update(home4.getID(), home4);


        RoomModel home1room1 = roomService.create(new RoomModel("room1", home1,RoomType.KITCHEN,"kitchen.png"));
        RoomModel home1room2 = roomService.create(new RoomModel("room2", home1,RoomType.LIVING_ROOM,"living.com"));
        RoomModel home1room3 = roomService.create(new RoomModel("room3", home1,RoomType.BATHROOM,"bathroom.png"));

        RoomModel home2room1 = roomService.create(new RoomModel("room1", home2,RoomType.ROOM,"room.jpg"));
        RoomModel home2room2 = roomService.create(new RoomModel("room2", home2,RoomType.GARAGE,"garage.jpg"));
        RoomModel home2room3 = roomService.create(new RoomModel("room3", home2,RoomType.BEDROOM,"bedroom.png"));

        homeService.addToSubSet(home1.getID(), home1room1);
        homeService.addToSubSet(home1.getID(), home1room2);
        homeService.addToSubSet(home1.getID(), home1room3);

        homeService.addToSubSet(home2.getID(), home2room1);
        homeService.addToSubSet(home2.getID(), home2room2);
        homeService.addToSubSet(home2.getID(), home2room3);


        DeviceModel home1room1device1 = deviceService.create(new DeviceModel("temp", DeviceType.TEMPERATURE));
        DeviceModel home1room1device2 = deviceService.create(new DeviceModel("hum", DeviceType.HUMIDITY));
        DeviceModel home1room2device1 = deviceService.create(new DeviceModel("light", DeviceType.LIGHT));
        DeviceModel home1room2device2 = deviceService.create(new DeviceModel("socket", DeviceType.SOCKET));
        DeviceModel home1room3device1 = deviceService.create(new DeviceModel("pir", DeviceType.PIR));
        DeviceModel home1room3device2 = deviceService.create(new DeviceModel("humii", DeviceType.HUMIDITY));

        DeviceModel home2room1device1 = deviceService.create(new DeviceModel("temp1", DeviceType.TEMPERATURE));
        DeviceModel home2room1device2 = deviceService.create(new DeviceModel("temp2", DeviceType.TEMPERATURE));
        DeviceModel home2room2device1 = deviceService.create(new DeviceModel("hum", DeviceType.HUMIDITY));
        DeviceModel home2room2device2 = deviceService.create(new DeviceModel("temp_bedroom", DeviceType.TEMPERATURE));
        DeviceModel home2room3device3 = deviceService.create(new DeviceModel("light", DeviceType.LIGHT));

        roomService.addToSubSet(home1room1.getID(), home1room1device1);
        roomService.addToSubSet(home1room1.getID(), home1room1device2);

        roomService.addToSubSet(home1room2.getID(), home1room2device1);
        roomService.addToSubSet(home1room2.getID(), home1room2device2);

        roomService.addToSubSet(home1room3.getID(), home1room3device1);
        roomService.addToSubSet(home1room3.getID(), home1room3device2);

        roomService.addToSubSet(home2room1.getID(), home2room1device1);
        roomService.addToSubSet(home2room1.getID(), home2room1device2);

        roomService.addToSubSet(home2room2.getID(), home2room2device1);
        roomService.addToSubSet(home2room2.getID(), home2room2device2);

        roomService.addToSubSet(home2room3.getID(), home2room3device3);

        deviceService.update(home1room1device1.getID(), home1room1device1);
        deviceService.update(home1room1device2.getID(), home1room1device2);
        deviceService.update(home1room2device1.getID(), home1room2device1);
        deviceService.update(home1room2device2.getID(), home1room2device2);
        deviceService.update(home1room3device1.getID(), home1room3device1);
        deviceService.update(home1room3device2.getID(), home1room3device2);
        deviceService.update(home2room1device1.getID(), home2room1device1);
        deviceService.update(home2room1device2.getID(), home2room1device2);
        deviceService.update(home2room2device1.getID(), home2room2device1);
        deviceService.update(home2room2device2.getID(), home2room2device2);
        deviceService.update(home2room3device3.getID(), home2room3device3);

    }
}
