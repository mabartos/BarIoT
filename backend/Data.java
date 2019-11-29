
public class User {
    long id;
    String userName;
    String password;
    String firstName;
    String lastName;
    String email;
}

public class Home {
    long id;
    String name;
    String brokerUrl;

}

public class Room {
    long id;
    String name;
    Home home;
}

public class Device {
    long id;
    String name;
    Room room;
}
