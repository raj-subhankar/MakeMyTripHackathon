package com.yellowsoft.yellow.events;

import com.yellowsoft.yellow.model.pojo.Post;

import java.util.List;

/**
 * Created by subhankar on 10/11/2016.
 */
public class NewPostsEvent {
    List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public NewPostsEvent(List<Post> newPosts) {
        this.posts = newPosts;
    }
}
