package view;

import controller.Controller;
import domain.ProgramState;
import domain.adts.*;
import domain.statement.IStatement;
import domain.value.IValue;
import domain.value.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private Controller controller;
    private ArrayList<IStatement> statements;
    private Scanner input;

    public View(Controller controller, ArrayList<IStatement> statements) {
        this.controller = controller;
        this.statements = statements;
        this.input = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            try {
                PrintMenu();
                System.out.println("Your option is = ");
                int option = input.nextInt();
                input.nextLine();
                if (option == 0)
                    break;
                else if (option == 1)
                    PrintAll();
                else if (option == 2)
                    ExecuteProgram();
                else
                    System.out.println("nonexistent choice");
            } catch (RuntimeException ex) {
                System.out.println("exception happened: " + ex.getMessage());
            }
        }

    }

    private void PrintMenu() {
        System.out.println("0. exit");
        System.out.println("1.Print options");
        System.out.println("2.Run Program");
    }

    private void PrintAll() {
        for (int i = 0; i < this.statements.size(); i++)
            System.out.println(String.valueOf(i + 1) + ": " + this.statements.get(i).toString());
    }

    private void ExecuteProgram() throws RuntimeException
    {
        System.out.println("please enter the program number you want: ");
        int programOption = input.nextInt();
        input.nextLine();
        if(programOption<1 || programOption > this.statements.size())
            throw new RuntimeException("the option does not exist ");
        else
        {
            IStatement statement = this.statements.get(programOption-1);
            IStack<IStatement> executionStack = new MyStack<IStatement>();
            IDictionary<String, IValue> symbolTable = new MyDictionary<String, IValue>();
            IList<IValue> output = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable= new MyDictionary<StringValue,BufferedReader>();
            IHeap heap = new MyHeap();

            ProgramState programState = new ProgramState(executionStack,symbolTable,output,fileTable,statement,heap);
            this.controller.AddProgram(programState);
            this.controller.AllSteps();
        }
    }
}