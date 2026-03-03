package courier;

public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials() {
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials withLoginOnly(String password) {
        return new CourierCredentials(null, password);
    }

    public static CourierCredentials withPasswordOnly(String password) {
        return new CourierCredentials(null, password);
    }
}