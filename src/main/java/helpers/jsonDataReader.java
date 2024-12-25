package helpers;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import testData.LoginData;
public class jsonDataReader {

    public static LoginData getLoginData() {
        Gson gson = new Gson();
        LoginData loginData = null;

        try {
            FileReader reader = new FileReader("D:\\testing\\SwagProject\\src\\main\\java\\testData\\login.json");
            loginData = gson.fromJson(reader, LoginData.class);
        } catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return loginData;
    }
}
