package ma.emsi.contact_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recycler_view;
    DbHelper dbHelper;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view=findViewById(R.id.recycler_view);
        //recycler_view.setHasFixedSize(true);
        dbHelper=new DbHelper(this);

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData()
    {
        ArrayList<ContactModel> cms=dbHelper.getAll();
        Log.d(" start","----------------------------------------");
        for (int i = 0; i < cms.size(); i++) {
            ContactModel cm =cms.get(i);
            Log.d(" i = "+i,cm.getName());
        }
        Log.d(" end","----------------------------------------");
        contactAdapter=new ContactAdapter(this,dbHelper.getAll());
        recycler_view.setAdapter(contactAdapter);
    }



    public void create(View vien)
    {
        Intent i=new Intent(this,AddEditContact.class);
        startActivity(i);
    }
}