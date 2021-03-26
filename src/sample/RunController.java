package sample;

import Observer.MyObserver;
import com.sun.source.tree.ReturnTree;
import controller.Controller;
import domain.ProgramState;
import domain.adts.IStack;
import domain.statement.IStatement;
import domain.value.IValue;
import domain.value.StringValue;
import exceptions.MyExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RunController extends MyObserver {
    private Stage stage;
    private Controller controller;
    private Scene scene;

    @FXML
    private TableView<Pair<Integer, IValue>> Heap_Table;
    @FXML
    private TableColumn<Pair<Integer,IValue>,Integer> Adress_Column;
    @FXML
    private TableColumn<Pair<Integer,IValue>,IValue> Value_Column;
    @FXML
    private TableView<Pair<String, IValue>> Symbol_Table;
    @FXML
    private TableColumn<Pair<String,IValue>,Integer> Name_Column;
    @FXML
    private TableColumn<Pair<String,IValue>,IValue> Sym_Value_Column;
    @FXML
    private ListView<IStatement> Execution_Stack;
    @FXML
    private ListView<String> Output_List;
    @FXML
    private ListView<Integer> Program_States;
    @FXML
    private TextField Program_States_Number;

    @FXML
    private ListView<String> File_Table;


    public void SetParentStage(Stage stage)
    {
        this.stage = stage;
    }

    public void SetController(Controller controller)
    {
        this.controller = controller;
        this.controller.SetExecutor(Executors.newFixedThreadPool(2));
    }

    public void SetScene(Scene scene)
    {
        this.scene = scene;
    }

    public void CloseProgram()
    {
        this.controller.GetExecutor().shutdownNow();
        this.stage.setScene(scene);
        this.controller.SetProgramList(new ArrayList<>());
    }


    private void populate_Programlist()
    {
        ObservableList<Integer> programList = FXCollections.observableArrayList();
        ArrayList<ProgramState> programs = this.controller.GetPrograms().RetrieveAll();
        AtomicInteger index = new AtomicInteger(0);
        programs.forEach(ProgramState -> programList.add(index.getAndIncrement(),ProgramState.GetId()));
        this.Program_States.setItems(programList);
        this.Program_States.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void populate_Execution_Stack()
    {
        ObservableList<IStatement> Stack = FXCollections.observableArrayList();
        ProgramState program = GetSelectedProgram();
        IStack<IStatement> myStack = program.getExeStack();
        Stack.addAll(myStack.stream().collect(Collectors.toList()));
        this.Execution_Stack.setItems(Stack);
    }

    private ProgramState GetSelectedProgram() throws MyExecutionException
    {
        Integer id = this.Program_States.getSelectionModel().getSelectedItem();
        System.out.println(id);
        ProgramState program;
        if(Objects.isNull(id) && this.controller.GetPrograms().RetrieveAll().size() != 0)
        {
            this.Program_States.getSelectionModel().selectIndices(0);
            id = this.Program_States.getItems().get(0);
        }
        program = this.controller.getProgramById(id);
        if(Objects.isNull(program))
            throw new MyExecutionException("it doesn't exist");
        //if(!program.isNotCompleted())
            //throw new MyExecutionException("completed program");
        System.out.println(program);
        return program;
    }



    @FXML
    public void initialize()
    {
        this.Program_States.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //this.FileTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.Adress_Column.setCellValueFactory(new PropertyValueFactory<>("key"));
        this.Name_Column.setCellValueFactory(new PropertyValueFactory<>("key"));
        this.Value_Column.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.Sym_Value_Column.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void OneStep(ActionEvent Event)
    {
        ArrayList<ProgramState> programStates = (ArrayList<ProgramState>) this.controller.RemoveCompletedProgram(this.controller.GetPrograms().RetrieveAll());
        if(programStates.size()==0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("upsi");
            alert.setContentText("program is done");
            alert.setResizable(true);
            alert.showAndWait();
            return;
        }
        this.controller.GarbageCollectorSafe(programStates);
          try
          {
              //ProgramState program = this.GetSelectedProgram();
              //program.oneStep();
              this.controller.OneStepForAllPrograms(programStates);
          }
          catch (RuntimeException | InterruptedException ex){
              this.CloseProgram();
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setResizable(true);
              alert.setContentText(ex.getMessage());
              alert.showAndWait();
          }
          this.controller.RemoveCompletedProgram(programStates);
          //this.update();
          if(programStates.size()==0)
              this.CloseProgram();
    }

    @Override
    public void update()
    {
        this.populate_Programlist();
     this.SetTextField();
     this.Populate_FileTable();
     this.Populate_Heap();

     this.Populate_SymbolTable();
     this.populate_Execution_Stack();
        this.Populate_Output();
    }

    public void SetTextField()
    {
        this.Program_States_Number.setText(this.controller.GetPrograms().Size().toString());
    }

    public void Populate_SymbolTable()
    {
        ObservableList<Pair<String,IValue>> symbol_table = FXCollections.observableArrayList();
        ProgramState program = GetSelectedProgram();
        HashMap<String,IValue> symbol = (HashMap<String, IValue>) program.getSymTable().GetContent();
        AtomicInteger index = new AtomicInteger(0);
        symbol.forEach((name,content) -> symbol_table.add(index.getAndIncrement(),new Pair<>(name,content)));
        this.Symbol_Table.setItems(symbol_table);
    }

    public void Populate_FileTable()
    {
        ObservableList<String> FileTable = FXCollections.observableArrayList();
        ProgramState programState = GetSelectedProgram();
        HashMap<StringValue, BufferedReader> file_table = (HashMap<StringValue, BufferedReader>) programState.getFileTable().GetContent();
        AtomicInteger index = new AtomicInteger(0);

        System.out.println(file_table);
        file_table.forEach((name,bf) -> FileTable.add(index.getAndIncrement(), name.getValue()));
        System.out.println(FileTable);

        this.File_Table.setItems(FileTable);
    }
    public void Populate_Output()
    {
        ObservableList<String> List = FXCollections.observableArrayList();
        ProgramState program = GetSelectedProgram();
        ArrayList<IValue> output = program.getOut().RetrieveAll();
        AtomicInteger index = new AtomicInteger(0);
        output.forEach(out -> List.add(index.getAndIncrement(),out.toString()));
        this.Output_List.setItems(List);
    }

    public void Populate_Heap()
    {
        ObservableList<Pair<Integer,IValue>> heap_table = FXCollections.observableArrayList();
        ProgramState program = GetSelectedProgram();
        HashMap<Integer,IValue> heap = (HashMap<Integer, IValue>) program.getHeap().GetContent();
        AtomicInteger index = new AtomicInteger(0);
        heap.forEach((adress,content) -> heap_table.add(index.getAndIncrement(),new Pair<>(adress,content)));
        this.Heap_Table.setItems(heap_table);
    }

    public void HandleProgram()
    {
        update();
    }
}
