package com.fges.adapters.in.web.server;

import com.fges.adapters.in.web.GroceryShopAdapter;
import fr.anthonyquere.GroceryShopServer;

public class WebServerLauncher {

    public void launch(int port, GroceryShopAdapter adapter) {
        GroceryShopServer server = new GroceryShopServer(adapter);
        server.start(port);
        System.out.println("Serveur web lancé sur http://localhost:" + port);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}