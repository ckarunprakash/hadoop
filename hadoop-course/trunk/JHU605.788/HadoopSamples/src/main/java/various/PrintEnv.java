package various;

import java.util.Map;

public class PrintEnv {
    public static void main(String[] args) {
        for(Map.Entry<String,String> env : System.getenv().entrySet()){
            System.out.println(env);
        }
    }
}
