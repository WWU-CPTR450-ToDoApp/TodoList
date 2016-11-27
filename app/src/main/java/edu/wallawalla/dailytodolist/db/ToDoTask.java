package edu.wallawalla.dailytodolist.db;

public class ToDoTask {
    private int _id;
    private String _data_col1;
    private long _data_col2, _data_col3;
    private int _data_col4, _data_col5;
    private String _data_col6;

    public ToDoTask() {}
    public ToDoTask(String data_col1, long data_col2, long data_col3, int data_col4, int data_col5, String data_col6) {
        this._data_col1 = data_col1;
        this._data_col2 = data_col2;
        this._data_col3 = data_col3;
        this._data_col4 = data_col4;
        this._data_col5 = data_col5;
        this._data_col6 = data_col6;
    }
    public ToDoTask(String data_col1) {
        this._data_col1 = data_col1;
    }
    public void setID(int id) {
        this._id = id;
    }
    public int getID() {
        return this._id;
    }

    public void setCol1Data(String data_col1) {
        this._data_col1 = data_col1;
    }
    public String getCol1Data() {
        return this._data_col1;
    }

    public void setCol2Data(long data_col2) {
        this._data_col2 = data_col2;
    }
    public long getCol2Data() {
        return this._data_col2;
    }

    public void setCol3Data(long data_col3) {
        this._data_col3 = data_col3;
    }
    public long getCol3Data() {
        return this._data_col3;
    }

    public void setCol4Data(int data_col4) {
        this._data_col4 = data_col4;
    }
    public int getCol4Data() {
        return this._data_col4;
    }

    public void setCol5Data(int data_col5) {
        this._data_col5 = data_col5;
    }
    public int getCol5Data() {
        return this._data_col5;
    }

    public void setCol6Data(String data_col6) {
        this._data_col6 = data_col6;
    }
    public String getCol6Data() {
        return this._data_col6;
    }
}
