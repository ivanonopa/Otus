package ru.otus.l10.dataset;

import ru.otus.l10.dataset.DataSet;

public class UserDataSet extends DataSet {
    private Integer id;
    private String name;
    private Integer age;
    private Integer height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserDataSet(Integer id, String name, Integer age, Integer height){
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", height=" + height +
                '}';
    }
}
