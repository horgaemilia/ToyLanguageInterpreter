package view.command;

public abstract class Command {
    private String key;
    private String description;
    public Command(String key,String description)
    {
        this.description = description;
        this.key = key;
    }
    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
