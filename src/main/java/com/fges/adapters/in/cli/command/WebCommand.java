package com.fges.adapters.in.cli.command;

import com.fges.adapters.in.web.GroceryShopAdapter;
import com.fges.application.port.in.GroceryListService;
import com.fges.adapters.in.web.server.WebServerLauncher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebCommand implements Command {
    private final GroceryListService service;
    private final int webPort;

    @Override
    public int execute() {
        System.out.println("Launching the Web interface on port " + webPort);
        WebServerLauncher launcher = new WebServerLauncher();
        launcher.launch(webPort, new GroceryShopAdapter(service));
        return 0;
    }
}