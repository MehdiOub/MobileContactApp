package ma.emsi.contact_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView nameTextView, phoneTextView, emailTextView, noteTextView;
    private ImageView imageView;
    private DbHelper dbHelper;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        nameTextView = findViewById(R.id.detail_name);
        phoneTextView = findViewById(R.id.detail_phone);
        emailTextView = findViewById(R.id.detail_email);
        noteTextView = findViewById(R.id.detail_note);
        imageView = findViewById(R.id.detail_image);
        dbHelper = new DbHelper(this);
        contactId = getIntent().getStringExtra("CONTACT_ID");
        loadContactDetails();
        Button editButton = findViewById(R.id.button_edit);
        Button deleteButton = findViewById(R.id.button_delete);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactDetailActivity.this, AddEditContact.class);
                intent.putExtra("CONTACT_ID", contactId);
                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ContactDetailActivity.this)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete this contact?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deleteContact(contactId);
                                Toast.makeText(ContactDetailActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void loadContactDetails() {
        ContactModel contact = dbHelper.getContactById(contactId);
        if (contact != null) {
            nameTextView.setText(contact.getName());
            phoneTextView.setText(contact.getPhone());
            emailTextView.setText(contact.getEmail());
            noteTextView.setText(contact.getNote());
            if (!contact.getImage().isEmpty()) {
                imageView.setImageURI(Uri.parse(contact.getImage()));
            }
        }
    }
}
