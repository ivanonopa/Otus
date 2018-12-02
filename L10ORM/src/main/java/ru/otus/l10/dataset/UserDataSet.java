package ru.otus.l10.dataset;

import javax.persistence.Table;

@Table(name = "user")
public class UserDataSet extends DataSet {
    private String name;
    private Integer age;
    private Integer height;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    public UserDataSet(){
    }

    public UserDataSet(String name, Integer age, Integer height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id= " + super.id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", height=" + height +
                '}';
    }
}
