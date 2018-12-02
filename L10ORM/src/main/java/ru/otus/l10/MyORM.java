package ru.otus.l10;

import ru.otus.l10.dataset.UserDataSet;
import ru.otus.l10.db.DBService;
import ru.otus.l10.db.DBServiceImpl;

public class MyORM {
    public static void main(String[] args) throws Exception {
        try (DBService db = new DBServiceImpl(UserDataSet.class)) {
            db.prepareTables();
            db.save(new UserDataSet("Ivan",35, 185));
            db.save(new UserDataSet("Vasya",33,183));
            System.out.println(db.getAllData());
            System.out.println(db.load(2));

        }
    }
}
