package pochat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    private final String taskDescription;
    private boolean isDone;

    protected Task(String taskDescription, boolean isDone) {
        this.taskDescription = taskDescription;
        this.isDone = isDone;
    }

    private static String formatLoadedDateTime(String dateTime) {
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter processedFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        return LocalDateTime.parse(dateTime, originalFormatter).format(processedFormatter);
    }

    public static Task of(String taskObjectString) {
        if (taskObjectString.startsWith("[T]")) {
            boolean isDone = taskObjectString.charAt(4) == 'X';
            String taskDescription = taskObjectString.substring(7);

            return new ToDo(taskDescription, isDone);

        } else if (taskObjectString.startsWith("[D]")) {
            boolean isDone = taskObjectString.charAt(4) == 'X';
            int byIndex = taskObjectString.indexOf("by");

            String taskDescription = taskObjectString.substring(7, byIndex - 2);
            String deadline = taskObjectString.substring(byIndex + 4, taskObjectString.length() - 1);

            return new Deadline(taskDescription, formatLoadedDateTime(deadline), isDone);

        } else if (taskObjectString.startsWith("[E]")) {
            boolean isDone = taskObjectString.charAt(4) == 'X';
            int fromIndex = taskObjectString.indexOf("from");
            int toIndex = taskObjectString.indexOf("to");

            String taskDescription = taskObjectString.substring(7, fromIndex - 2);
            String from = taskObjectString.substring(fromIndex + 6, toIndex - 1);
            String to = taskObjectString.substring(toIndex + 4, taskObjectString.length() - 1);

            return new Event(taskDescription, formatLoadedDateTime(from),
                    formatLoadedDateTime(to), isDone);
        } else {
            throw new IllegalArgumentException("Task object string is not a task object.");
        }
    }

    /**
     * Checks whether the Task description contains the keyword
     * @param keyword: the task should contain this keyword
     * @return <code>true</code> if the Task description contains this keyword,
     * otherwise <code>false</code>.
     */
    public boolean contains(String keyword) {
        return taskDescription.contains(keyword);
    }

    private boolean isDone() {
        return this.isDone;
    }

    private void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void markAsDone() {
        this.setDone(true);
    }

    public void unmarkAsDone() {
        this.setDone(false);
    }

    protected LocalDateTime toLocalDateTime(String deadline) {
        return LocalDateTime.parse(deadline, DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }

    protected String formatYearMonthDay(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return localDateTime.format(dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "[" + (this.isDone() ? "X" : " ") + "] " + taskDescription;
    }
}
