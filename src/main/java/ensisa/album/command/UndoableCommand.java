package ensisa.album.command;

public interface UndoableCommand extends Command {
    public void undo();
    public default void redo() {
        execute();
    }
}
