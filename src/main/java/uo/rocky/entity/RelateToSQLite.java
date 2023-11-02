package uo.rocky.entity;

import java.sql.Connection;

public interface RelateToSQLite {
    static String escapeSingleQuotes(String string) {
        return null == string ? "NULL" : string.replace("'", "''");
    }

    static String escapeDoubleQuotes(String string) {
        return null == string ? "NULL" : string.replace("\"", "\"\"");
    }

    static String escapeQuotes(String string) {
        return escapeDoubleQuotes(escapeSingleQuotes(string));
    }

    void insertSQLite(Connection connectionSQLite) throws Exception;  // TODO: redefine exception

    void deleteSQLite(Connection connectionSQLite) throws Exception;  // TODO: redefine exception

    void updateSQLite(Connection connectionSQLite) throws Exception;  // TODO: redefine exception

    void selectSQLite(Connection connectionSQLite) throws Exception;  // TODO: redefine exception
}