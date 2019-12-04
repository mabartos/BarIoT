
public class User {
    long id;
    String username;
    String password;
    String firstname;
    String lastname;
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
