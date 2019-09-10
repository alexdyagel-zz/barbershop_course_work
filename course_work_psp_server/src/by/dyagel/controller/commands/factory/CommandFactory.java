package by.dyagel.controller.commands.factory;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.commands.order.AddOrderCommand;
import by.dyagel.controller.commands.order.DeleteOrderCommand;
import by.dyagel.controller.commands.order.GetOrdersCommand;
import by.dyagel.controller.commands.seance.AddSeanceCommand;
import by.dyagel.controller.commands.seance.DeleteSeanceCommand;
import by.dyagel.controller.commands.seance.FindAvailableSeancesCommand;
import by.dyagel.controller.commands.seance.GetSeancesCommand;
import by.dyagel.controller.commands.services.AddServiceCommand;
import by.dyagel.controller.commands.services.DeleteServiceCommand;
import by.dyagel.controller.commands.services.GetServicesCommand;
import by.dyagel.controller.commands.services.UpdateServiceCommand;
import by.dyagel.controller.commands.specialists.AddSpecialistCommand;
import by.dyagel.controller.commands.specialists.DeleteSpecialistCommand;
import by.dyagel.controller.commands.specialists.GetSpecialistsCommand;
import by.dyagel.controller.commands.specialists.UpdateSpecialistCommand;
import by.dyagel.controller.commands.user.*;
import by.dyagel.controller.messages.UserMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for creating server commands
 * implements factory method
 */
public class CommandFactory {
    private Map<UserMessage, ICommand> commands;

    public CommandFactory() {
        commands = new HashMap<>();
        commands.put(UserMessage.SIGN_IN, new SignInCommand());
        commands.put(UserMessage.SIGN_UP, new SignUpCommand());
        commands.put(UserMessage.GET_USERS, new GetUsersCommand());
        commands.put(UserMessage.DELETE_USER, new DeleteUserCommand());
        commands.put(UserMessage.UPDATE_USER, new UpdateUserCommand());
        commands.put(UserMessage.GET_SERVICES, new GetServicesCommand());
        commands.put(UserMessage.DELETE_SERVICE, new DeleteServiceCommand());
        commands.put(UserMessage.ADD_SERVICE, new AddServiceCommand());
        commands.put(UserMessage.UPDATE_SERVICE, new UpdateServiceCommand());
        commands.put(UserMessage.GET_SPECIALISTS, new GetSpecialistsCommand());
        commands.put(UserMessage.DELETE_SPECIALIST, new DeleteSpecialistCommand());
        commands.put(UserMessage.ADD_SPECIALIST, new AddSpecialistCommand());
        commands.put(UserMessage.UPDATE_SPECIALIST, new UpdateSpecialistCommand());
        commands.put(UserMessage.GET_SEANCES, new GetSeancesCommand());
        commands.put(UserMessage.DELETE_SEANCE, new DeleteSeanceCommand());
        commands.put(UserMessage.ADD_SEANCE, new AddSeanceCommand());
        commands.put(UserMessage.FIND_AVAILABLE_SEANCES_OF_SPECIALIST, new FindAvailableSeancesCommand());
        commands.put(UserMessage.ADD_ORDER, new AddOrderCommand());
        commands.put(UserMessage.GET_ORDERS, new GetOrdersCommand());
        commands.put(UserMessage.DELETE_ORDER, new DeleteOrderCommand());
    }

    public ICommand createCommand(UserMessage command) {
        return commands.getOrDefault(command, null);
    }
}
