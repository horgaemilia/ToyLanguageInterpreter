package first;

import controller.Controller;
import domain.ProgramState;
import domain.adts.*;
import domain.expression.*;
import domain.statement.*;
import domain.type.*;
import domain.value.BoolValue;
import domain.value.IValue;
import domain.value.IntValue;
import domain.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.View;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Interpreter {
    public static void main(String[] args)
    {
/*
        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
            new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))),
                    new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                            new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                    new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        IStack<IStatement> executionStack1 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable1 = new MyDictionary<String, IValue>();
        IList<IValue> output1 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable1= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap1 = new MyHeap();
        ProgramState prg1 = new ProgramState(executionStack1,symbolTable1,output1,fileTable1,example1,heap1);
        IRepository repo1 = new Repository("C:\\Users\\PC\\Desktop\\map\\log1.txt");
        repo1.AddProgram(prg1);
        Controller controller1 = new Controller(repo1);

        IStatement example2 =  new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStack<IStatement> executionStack2 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable2 = new MyDictionary<String, IValue>();
        IList<IValue> output2 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable2= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap2 = new MyHeap();
        ProgramState prg2 = new ProgramState(executionStack2,symbolTable2,output2,fileTable2,example2,heap2);
        IRepository repo2 = new Repository("C:\\Users\\PC\\Desktop\\map\\log2.txt");
        repo2.AddProgram(prg2);
        Controller controller2 = new Controller(repo2);

        IStatement example3 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v"))))));
        IStack<IStatement> executionStack3 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable3 = new MyDictionary<String, IValue>();
        IList<IValue> output3 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable3= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap3 = new MyHeap();
        ProgramState prg3 = new ProgramState(executionStack3,symbolTable3,output3,fileTable3,example3,heap3);
        IRepository repo3 = new Repository("C:\\Users\\PC\\Desktop\\map\\log3.txt");
        repo3.AddProgram(prg3);
        Controller controller3 = new Controller(repo3);


        IStatement example4 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new BoolValue(true))),
                        new PrintStatement(new VariableExpression("v"))));
        IStack<IStatement> executionStack4 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable4 = new MyDictionary<String, IValue>();
        IList<IValue> output4 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable4= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap4 = new MyHeap();
        ProgramState prg4 = new ProgramState(executionStack4,symbolTable4,output4,fileTable4,example4,heap4);
        IRepository repo4 = new Repository("C:\\Users\\PC\\Desktop\\map\\log4.txt");
        repo4.AddProgram(prg4);
        Controller controller4 = new Controller(repo4);

        IStatement example5 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(5))),
                        new PrintStatement(new VariableExpression("d"))));
        IStack<IStatement> executionStack5 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable5 = new MyDictionary<String, IValue>();
        IList<IValue> output5 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable5= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap5 = new MyHeap();
        ProgramState prg5 = new ProgramState(executionStack5,symbolTable5,output5,fileTable5,example5,heap5);
        IRepository repo5 = new Repository("C:\\Users\\PC\\Desktop\\map\\log5.txt");
        repo5.AddProgram(prg5);
        Controller controller5 = new Controller(repo5);


        IStatement example6 =  new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('.',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IStack<IStatement> executionStack6 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable6 = new MyDictionary<String, IValue>();
        IList<IValue> output6 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable6= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap6 = new MyHeap();
        ProgramState prg6 = new ProgramState(executionStack6,symbolTable6,output6,fileTable6,example6,heap6);
        IRepository repo6 = new Repository("C:\\Users\\PC\\Desktop\\map\\log6.txt");
        repo6.AddProgram(prg6);
        Controller controller6 = new Controller(repo6);

        IStatement example7 = new CompoundStatement(new VariableDeclarationStatement("variableFile", new StringType()),
                new CompoundStatement(new AssignStatement("variableFile", new ValueExpression(new StringValue("C:\\Users\\PC\\Desktop\\map\\testIn.txt"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("variableFile")),
                                new CompoundStatement(new VariableDeclarationStatement("variableInt", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("variableFile"), "variableInt"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("variableInt")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("variableFile"), "variableInt"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("variableInt")),
                                                                        new CloseRFile(new VariableExpression("variableFile"))))))))));
        IStack<IStatement> executionStack7 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable7 = new MyDictionary<String, IValue>();
        IList<IValue> output7 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable7= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap7 = new MyHeap();
        ProgramState prg7 = new ProgramState(executionStack7,symbolTable7,output7,fileTable7,example7,heap7);
        IRepository repo7 = new Repository("C:\\Users\\PC\\Desktop\\map\\log7.txt");
        repo7.AddProgram(prg7);
        Controller controller7 = new Controller(repo7);



        IStatement example8 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),new CompoundStatement(new AssignStatement("a",new RelationalExpression(">",new ValueExpression(new IntValue(4)),new ValueExpression(new IntValue(5)))),
                new PrintStatement(new VariableExpression("a"))));
        IStack<IStatement> executionStack8 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable8 = new MyDictionary<String, IValue>();
        IList<IValue> output8 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable8= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap8 = new MyHeap();
        ProgramState prg8 = new ProgramState(executionStack8,symbolTable8,output8,fileTable8,example8,heap8);
        IRepository repo8 = new Repository("C:\\Users\\PC\\Desktop\\map\\log8.txt");
        repo8.AddProgram(prg8);
        Controller controller8 = new Controller(repo8);

        IStatement example9 = new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),new CompoundStatement(new AssignStatement("a",new RelationalExpression(">.",new ValueExpression(new IntValue(4)),new ValueExpression(new IntValue(5)))),
                new PrintStatement(new VariableExpression("a"))));
        IStack<IStatement> executionStack9 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable9 = new MyDictionary<String, IValue>();
        IList<IValue> output9 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable9= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap9 = new MyHeap();
        ProgramState prg9 = new ProgramState(executionStack9,symbolTable9,output9,fileTable9,example9,heap9);
        IRepository repo9 = new Repository("C:\\Users\\PC\\Desktop\\map\\log9.txt");
        repo9.AddProgram(prg9);
        Controller controller9 = new Controller(repo9);



        IStatement example10 = new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(5))),
                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("b"))),
                                new CompoundStatement(new WriteHeap("a", new ValueExpression(new IntValue(15))),
                                        new PrintStatement(new ArithmeticExpression('-',
                                                new ReadHeap(new VariableExpression("a")),
                                                new ValueExpression(new IntValue(5))))))));

        IStack<IStatement> executionStack10 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable10 = new MyDictionary<String, IValue>();
        IList<IValue> output10 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable10= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap10 = new MyHeap();
        ProgramState prg10 = new ProgramState(executionStack10,symbolTable10,output10,fileTable10,example10,heap10);
        IRepository repo10 = new Repository("C:\\Users\\PC\\Desktop\\map\\log10.txt");
        repo10.AddProgram(prg10);
        Controller controller10 = new Controller(repo10);

        IStatement example11 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(".",new VariableExpression("v"),new ValueExpression(new IntValue(0))),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('-',
                        new VariableExpression("v"),new ValueExpression(new IntValue(1))))))));

        IStack<IStatement> executionStack11 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable11 = new MyDictionary<String, IValue>();
        IList<IValue> output11 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable11= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap11 = new MyHeap();
        ProgramState prg11 = new ProgramState(executionStack11,symbolTable11,output11,fileTable11,example11,heap11);
        IRepository repo11 = new Repository("C:\\Users\\PC\\Desktop\\map\\log11.txt");
        repo11.AddProgram(prg11);
        Controller controller11 = new Controller(repo11);



        IStatement example12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeap("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeap(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));

        IStack<IStatement> executionStack12 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable12 = new MyDictionary<String, IValue>();
        IList<IValue> output12 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable12= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap12 = new MyHeap();
        ProgramState prg12 = new ProgramState(executionStack12,symbolTable12,output12,fileTable12,example12,heap12);
        IRepository repo12 = new Repository("C:\\Users\\PC\\Desktop\\map\\log12.txt");
        repo12.AddProgram(prg12);
        Controller controller12 = new Controller(repo12);

        IStatement example13 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new AssignStatement("a",new ValueExpression(new IntValue(2))),
                new CompoundStatement(new VariableDeclarationStatement("b",new RefType(new IntType())),
                        new CompoundStatement(new NewStatement("b",new ValueExpression(new IntValue(19))), new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new AssignStatement("c",new ValueExpression(new IntValue(12))),new CompoundStatement(
                                        new PrintStatement(new VariableExpression("a")),
                                        new ForkStatement(new CompoundStatement(new AssignStatement("a",new ValueExpression(new IntValue(4))),new PrintStatement(new VariableExpression("a"))))))), new CompoundStatement( new PrintStatement(new VariableExpression("a")),new PrintStatement(
                                                new ReadHeap(new VariableExpression("b")))
                        )
                                        )))));
        IStack<IStatement> executionStack13 = new MyStack<IStatement>();
        IDictionary<String, IValue> symbolTable13 = new MyDictionary<String, IValue>();
        IList<IValue> output13 = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable13= new MyDictionary<StringValue,BufferedReader>();
        IHeap heap13 = new MyHeap();
        ProgramState prg13 = new ProgramState(executionStack13,symbolTable13,output13,fileTable13,example13,heap13);
        IRepository repo13 = new Repository("C:\\Users\\PC\\Desktop\\map\\log13.txt");
        repo13.AddProgram(prg13);
        Controller controller13 = new Controller(repo13);


        TextMenu menu = new TextMenu();
        menu.AddCommand(new ExitCommand("0", "exit"));
        menu.AddCommand(new RunExample("1",example1.toString(),controller1));
        menu.AddCommand(new RunExample("2",example2.toString(),controller2));
        menu.AddCommand(new RunExample("3",example3.toString(),controller3));
        menu.AddCommand(new RunExample("5",example12.toString(),controller12));
        menu.AddCommand(new RunExample("6",example6.toString(),controller6));
        menu.AddCommand(new RunExample("7",example7.toString(),controller7));
        menu.AddCommand(new RunExample("8",example8.toString(),controller8));
        menu.AddCommand(new RunExample("9",example9.toString(),controller9));
        menu.AddCommand(new RunExample("10",example10.toString(),controller10));
        menu.AddCommand(new RunExample("4",example11.toString(),controller11));
        menu.AddCommand(new RunExample("13",example13.toString(),controller13));

        menu.Show();
*/
        Assignment6Examples();
    }

    /*
    public static void main(String[] args)
    {
        ArrayList<IStatement> statements = new ArrayList<>();
        createStatements(statements);
        IRepository repo = new Repository("random.txt");
        Controller controller = new Controller(repo);
        View view = new View(controller, statements);
        view.run();
    }
    */

    public static void Assignment6Examples()
    {

        TextMenu menu = new TextMenu();
        menu.AddCommand(new ExitCommand("0", "exit"));

        IStatement example2 =  new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new BoolType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('+',new ValueExpression(new IntValue(2)),new
                                ArithmeticExpression('*',new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new ArithmeticExpression('+',new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));
        IDictionary<String, IType> typeEnv1 = new MyDictionary<>();
        try
        {
            System.out.println(example2.toString());
            example2.typecheck(typeEnv1);
            IStack<IStatement> executionStack2 = new MyStack<IStatement>();
            IDictionary<String, IValue> symbolTable2 = new MyDictionary<String, IValue>();
            IList<IValue> output2 = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<StringValue, BufferedReader>();
            IHeap heap2 = new MyHeap();
            ProgramState prg2 = new ProgramState(executionStack2, symbolTable2, output2, fileTable2, example2, heap2);
            IRepository repo2 = new Repository("C:\\Users\\PC\\Desktop\\map\\log2.txt");
            repo2.AddProgram(prg2);
            Controller controller2 = new Controller(repo2);
            menu.AddCommand(new RunExample("2",example2.toString(),controller2));
        }
        catch (RuntimeException ex)
        {
            System.out.println(ex.getMessage());
        }

        IStatement example1 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new BoolValue(true))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        IDictionary<String, IType> typeEnv2 = new MyDictionary<>();
        try
        {
            System.out.println(example1);
            example1.typecheck(typeEnv2);
            IStack<IStatement> executionStack1 = new MyStack<IStatement>();
            IDictionary<String, IValue> symbolTable1 = new MyDictionary<String, IValue>();
            IList<IValue> output1 = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<StringValue, BufferedReader>();
            IHeap heap1 = new MyHeap();
            ProgramState prg1 = new ProgramState(executionStack1, symbolTable1, output1, fileTable1, example1, heap1);
            IRepository repo1 = new Repository("C:\\Users\\PC\\Desktop\\map\\log1.txt");
            repo1.AddProgram(prg1);
            Controller controller1 = new Controller(repo1);
            menu.AddCommand(new RunExample("1",example1.toString(),controller1));
        }
        catch (RuntimeException ex)
        {
            System.out.println(ex.getMessage());
        }
        IStatement example11 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(4))),
                new WhileStatement(new RelationalExpression(">",new VariableExpression("v"),new ValueExpression(new IntValue(0))),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('-',
                        new VariableExpression("v"),new ValueExpression(new IntValue(1))))))));
        IDictionary<String, IType> typeEnv3 = new MyDictionary<>();
        try {
            example11.typecheck(typeEnv3);
            IStack<IStatement> executionStack11 = new MyStack<IStatement>();
            IDictionary<String, IValue> symbolTable11 = new MyDictionary<String, IValue>();
            IList<IValue> output11 = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable11 = new MyDictionary<StringValue, BufferedReader>();
            IHeap heap11 = new MyHeap();
            ProgramState prg11 = new ProgramState(executionStack11, symbolTable11, output11, fileTable11, example11, heap11);
            IRepository repo11 = new Repository("C:\\Users\\PC\\Desktop\\map\\log11.txt");
            repo11.AddProgram(prg11);
            Controller controller11 = new Controller(repo11);
            menu.AddCommand(new RunExample("11",example11.toString(),controller11));
        }
        catch (RuntimeException ex)
        {
            System.out.println(ex.getMessage());
        }
        menu.Show();
    }
}
