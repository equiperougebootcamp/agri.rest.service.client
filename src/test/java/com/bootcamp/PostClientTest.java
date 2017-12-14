package com.bootcamp;

import com.bootcamp.client.PostClient;
import com.bootcamp.entities.Post;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class PostClientTest {
    @Test
    public void getPosts() throws IOException {
        PostClient postClient = new PostClient();
        List<Post> posts = postClient.findAll();
        Gson gson = new Gson();
        for(Post current:posts){
            String data = gson.toJson(current);
            System.out.println(data);
        }

    }

    @Test
    public void getPostById() throws IOException {
        PostClient postClient = new PostClient();
        Post post = postClient.getById(1);
        Gson gson = new Gson();
        String data = gson.toJson(post);
        System.out.println(data);
    }
}
