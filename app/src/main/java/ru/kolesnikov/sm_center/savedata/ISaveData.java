package ru.kolesnikov.sm_center.savedata;

import java.io.Serializable;

public interface ISaveData extends Serializable {

    void save(String name, String value);

    String read(String name);
}
