package Database;

public class Login {
    private String email;
    private String password;
    private String session_id;
    private String status;

    //constructor
    public Login(String email, String password, String session_id, String status) {
        this.email = email;
        this.password = password;
        this.session_id = session_id;
        this.status = status;
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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", session_id='" + session_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
