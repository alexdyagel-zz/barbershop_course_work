package by.dyagel.controller.commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Interface for implementation of Command Pattern
 */
public interface ICommand {
    void execute(ObjectInputStream ois, ObjectOutputStream oos) throws IOException;
}