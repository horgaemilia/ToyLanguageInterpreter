package view.command;

import controller.Controller;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String key,String description,Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute()
    {
        try
        {
            controller.AllSteps();
        }
        catch (RuntimeException ex)
        {
            System.out.println("exception happened: " + ex.getMessage());
        }
    }
}
