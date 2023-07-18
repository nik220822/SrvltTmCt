package ru.nicode.servlet;

import ru.nicode.controller.PostController;
import ru.nicode.repository.PostRepository;
import ru.nicode.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private final String DELETE = "DELETE";
    private final String GET = "GET";
    private final String POST = "POST";
    private final String commonPath = "/api/posts";
    private PostRepository repository;
    private PostService service;
    private PostController controller;

    @Override
    public void init() {
        repository = new PostRepository();
        service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(GET) && path.equals(commonPath)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET) && path.matches(commonPath + "/\\d+")) {
                // easy way
                final var id = IdFrom(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST) && path.equals(commonPath)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE) && path.matches(commonPath + "/\\d+")) {
                // easy way
                final var id = IdFrom(path);
                controller.removeById(id, resp);
                return;
            }
//            resp.getWriter().print("Hi!");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long IdFrom(String path) {
//        String[] parts = path.split("/");
//        return Long.parseLong(parts[parts.length - 1]);
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

