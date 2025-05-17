package com.fges.adapters.in.cli.builder;

import com.fges.application.service.GroceryListServiceImpl;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CLIApplicationBuilderTest {

    @Test
    void should_parseArguments_withValidOptions() throws Exception {
        String[] args = {"-s", "list.json", "-f", "json", "-c", "fruits", "add" };
        CLIApplicationBuilder builder = new CLIApplicationBuilder(args)
                .setupOptions()
                .parseArguments();

        assertThat(builder.getParsedArgs()).containsExactly("add");
        assertThat(builder.getCategory()).isEqualTo("fruits");
    }

    @Test
    void should_useDefaultFormatAndCategory_whenNotProvided() throws Exception {
        String[] args = {"list", "-s", "list.json"};
        CLIApplicationBuilder builder = new CLIApplicationBuilder(args)
                .setupOptions()
                .parseArguments();

        assertThat(builder.getCategory()).isEqualTo("default");
    }

    @Test
    void should_throwException_whenSourceMissing() throws Exception {
        String[] args = {"add"};
        CLIApplicationBuilder builder = new CLIApplicationBuilder(args)
                .setupOptions()
                .parseArguments();

        assertThatThrownBy(builder::buildService)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Missing required option");
    }

    @Test
    void should_buildGroceryService_whenAllRequiredOptionsProvided() throws Exception {
        String[] args = { "-s", "grocery.csv", "-f", "csv", "list"};
        CLIApplicationBuilder builder = new CLIApplicationBuilder(args)
                .setupOptions()
                .parseArguments();

        GroceryListServiceImpl service = builder.buildService();

        assertThat(service).isNotNull();
    }
}
