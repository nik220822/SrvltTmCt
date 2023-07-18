package ru.nicode.repository;

import ru.nicode.exception.NotFoundException;
import ru.nicode.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final ConcurrentHashMap<Long, Post> postsMap = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);
    private final Post zeroPost = new Post(-1, "You cannot update a non-existent post");

    public List<Post> all() {
        ArrayList<Post> postsList = new ArrayList<>();
        for (long key : postsMap.keySet()) {
            postsList.add(postsMap.get(key));
        }
        return postsList;
    }

    public Optional<Post> getById(long id) {
        /*if (!postsMap.containsKey(id)) {
            return Optional.of(zeroPost);
        }*/
        return Optional.of(postsMap.get(id));
    }

    public Post save(Post post) {
        if (!postsMap.containsKey(post.getId())) {
            if (post.getId() > 0) {
                return zeroPost;
            }
        }
        if (post.getId() <= 0) {
            id.set(id.get() + 1);
            post.setId(id.get());
        }
        postsMap.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        if (!postsMap.containsKey(id)) {
            throw new NotFoundException();
        } else {
            postsMap.remove(id);
        }
    }
}
