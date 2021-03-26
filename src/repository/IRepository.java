package repository;

import domain.ProgramState;
import domain.adts.MyList;
import exceptions.LogException;

import java.util.List;

public interface IRepository{
    void  AddProgram(ProgramState newProgram);
    void logProgramStateExecution(ProgramState state) throws LogException;
    MyList<ProgramState> getProgramList();
    void SetProgramList(List<ProgramState> programStates);
}
