package com.ab.aplikacje_biznesowe;

import java.sql.SQLException;
import java.util.ArrayList;

public interface JTable {
    //void toTableRow();
    //String toString();
    void toStringRow();
    void goToRowId(int id) throws SQLException;
}
