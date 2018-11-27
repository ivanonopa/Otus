package ru.otus.l10;

import ru.otus.l10.dataset.UserDataSet;
import ru.otus.l10.db.DBService;
import ru.otus.l10.db.DBServiceImpl;

public class MyORM {
    public static void main(String[] args) throws Exception {
        try (DBService db = new DBServiceImpl()) {
            db.prepareTables();
            db.save(new UserDataSet(1,"Ivan",35, 185));
            db.save(new UserDataSet(2,"Vasya",33,183));
            db.listAllData();
            System.out.println(db.load(2,UserDataSet.class));

        }
    }
}
