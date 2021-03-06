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

        HomeModel home1 = homeService.create(new HomeModel("Home Brno","broker.cz","flat.jpg"));
        HomeModel home2 = homeService.create(new HomeModel("Cottage Znojmo ", "broker.com","other.jpg"));
        HomeModel home3 = homeService.create(new HomeModel("Home 2","house","house.jpg"));
        HomeModel home4 = homeService.create(new HomeModel("Home 3", "asdfasdf.cz","flat.jpg"));

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


        RoomModel home1room1 = roomService.create(new RoomModel("Kitchen", home1,RoomType.KITCHEN,"kitchen.png"));
        RoomModel home1room2 = roomService.create(new RoomModel("Living room", home1,RoomType.LIVING_ROOM,"livingroom.jpg"));
        RoomModel home1room3 = roomService.create(new RoomModel("Bathroom", home1,RoomType.BATHROOM,"bathroom.png"));

        RoomModel home2room1 = roomService.create(new RoomModel("Room 1", home2,RoomType.ROOM,"room.jpg"));
        RoomModel home2room2 = roomService.create(new RoomModel("Garage", home2,RoomType.GARAGE,"garage.jpg"));
        RoomModel home2room3 = roomService.create(new RoomModel("Bedroom", home2,RoomType.BEDROOM,"bedroom.png"));

        homeService.addToSubSet(home1.getID(), home1room1);
        homeService.addToSubSet(home1.getID(), home1room2);
        homeService.addToSubSet(home1.getID(), home1room3);

        homeService.addToSubSet(home2.getID(), home2room1);
        homeService.addToSubSet(home2.getID(), home2room2);
        homeService.addToSubSet(home2.getID(), home2room3);


        DeviceModel home1room1device1 = deviceService.create(new DeviceModel("Temperature", DeviceType.TEMPERATURE));
        DeviceModel home1room1device2 = deviceService.create(new DeviceModel("Humidity", DeviceType.HUMIDITY));
        DeviceModel home1room1device3 = deviceService.create(new DeviceModel("Socket", DeviceType.SOCKET));
        DeviceModel home1room1device4 = deviceService.create(new DeviceModel("Stats", DeviceType.STATS));
        DeviceModel home1room1device5 = deviceService.create(new DeviceModel("Lights", DeviceType.LIGHT));
        
        
        DeviceModel home1room2device1 = deviceService.create(new DeviceModel("Main lights", DeviceType.LIGHT));
        DeviceModel home1room2device2 = deviceService.create(new DeviceModel("Socket", DeviceType.SOCKET));
        DeviceModel home1room2device3 = deviceService.create(new DeviceModel("Air Conditioner", DeviceType.AIRCONDITIONER));
        DeviceModel home1room2device4 = deviceService.create(new DeviceModel("GAS", DeviceType.GAS));
        
        
        DeviceModel home1room3device1 = deviceService.create(new DeviceModel("Heater", DeviceType.GAS));
        DeviceModel home1room3device2 = deviceService.create(new DeviceModel("Humidity 2", DeviceType.HUMIDITY));
        DeviceModel home1room3device3 = deviceService.create(new DeviceModel("Temperature", DeviceType.TEMPERATURE));

        DeviceModel home2room1device1 = deviceService.create(new DeviceModel("Temperature Outdoor", DeviceType.TEMPERATURE));
        DeviceModel home2room1device2 = deviceService.create(new DeviceModel("Temperature Indoor", DeviceType.TEMPERATURE));
        DeviceModel home2room1device3 = deviceService.create(new DeviceModel("Statistics", DeviceType.STATS));
        DeviceModel home2room1device4 = deviceService.create(new DeviceModel("Heater", DeviceType.HEATER));
        DeviceModel home2room1device5 = deviceService.create(new DeviceModel("Air conditioner", DeviceType.AIRCONDITIONER));
        
        
        DeviceModel home2room2device1 = deviceService.create(new DeviceModel("Humidity", DeviceType.HUMIDITY));
        DeviceModel home2room2device2 = deviceService.create(new DeviceModel("Temperature aquarium", DeviceType.TEMPERATURE));
        DeviceModel home2room2device3 = deviceService.create(new DeviceModel("Statistics", DeviceType.STATS));


        DeviceModel home2room3device1 = deviceService.create(new DeviceModel("Under lights", DeviceType.LIGHT));
        DeviceModel home2room3device2 = deviceService.create(new DeviceModel("Main lights", DeviceType.LIGHT));
        DeviceModel home2room3device3 = deviceService.create(new DeviceModel("Stats", DeviceType.STATS));

        roomService.addToSubSet(home1room1.getID(), home1room1device1);
        roomService.addToSubSet(home1room1.getID(), home1room1device2);
        roomService.addToSubSet(home1room1.getID(), home1room1device3);
        roomService.addToSubSet(home1room1.getID(), home1room1device4);
        roomService.addToSubSet(home1room1.getID(), home1room1device5);


        roomService.addToSubSet(home1room2.getID(), home1room2device1);
        roomService.addToSubSet(home1room2.getID(), home1room2device2);
        roomService.addToSubSet(home1room2.getID(), home1room2device3);
        roomService.addToSubSet(home1room2.getID(), home1room2device4);


        roomService.addToSubSet(home1room3.getID(), home1room3device1);
        roomService.addToSubSet(home1room3.getID(), home1room3device2);
        roomService.addToSubSet(home1room3.getID(), home1room3device3);


        roomService.addToSubSet(home2room1.getID(), home2room1device1);
        roomService.addToSubSet(home2room1.getID(), home2room1device2);
        roomService.addToSubSet(home2room1.getID(), home2room1device3);
        roomService.addToSubSet(home2room1.getID(), home2room1device4);
        roomService.addToSubSet(home2room1.getID(), home2room1device5);


        roomService.addToSubSet(home2room2.getID(), home2room2device1);
        roomService.addToSubSet(home2room2.getID(), home2room2device2);
        roomService.addToSubSet(home2room2.getID(), home2room2device3);
        
        roomService.addToSubSet(home2room3.getID(), home2room3device1);
        roomService.addToSubSet(home2room3.getID(), home2room3device2);
        roomService.addToSubSet(home2room3.getID(), home2room3device3);

        deviceService.update(home1room1device1.getID(), home1room1device1);
        deviceService.update(home1room1device2.getID(), home1room1device2);
        deviceService.update(home1room1device3.getID(), home1room1device3);
        deviceService.update(home1room1device4.getID(), home1room1device4);
        deviceService.update(home1room1device5.getID(), home1room1device5);

        deviceService.update(home1room2device1.getID(), home1room2device1);
        deviceService.update(home1room2device2.getID(), home1room2device2);
        deviceService.update(home1room2device3.getID(), home1room2device3);
        deviceService.update(home1room2device4.getID(), home1room2device4);

        deviceService.update(home1room3device1.getID(), home1room3device1);
        deviceService.update(home1room3device2.getID(), home1room3device2);
        deviceService.update(home1room3device3.getID(), home1room3device3);

        deviceService.update(home2room1device1.getID(), home2room1device1);
        deviceService.update(home2room1device2.getID(), home2room1device2);
        deviceService.update(home2room1device3.getID(), home2room1device3);
        deviceService.update(home2room1device4.getID(), home2room1device4);
        deviceService.update(home2room1device5.getID(), home2room1device5);

        deviceService.update(home2room2device1.getID(), home2room2device1);
        deviceService.update(home2room2device2.getID(), home2room2device2);
        deviceService.update(home2room2device3.getID(), home2room2device3);

        deviceService.update(home2room3device1.getID(), home2room3device1);
        deviceService.update(home2room3device2.getID(), home2room3device2);
        deviceService.update(home2room3device3.getID(), home2room3device3);

    }
}
