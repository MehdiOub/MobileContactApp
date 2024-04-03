package ma.emsi.contact_2;

public class Constants {
    public static final String DATABASE_NAME="contacts_db";
    public static final int VERSION=1;
    public static final String TABLE_NAME="contacts";
    public static final String ID="id";
    public static final String IMAGE="image";
    public static final String NAME="name";
    public static final String PHONE="phone";
    public static final String EMAIL="email";
    public static final String NOTE="note";
    public static final String CREATED_TIME="created_time";
    public static final String UPDATED_TIME="updated_time";

    public static final String CREATE_TABLE="create table "+TABLE_NAME+" " +
            "("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
             IMAGE+" TEXT,"+
             NAME+" TEXT,"+
             PHONE+" TEXT,"+
             EMAIL+" TEXT,"+
             NOTE+" TEXT,"+
             CREATED_TIME+" TEXT,"+
             UPDATED_TIME+" TEXT"+
        ")";
}
