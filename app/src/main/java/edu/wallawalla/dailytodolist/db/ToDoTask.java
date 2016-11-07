package edu.wallawalla.dailytodolist.db;

public class ToDoTask {
    private int _id;
    private String _taskname;

    public ToDoTask() {}
    public ToDoTask(int id, String taskname) {
        this._id = id;
        this._taskname = taskname;
    }
    public ToDoTask(String taskname) {
        this._taskname = taskname;
    }
    public void setID(int id) {
        this._id = id;
    }
    public int getID() {
        return this._id;
    }
    public void setTaskName(String taskname) {
        this._taskname = taskname;
    }
    public String getTaskName() {
        return this._taskname;
    }


}
