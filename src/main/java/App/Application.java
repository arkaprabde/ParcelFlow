package App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@EnableJpaRepositories("App.Repos")
@SpringBootApplication
public class Application
{
	static
	{
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
	}
	
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
