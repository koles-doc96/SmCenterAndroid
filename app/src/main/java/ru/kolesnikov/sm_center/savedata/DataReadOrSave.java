package ru.kolesnikov.sm_center.savedata;

public class DataReadOrSave  {
    private ISaveData saveData;

    public DataReadOrSave(ISaveData saveData) {
        this.saveData = saveData;
    }

    public void save(String name, String value){
        saveData.save(name, value);
    }

    public String read(String name){
        return saveData.read(name);
    }

    public void setSaveData(ISaveData saveData) {
        this.saveData = saveData;
    }

    public ISaveData getSaveData() {
        return saveData;
    }
}
