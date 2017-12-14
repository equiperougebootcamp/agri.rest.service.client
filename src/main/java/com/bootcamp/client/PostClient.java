package com.bootcamp.client;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.constants.AppConstant;
import com.bootcamp.entities.Post;
import com.bootcamp.utils.PropertiesFileUtils;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PostClient implements AppConstant {
    RestTemplate restTemplate;
    PropertiesFileUtils propertiesFileUtils;

    public PostClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate = new RestTemplate(factory);
        propertiesFileUtils= new PropertiesFileUtils();
    }

    public List<Post> findAll() throws IOException {
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("post-service-fonctionnel-get-all-post");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        Type typeOfObjectsListNew = new TypeToken<List<Post>>() {}.getType();
        List<Post> posts = GsonUtils.getObjectFromJson(jsonData,typeOfObjectsListNew);

        return posts;
    }

    public Post create(Post post) throws IOException {
        String uri= propertiesFileUtils.getAppUrl("post-service-fonctionnel-create-post");

        String requestBody = GsonUtils.toJSONWithoutClassName(post);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity request = new HttpEntity(requestBody, headers);

        String apiResponse = restTemplate.postForObject(uri,
                request, String.class);
        post = GsonUtils.getObjectFromJson(apiResponse, Post.class);

        return post;
    }

    public Post getById(int id) throws IOException{
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("post-service-fonctionnel-get-post-byid");
        String uriSufix="/"+id;
        uri+=uriSufix;
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        Post post = GsonUtils.getObjectFromJson(jsonData,Post.class);
        return post;

    }

}
