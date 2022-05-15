package ru.potatocoder228.itmo.lab6.connection;

import ru.potatocoder228.itmo.lab6.data.Dragon;

import java.io.Serializable;

public class CommandMsg implements Request{
        private String commandName;
        private String commandStringArgument;
        private Serializable commandObjectArgument;

        public CommandMsg(String commandNm, String commandSA, Serializable commandOA){
            commandName = commandNm;
            commandObjectArgument = commandOA;
            commandStringArgument = commandSA;
        }

        public String getCommandName(){
            return commandName;
        }
        public String getStringArg(){
            return commandStringArgument;
        }
        public Dragon getDragon(){
            return (Dragon) commandObjectArgument;
        }
}
