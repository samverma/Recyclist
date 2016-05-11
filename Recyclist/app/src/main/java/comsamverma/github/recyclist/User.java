package comsamverma.github.recyclist;

/**
 * Created by sam on 3/23/2016.
 */
public class User {
    public int id;
    public String email;
    public String firstname;
    public String lastname;
    public boolean banned;
    public String compname;
    public String compinfo;
    public String comppurp;
    public String complicnum;
    public boolean enotify;
    public String token;

    public User(String e, String t){
        this.email = e;
        this.token = t;
    }
}
