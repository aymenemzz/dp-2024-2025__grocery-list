package com.fges.adapters.in.cli.facade;

import com.fges.adapters.in.cli.builder.CLIApplicationBuilder;
import com.fges.adapters.in.cli.controller.CLICommandController;
import com.fges.application.service.GroceryListServiceImpl;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CLIApplicationFacadeTest {

    @Test
    void should_return1_whenBuilderFailsToParse() {
        String[] args = {"add", "-s"};

        try (MockedConstruction<CLIApplicationBuilder> mocked = mockConstruction(CLIApplicationBuilder.class,
                (mock, context) -> {
                    when(mock.setupOptions()).thenReturn(mock);
                    when(mock.parseArguments()).thenThrow(new ParseException("error"));
                })) {

            int result = new CLIApplicationFacade(args).run();

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    void should_return1_whenServiceConstructionFails() throws Exception {
        String[] args = {"add", "-s", "list.json"};
        try (MockedConstruction<CLIApplicationBuilder> mocked = mockConstruction(CLIApplicationBuilder.class,
                (mock, context) -> {
                    when(mock.setupOptions()).thenReturn(mock);
                    when(mock.parseArguments()).thenReturn(mock);
                    when(mock.buildService()).thenThrow(new RuntimeException("fail"));
                })) {

            int result = new CLIApplicationFacade(args).run();

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    void should_return1_whenControllerFails() throws Exception {
        String[] args = {"add", "-s", "list.json"};
        GroceryListServiceImpl mockService = mock(GroceryListServiceImpl.class);

        try (MockedConstruction<CLIApplicationBuilder> builderMock = mockConstruction(CLIApplicationBuilder.class,
                (mock, context) -> {
                    when(mock.setupOptions()).thenReturn(mock);
                    when(mock.parseArguments()).thenReturn(mock);
                    when(mock.buildService()).thenReturn(mockService);
                    when(mock.getParsedArgs()).thenReturn(new String[]{"add"});
                    when(mock.getCategory()).thenReturn("fruits");
                });
             MockedConstruction<CLICommandController> controllerMock = mockConstruction(CLICommandController.class,
                (mock, context) -> when(mock.executeCommand()).thenThrow(new RuntimeException("fail")))) {

            int result = new CLIApplicationFacade(args).run();

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    void should_returnCommandResult_whenExecutionSucceeds() throws Exception {
        String[] args = {"list", "-s", "data.json"};
        GroceryListServiceImpl mockService = mock(GroceryListServiceImpl.class);

        try (MockedConstruction<CLIApplicationBuilder> builderMock = mockConstruction(CLIApplicationBuilder.class,
                (mock, context) -> {
                    when(mock.setupOptions()).thenReturn(mock);
                    when(mock.parseArguments()).thenReturn(mock);
                    when(mock.buildService()).thenReturn(mockService);
                    when(mock.getParsedArgs()).thenReturn(new String[]{"list"});
                    when(mock.getCategory()).thenReturn("default");
                });
             MockedConstruction<CLICommandController> controllerMock = mockConstruction(CLICommandController.class,
                (mock, context) -> when(mock.executeCommand()).thenReturn(0))) {

            int result = new CLIApplicationFacade(args).run();

            assertThat(result).isEqualTo(0);
        }
    }
}
