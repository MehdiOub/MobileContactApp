package ma.emsi.contact_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper
{
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Constants.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
     {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }
    public long insertContact(String image,String name,String phone,String email,String note,String created_time,String updated_time)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Constants.IMAGE,image);
        cv.put(Constants.NAME,name);
        cv.put(Constants.PHONE,phone);
        cv.put(Constants.EMAIL,email);
        cv.put(Constants.NOTE,note);
        cv.put(Constants.CREATED_TIME,created_time);
        cv.put(Constants.UPDATED_TIME,updated_time);
        long id=db.insert(Constants.TABLE_NAME,null,cv);
        db.close();
        return id;
    }
    public ContactModel getContactById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContactModel contact = null;
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] { Constants.ID, Constants.NAME, Constants.IMAGE, Constants.PHONE, Constants.EMAIL, Constants.NOTE, Constants.CREATED_TIME, Constants.UPDATED_TIME }, Constants.ID + "=?", new String[]{id}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Constants.ID);
            int nameIndex = cursor.getColumnIndex(Constants.NAME);
            int imageIndex = cursor.getColumnIndex(Constants.IMAGE);
            int phoneIndex = cursor.getColumnIndex(Constants.PHONE);
            int emailIndex = cursor.getColumnIndex(Constants.EMAIL);
            int noteIndex = cursor.getColumnIndex(Constants.NOTE);
            int createdTimeIndex = cursor.getColumnIndex(Constants.CREATED_TIME);
            int updatedTimeIndex = cursor.getColumnIndex(Constants.UPDATED_TIME);

            if (idIndex != -1 && nameIndex != -1 && imageIndex != -1 && phoneIndex != -1 && emailIndex != -1 && noteIndex != -1 && createdTimeIndex != -1 && updatedTimeIndex != -1) {
                contact = new ContactModel(
                        cursor.getString(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(imageIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(emailIndex),
                        cursor.getString(noteIndex),
                        cursor.getString(createdTimeIndex),
                        cursor.getString(updatedTimeIndex)
                );
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return contact;
    }
    public boolean deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Constants.TABLE_NAME, Constants.ID + "=?", new String[]{id});
        db.close();
        return result > 0; // Return true if the delete operation was successful
    }
    public boolean updateContact(String id, String image, String name, String phone, String email, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.IMAGE, image);
        cv.put(Constants.NAME, name);
        cv.put(Constants.PHONE, phone);
        cv.put(Constants.EMAIL, email);
        cv.put(Constants.NOTE, note);

        int update = db.update(Constants.TABLE_NAME, cv, Constants.ID + " = ?", new String[]{id});
        db.close();
        return update > 0;
    }
    public ArrayList<ContactModel> getAll()
    {
        ArrayList<ContactModel> contact_list=new ArrayList<>();
        String query="SELECT * FROM "+Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if (cursor.moveToFirst())
        {
            do {
                ContactModel cm=new ContactModel(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.CREATED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.UPDATED_TIME))
                );
                contact_list.add(cm);
            }while (cursor.moveToNext());
        }
        return contact_list;
    }
}
