package com.example.switter.service;

import com.example.switter.domain.Role;
import com.example.switter.domain.User;
import com.example.switter.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        // поиск пользователя
        User userFromDB = userRepo.findByUsername(user.getUsername());

        //если данный пользователь существует, мы возвращаем false, что означает, что пользователь не добавлен
        if (userFromDB != null) {
            return false;
        }
        //если пользователя нет
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString()); // код активации

        userRepo.save(user); // сохранение пользователя

        sendMessage(user);

        return true; // true - пользователь создан
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) { // проверет строчки, чтобы они не были пустыми и не равнялись нулю
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Switter. Please, click on the link below to activate your profile \n" +
                            "http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        // искать пользователя по коду активации в репозитории
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null); // пользователь подтвердил свой почтовый ящик

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username); // установим пользователю новое имя

        // получение списка ролей
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        // данная форма содержит роли для пользователя

        user.getRoles().clear();

        for (String key : form.keySet()) { // итерация по списку ключей
            if (roles.contains(key)) { // фильтрация
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user); // сохраняем пользователя
    }

    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail(); // возьмем текущий email

        // проверим, что он изменился
        boolean isEmailChanged = (email != null && !email.equals(userEmail));

        // если email изменился, то обновляем его у пользователя
        if (isEmailChanged) {
            user.setEmail(email);

            // если пользователь установил новый email, то пользователь получает новый activation code
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        // проверяем, если пользователь установил новый пароль, устанавливаем его
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

        // если email изменился, высылаем activation code
        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}
