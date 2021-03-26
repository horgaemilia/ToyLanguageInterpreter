package view;

import domain.adts.IDictionary;
import view.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;
    public TextMenu()
    {
        commands = new HashMap<>();
    }

    public void AddCommand(Command c)
    {
        commands.put(c.getKey(),c);
    }

    private void PrintMenu()
    {
        for(Command cmd:commands.values())
        {
            String line=String.format("%4s : %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }
    }

    public void Show()
    {
        Scanner scanner=new Scanner(System.in);
        while(true) {
            PrintMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println(" this option does not exist");
                continue;
            }
            com.execute();
        }
    }
}
