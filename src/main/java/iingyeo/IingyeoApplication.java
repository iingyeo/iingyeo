package iingyeo;

import iingyeo.entity.User;
import iingyeo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class IingyeoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(IingyeoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();

        userRepository.save(new User("taemyung", "heo", "tmheo74@gmail.com", new Date()));
        userRepository.save(new User("minseo", "heo", "minseo@gmail.com", new Date()));
        userRepository.save(new User("jungwoo", "heo", "jungwoo@gmail.com", new Date()));

        System.out.println("Users found with findAll():");
        System.out.println("-------------------------------");
        for (User user : userRepository.findAll()) {
            System.out.println(user);
        }

        System.out.println();
        System.out.println(userRepository.findByEmailAddress("tmheo74@gmail.com"));
    }
}
