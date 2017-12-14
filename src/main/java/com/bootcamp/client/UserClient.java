package com.bootcamp.client;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.constants.AppConstant;
import com.bootcamp.entities.Post;
import com.bootcamp.entities.User;
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

public class UserClient implements AppConstant {
    RestTemplate restTemplate;
    PropertiesFileUtils propertiesFileUtils;

    public UserClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate = new RestTemplate(factory);
        propertiesFileUtils= new PropertiesFileUtils();
    }

    public User create(User user) throws IOException {
        String uri= propertiesFileUtils.getAppUrl("user-service-fonctionnel-create-user");

        String requestBody = GsonUtils.toJSONWithoutClassName(user);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity request = new HttpEntity(requestBody, headers);

        String apiResponse = restTemplate.postForObject(uri,
                request, String.class);
        user = GsonUtils.getObjectFromJson(apiResponse, User.class);

        return user;
    }

    public User getById(int id) throws IOException{
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("user-service-fonctionnel-get-user-byid");
        String uriSufix="/"+id;
        uri+=uriSufix;
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        User user = GsonUtils.getObjectFromJson(jsonData,User.class);
        return user;

    }

    public List<User> findAll() throws IOException {
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("user-service-fonctionnel-get-all-user");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        Type typeOfObjectsListNew = new TypeToken<List<User>>() {}.getType();
        List<User> users = GsonUtils.getObjectFromJson(jsonData,typeOfObjectsListNew);

        return users;
    }

}
