package edu.neu.ccs.cs5004;

import java.util.List;

public interface IArgParser {

    static List<Object> parseArgs(List<String> args) throws WrongExtensionType, IllegalArgumentException{
        return null;
    }

    static boolean hasFile(String nextArg) {
        return false;
    }

    static String checkExtension(String args) throws WrongExtensionType {
        return null;
    }

    static String checkExtensionCsv(String args) throws WrongExtensionType {
        return null;
    }

    static String getFileExtension(String fileName) throws WrongExtensionType {
        return null;
    }

}
