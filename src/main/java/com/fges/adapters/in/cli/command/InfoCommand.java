package com.fges.adapters.in.cli.command;

import com.fges.domain.SystemInfo;

public class InfoCommand implements Command{


    @Override
    public int execute() {
        System.out.println(new SystemInfo());
        return 0;
    }
}
