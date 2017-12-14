package com.bootcamp.client;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.constants.AppConstant;
import com.bootcamp.entities.Produit;
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

public class ProduitClient implements AppConstant {
    RestTemplate restTemplate;
    PropertiesFileUtils propertiesFileUtils;

    public ProduitClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate = new RestTemplate(factory);
        propertiesFileUtils= new PropertiesFileUtils();
    }

    public List<Produit> findAll() throws IOException {
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("produit-service-fonctionnel-get-all-produit");
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        Type typeOfObjectsListNew = new TypeToken<List<Produit>>() {}.getType();
        List<Produit> produits = GsonUtils.getObjectFromJson(jsonData,typeOfObjectsListNew);

        return produits;
    }

    public Produit create(Produit produit) throws IOException {
        String uri= propertiesFileUtils.getAppUrl("produit-service-fonctionnel-create-produit");

        String requestBody = GsonUtils.toJSONWithoutClassName(produit);
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        HttpEntity request = new HttpEntity(requestBody, headers);

        String apiResponse = restTemplate.postForObject(uri,
                request, String.class);
        produit = GsonUtils.getObjectFromJson(apiResponse, Produit.class);

        return produit;
    }

    public Produit getById(int id) throws IOException{
        propertiesFileUtils= new PropertiesFileUtils();
        String uri=propertiesFileUtils.getAppUrl("produit-service-fonctionnel-get-produit-byid");
        String uriSufix="/"+id;
        uri+=uriSufix;
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        String jsonData = response.getBody();
        Produit produit = GsonUtils.getObjectFromJson(jsonData,Produit.class);
        return produit;

    }

}
