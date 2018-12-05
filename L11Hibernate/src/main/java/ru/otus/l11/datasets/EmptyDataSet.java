package ru.otus.l11.datasets;

import javax.persistence.*;

/**
 * Created by tully.
 */
@Entity
public class EmptyDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
