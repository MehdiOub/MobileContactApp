package ma.emsi.contact_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Context context;
    private ArrayList<ContactModel> contacts_list;

    public ContactAdapter(Context context, ArrayList<ContactModel> contacts_list) {
        this.context = context;
        this.contacts_list = contacts_list;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView photo, contact_number;
        TextView contact_name;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.contact_photo);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_name = itemView.findViewById(R.id.contact_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ContactModel contact = contacts_list.get(position);
                        Intent intent = new Intent(context, ContactDetailActivity.class);
                        intent.putExtra("CONTACT_ID", contact.getId());
                        context.startActivity(intent);

                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactModel contact = contacts_list.get(position);
        holder.contact_name.setText(contact.getName());
        if (contact.getImage().isEmpty()) {
            holder.photo.setImageResource(R.drawable.ic_baseline_person_24);
        } else {
            holder.photo.setImageURI(Uri.parse(contact.getImage()));
        }

        holder.contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Phone number clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts_list.size();
    }
}
