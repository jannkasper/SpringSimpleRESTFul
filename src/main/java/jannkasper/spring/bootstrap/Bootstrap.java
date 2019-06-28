package jannkasper.spring.bootstrap;

import jannkasper.spring.domain.Status;
import jannkasper.spring.domain.User;
import jannkasper.spring.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUser("johny@icloud.com", "johny", "johny1", Status.IN_PROGRESS);
        loadUser("stacy@icloud.com", "stacy", "stacy2", Status.IN_PROGRESS);
        loadUser("smith@gmail.com", "smith", "smith3", Status.ACTIVE);
        loadUser("george@hotmail.com", "george", "george4", Status.REMOVE);
        loadUser("barbra@gmail.com", "barbra", "barbra5", Status.ACTIVE);
        loadUser("jan@gmail.com", "jan", "jan6", Status.IN_PROGRESS);

        System.out.println("Users Loaded: " + userRepository.count());
    }

    private void loadUser(String email, String login, String password, Status status){
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setStatus(status);
        userRepository.save(user);
    }
}
