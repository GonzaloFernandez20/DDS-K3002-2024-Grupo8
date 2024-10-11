
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "java")
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}