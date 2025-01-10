package ensisa.album;

import ensisa.album.command.UndoableCommand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Stack;

public class UndoRedoHistory {
    private Stack<UndoableCommand> undoStack;
    private Stack<UndoableCommand> redoStack;
    private boolean inUndoRedo;
    private final BooleanProperty canUndo;
    private final BooleanProperty canRedo;

    public UndoRedoHistory() {
        undoStack = new Stack<UndoableCommand>();
        redoStack = new Stack<UndoableCommand>();
        canUndo = new SimpleBooleanProperty(false);
        canRedo = new SimpleBooleanProperty(false);
    }

    public void undo() {
        inUndoRedo = true;
        var top = undoStack.pop();
        top.undo();
        redoStack.push(top);
        inUndoRedo = false;
        canUndo.set(!undoStack.isEmpty());
        canRedo.set(true);
    }

    public void redo() {
        inUndoRedo = true;
        var top = redoStack.pop();
        top.redo();
        undoStack.push(top);
        inUndoRedo = false;
        canUndo.set(true);
        canRedo.set(!redoStack.isEmpty());
    }

    public void execute(UndoableCommand command) {
        if (inUndoRedo)
            throw new RuntimeException(
                    "Invoking execute within an undo/redo action.");
        // On ne peut réaliser une nouvelle opération pendant l'annulation ou le rétablissement d'une autre
        redoStack.clear();
        // La pile de rétablissement est vidée lorsqu’une nouvelle opération (qui est différente de la prochaine
        // opération de la pile) est réalisée
        undoStack.push(command);
        canUndo.set(true);
        canRedo.set(false);
        command.execute();
    }

    public void addCommand(UndoableCommand command) {
        // Ajoute une commande à la pile d'annulation sans l'éxécuter
        undoStack.push(command);
        canUndo.set(true);
        canRedo.set(false);
    }

    public void removeCommand(UndoableCommand command) {
        // Supprime une commande de la pile d'annulation sans l'éxécuter
        undoStack.remove(command);
        canUndo.set(!undoStack.isEmpty()); // Si la pile est vide, on ne peut pas annuler d'opération (undo)
    }

    public BooleanProperty canUndoProperty() {
        return canUndo;
    }

    public BooleanProperty canRedoProperty() {
        return canRedo;
    }

    public Stack<UndoableCommand> getUndoStack() {
        return undoStack;
    }

    public Stack<UndoableCommand> getRedoStack() {
        return redoStack;
    }

    public void printStacks() {
        System.out.println("Undo Stack:");
        for (UndoableCommand command : undoStack) {
            System.out.println(command);
        }

        System.out.println("Redo Stack:");
        for (UndoableCommand command : redoStack) {
            System.out.println(command);
        }
    }
}

