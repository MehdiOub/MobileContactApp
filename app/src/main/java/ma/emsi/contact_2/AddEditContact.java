package ma.emsi.contact_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class AddEditContact extends AppCompatActivity {
    private ImageView imageEt;
    private EditText nameEt, phoneEt, emailEt, noteEt;
    private String image, name, phone, email, note;
    private DbHelper dbHelper;
    private Uri uri;
    private String contactId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
        imageEt = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        noteEt = findViewById(R.id.noteEt);
        dbHelper = new DbHelper(this);
        contactId = getIntent().getStringExtra("CONTACT_ID");
        if (contactId != null) {
            isEditMode = true;
            loadContactData(contactId);
        }
    }

    public void chooseImage(View view) {
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imageEt.setImageURI(uri);
        }
    }

    public void save_data(View view) {
        image = uri.toString();
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();
        note = noteEt.getText().toString();
        if (isEditMode) {
            boolean success = dbHelper.updateContact(contactId, image, name, phone, email, note);
            if (success) {
                Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to update contact", Toast.LENGTH_SHORT).show();
            }
        } else {
            long id = dbHelper.insertContact(image, name, phone, email, note, System.currentTimeMillis() + "", System.currentTimeMillis() + "");
            if (id > -1) {
                Toast.makeText(this, "Contact added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadContactData(String id) {
        ContactModel contact = dbHelper.getContactById(id);
        if (contact != null) {
            nameEt.setText(contact.getName());
            phoneEt.setText(contact.getPhone());
            emailEt.setText(contact.getEmail());
            noteEt.setText(contact.getNote());
            if (!contact.getImage().isEmpty()) {
                uri = Uri.parse(contact.getImage());
                imageEt.setImageURI(uri);
            }
        }
    }
}
