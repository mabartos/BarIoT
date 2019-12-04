
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
}

public class Device {
    long id;
    String name;
    DeviceType type; // enum
}

public enum DeviceType {
    NONE,
    TEMPERATURE,
    HUMIDITY,
    LIGHT,
    RELAY,
    SWITCH,
    PIR,
    GAS
}
