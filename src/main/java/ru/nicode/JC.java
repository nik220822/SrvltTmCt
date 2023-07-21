package ru.nicode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nicode.controller.PostController;
import ru.nicode.repository.PostRepository;
import ru.nicode.service.PostService;

@Configuration
public class JC {
    @Bean
    public PostController postController() {
        return new PostController(postService());
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}
