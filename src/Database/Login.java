package Database;

public class Login {
    private String email;
    private String password;
    private String session_id;

    //constructor
    public Login(String email, String password, String session_id) {
        this.email = email;
        this.password = password;
        this.session_id = session_id;
    }

    //getter setter
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        return "Register{" +
                ", email='" + email + '\'' +
                ", password=" + password +
                '}';
    }
}
