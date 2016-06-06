package com.a1995.mahesh.unsafenotes.database;

/**
 * Created by mahesh on 2/6/16.
 */
public class Schema {

    public static final class NotesTable{
        public static final String NAME = "notes";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String CATEGORY = "category";
            public static final String DATE = "date";
            public static final String CONTENT = "content";
        }
    }
}
