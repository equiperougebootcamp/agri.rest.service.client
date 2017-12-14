package com.bootcamp;

import com.bootcamp.client.ProduitClient;
import com.bootcamp.entities.Produit;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ProduitClientTest {
    @Test
    public void getProduits() throws IOException {
        ProduitClient produitClient = new ProduitClient();
        List<Produit> produits = produitClient.findAll();
        Gson gson = new Gson();
        for(Produit current:produits){
            String data = gson.toJson(current);
            System.out.println(data);
        }

    }

    @Test
    public void getProduitById() throws IOException {
        ProduitClient produitClient = new ProduitClient();
        Produit produit = produitClient.getById(1);
        Gson gson = new Gson();
        String data = gson.toJson(produit);
        System.out.println(data);
    }
}
