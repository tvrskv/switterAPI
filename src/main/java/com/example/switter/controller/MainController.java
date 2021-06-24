package com.example.switter.controller;

import com.example.switter.domain.Message;
import com.example.switter.domain.User;
import com.example.switter.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepos messageRepos;

    // для проверки, существует ли директория файлов
    // указываем спрингу, что хотим получить переменную из properties.path
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepos.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepos.findByTag(filter); // метод findByTag возвращает List
        } else {
            messages = messageRepos.findAll(); // метод findAll возвращает Iterable
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file) throws IOException {

        Message message = new Message(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) { // сохраняем файл только если у него задано имя файлв
           File uploadDir = new File(uploadPath);

           if (uploadDir.exists()) {
               uploadDir.mkdir(); // создание uploadDir
           }

            String uuidFile = UUID.randomUUID().toString(); // уникальный идентификационный номер
            String resultFilename = uuidFile + "," + file.getOriginalFilename(); //

            file.transferTo(new File(uploadPath + "/" + resultFilename)); // загрузить файл
            message.setFilename(resultFilename);
        }

        messageRepos.save(message);

        Iterable<Message> messages = messageRepos.findAll();
        model.put("messages", messages);

        return "main";
    }
}