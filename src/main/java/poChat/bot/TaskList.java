package poChat.bot;

import java.util.ArrayList;
import poChat.tasks.Task;

public class TaskList {
    private final ArrayList<Task> listTasks;

    public TaskList() {
        this.listTasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> listTasks) {
        this.listTasks = listTasks;
    }

    public void add(Task task) {
        this.listTasks.add(task);
    }

    public int size() {
        return this.listTasks.size();
    }

    public void remove(Task task) {
        this.listTasks.remove(task);
    }

    public Task get(int index) {
        return this.listTasks.get(index);
    }

    public ArrayList<Task> toList() {
        return this.listTasks;
    }
}
