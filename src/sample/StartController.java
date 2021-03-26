package sample;

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
import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import repository.IRepository;
import repository.Repository;
import sample.RunController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StartController {

    @FXML
    private ListView<String> Programs_List;
    private Map<String, IStatement> programs;
    private Stage parentStage;

    public void SetParentStage(Stage parentStage)
    {
        this.parentStage = parentStage;
    }


    public void runSelectedProgram(ActionEvent event) throws IOException {
        String selectedProgram = this.Programs_List.getSelectionModel().getSelectedItem();
        IStatement statement = this.programs.get(selectedProgram.substring(selectedProgram.indexOf('.')+1));
        System.out.println(statement);
        IDictionary<String, IType> typeEnv = new MyDictionary<>();
        try
        {
            statement.typecheck(typeEnv);
            IStack<IStatement> executionStack1 = new MyStack<IStatement>();
            IDictionary<String, IValue> symbolTable1 = new MyDictionary<String, IValue>();
            IList<IValue> output1 = new MyList<IValue>();
            IDictionary<StringValue, BufferedReader> fileTable1= new MyDictionary<StringValue,BufferedReader>();
            IHeap heap1 = new MyHeap();
            ProgramState prg1 = new ProgramState(executionStack1,symbolTable1,output1,fileTable1,statement,heap1);
            IRepository repo1 = new Repository("C:\\Users\\PC\\Desktop\\map\\log"+(this.Programs_List.getSelectionModel().getSelectedIndices().get(0)+1)+".txt");
            //repo1.AddProgram(prg1);
            Controller controller1 = new Controller(repo1);
            controller1.AddProgram(prg1);
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RunWindow.fxml"));
            root = loader.load();
            RunController run  = loader.getController();
            run.SetParentStage(parentStage);
            run.SetController(controller1);
            run.SetScene(parentStage.getScene());
            Scene scene = new Scene(root,900,500);
            controller1.AddObserver(run);
            controller1.Notify();
            this.parentStage.setScene(scene);
            this.parentStage.show();
        }
        catch (RuntimeException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("upsi");
            alert.setContentText(ex.getMessage());
            alert.setResizable(true);
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize()
    {
        this.CreatePrograms();
        ObservableList<String> programList = FXCollections.observableArrayList();
        AtomicInteger Index = new AtomicInteger(0);
        this.programs.forEach((key,value) -> programList.add((Index.incrementAndGet()) + "." + key ));
        this.Programs_List.setItems(programList);
        this.Programs_List.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void CreatePrograms()
    {
        this.programs = new HashMap<>();
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
                new WhileStatement(new RelationalExpression(">",new VariableExpression("v"),new ValueExpression(new IntValue(0))),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('-',
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
                                        new ForkStatement(new CompoundStatement(new AssignStatement("a",new ValueExpression(new IntValue(12))),new CompoundStatement(
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


        IStatement example14 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new ForStatement("v",new ValueExpression(new IntValue(0)),
                                new ValueExpression(new IntValue(3)),new ArithmeticExpression('+',new VariableExpression("v"),new ValueExpression(new IntValue(1))),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('+',new VariableExpression("v"),new ValueExpression(new IntValue(1))))))),
                                new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"),new ValueExpression(new IntValue(10)))))));

        IStatement example15 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(new WhileStatement(new RelationalExpression("<",new VariableExpression("v"),new ValueExpression(new IntValue(3))),
                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('+',new VariableExpression("v"),new ValueExpression(new IntValue(1)))))),
                                                        new AssignStatement("v",new ArithmeticExpression('+',new VariableExpression("v"),new ValueExpression(new IntValue(1))))))
                                                        ,new CompoundStatement(new SleepStatement(new IntValue(5)),new PrintStatement(new ArithmeticExpression('*',new VariableExpression("v"),new ValueExpression(new IntValue(10))))))));


        IStatement example16 = new CompoundStatement(new VariableDeclarationStatement("a",new IntType()),
                         new CompoundStatement(new AssignStatement("a",new ValueExpression(new IntValue(3))),
                                 new SwitchStatement(new VariableExpression("a"),
                                         new ValueExpression(new IntValue(2)),
                                         new ValueExpression(new BoolValue(true)),
                                         new NopStatement(),
                                         new NopStatement(),
                                         new PrintStatement(new ValueExpression(new BoolValue(false))))));

        IStatement example17 = new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(4))),
                new RepeatStatement(new RelationalExpression("==",new VariableExpression("v"),new ValueExpression(new IntValue(0))),new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",new ArithmeticExpression('-',
                        new VariableExpression("v"),new ValueExpression(new IntValue(1))))))));


        IStatement example18 = new CompoundStatement(new VariableDeclarationStatement("v",new BoolType()),
                new CompoundStatement(new ConditionalStatement("v",new ValueExpression(new BoolValue(true)),new ValueExpression(new BoolValue(false)),new ValueExpression(new BoolValue(true))),
                new PrintStatement(new VariableExpression("v"))));






        this.programs.put(example1.toString(),example1);
        this.programs.put(example2.toString(),example2);
        this.programs.put(example3.toString(),example3);
        this.programs.put(example4.toString(),example4);
        this.programs.put(example5.toString(),example5);
        this.programs.put(example6.toString(),example6);
        this.programs.put(example7.toString(),example7);
        this.programs.put(example8.toString(),example8);
        this.programs.put(example9.toString(),example9);
        this.programs.put(example10.toString(),example10);
        this.programs.put(example11.toString(),example11);
        this.programs.put(example12.toString(),example12);
        this.programs.put(example13.toString(),example13);
        this.programs.put(example14.toString(),example14);
        this.programs.put(example15.toString(),example15);
        this.programs.put(example16.toString(),example16);
        this.programs.put(example17.toString(),example17);
        this.programs.put(example18.toString(),example18);
    }
}
