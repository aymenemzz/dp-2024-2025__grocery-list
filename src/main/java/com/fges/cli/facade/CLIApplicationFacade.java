package com.fges.cli.facade;

import com.fges.cli.builder.CLIApplicationBuilder;
import com.fges.cli.controller.CLICommandController;
import com.fges.serviceimpl.GroceryListServiceImpl;
import org.apache.commons.cli.ParseException;

import java.util.List;

public class CLIApplicationFacade {
    private final String[] args;

    public CLIApplicationFacade(String[] args) {
        this.args = args;
    }

    public int run() {
        try {
            CLIApplicationBuilder builder = new CLIApplicationBuilder(args)
                    .setupOptions()
                    .parseArguments();

            GroceryListServiceImpl groceryService = builder.buildService();

            // Récupérer uniquement les arguments non-optionnels après parsing
            String[] remainingArgs = builder.getParsedArgs();
            List<String> positionalArgs = List.of(remainingArgs);

            // Debugging: Vérifier ce que l'on envoie au controller
            System.err.println("Arguments finaux envoyés au controller: " + positionalArgs);

            CLICommandController controller = new CLICommandController(groceryService, positionalArgs, builder.getCategory());
            return controller.executeCommand();

        } catch (ParseException e) {
            System.err.println("Fail to parse arguments: " + e.getMessage());
            return 1;
        }
    }
}