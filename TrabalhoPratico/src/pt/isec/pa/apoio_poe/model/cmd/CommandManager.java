package pt.isec.pa.apoio_poe.model.cmd;

import java.util.Stack;

public class CommandManager {
    private final Stack<ICommand> history;
    private final Stack<ICommand> redoCmd;

    public CommandManager(){
        history = new Stack<>();
        redoCmd = new Stack<>();
    }

    public boolean invokeCommand(ICommand cmd){
        redoCmd.clear();
        if(cmd.execute()){
            history.push(cmd);
            return true;
        }
        return false;
    }

    public boolean undo(){
        if(history.isEmpty())
            return false;

        ICommand cmd = history.pop();
        if(cmd.undo()){
            redoCmd.push(cmd);
            return true;
        }
        return false;
    }

    public boolean redo(){
        if(redoCmd.isEmpty())
            return false;

        ICommand cmd = redoCmd.pop();
        if(cmd.execute()) {
            history.push(cmd);
            return true;
        }
        return false;
    }

    public boolean hasUndo(){
        return history.size()>0;
    }

    public boolean hasRedo(){
        return redoCmd.size()>0;
    }
}
