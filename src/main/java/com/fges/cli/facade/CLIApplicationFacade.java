package com.fges.cli.facade;

import com.fges.cli.builder.CLIApplicationBuilder;
import com.fges.cli.controller.CLICommandController;
import com.fges.serviceimpl.GroceryListServiceImpl;
import com.fges.valueobject.SystemInfo;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class CLIApplicationFacade {
    private final String[] args;

    public CLIApplicationFacade(String[] args) {
        this.args = args;
    }

    public int run() {
        CLIApplicationBuilder builder;
        GroceryListServiceImpl groceryService;
        String[] remainingArgs;
        List<String> positionalArgs;
        CLICommandController controller;

        try {
            builder = new CLIApplicationBuilder(args)
                    .setupOptions()
                    .parseArguments();

            if ("info".equals(builder.getCommand())) {
                System.out.println(new SystemInfo());
                return 0;
            }

        } catch (ParseException e) {
            System.err.println("Fail to parse arguments: " + e.getMessage());
            return 1;
        }
        try {
            groceryService = builder.buildService();
        } catch (Exception e) {
            System.err.println("Error building service: " + e.getMessage());
            return 1;
        }

        remainingArgs = builder.getParsedArgs();
        positionalArgs = List.of(remainingArgs);

        try {
            controller = new CLICommandController(groceryService, positionalArgs, builder.getCategory());
            return controller.executeCommand();
        } catch (NullPointerException e) {
            System.err.println("Controller returned null: " + e.getMessage());
            return 1;
        } catch (Exception e) {
            System.err.println("Unexpected error during execution: " + e.getMessage());
            return 1;
        }
    }
}