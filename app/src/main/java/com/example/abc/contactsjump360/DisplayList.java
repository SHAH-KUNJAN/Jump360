package com.example.abc.contactsjump360;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class DisplayList extends ArrayAdapter<Contactlist> {
    private final List<String> conkeys;
    private final List<Contactlist> displist;
    private Activity context;


    public DisplayList(Activity context, List<Contactlist> condisp, List<String> conkeys) {
        super(context, R.layout.displaylist, condisp);
        this.context=context;
        this.displist=condisp;
        this.conkeys=conkeys;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem=inflater.inflate(R.layout.displaylist,null,true);

        TextView txtname=(TextView) listViewItem.findViewById(R.id.txtname);

        TextView txtno1=(TextView) listViewItem.findViewById(R.id.txtno1);

       TextView txtno2=(TextView) listViewItem.findViewById(R.id.txtno2);

       final LinearLayout l1=(LinearLayout)listViewItem.findViewById(R.id.lay1) ;

       LinearLayout lmain=(LinearLayout)listViewItem.findViewById(R.id.lview);

        Button btnd=(Button)listViewItem.findViewById(R.id.btndetail);
        Button btnc=(Button)listViewItem.findViewById(R.id.btncall);
        Button btnm=(Button)listViewItem.findViewById(R.id.btnmessage);

       lmain.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               if(l1.getVisibility()==View.GONE)
               {
                   l1.setVisibility(View.VISIBLE);
               }
               else
               {
                   l1.setVisibility(View.GONE);
               }
           }
       });

        final Contactlist cont=displist.get(position);

        btnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(getContext(),Detailview.class).putExtra("contact",cont).putExtra("key",conkeys.get(position)));
            }
        });

        btnc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",cont.getUserno1() , null));
                view.getContext().startActivity(intent);
            }
        });

        btnm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendSMS(view,cont.getUserno1());
            }
        });

        txtname.setText(cont.getUsername());
        txtno1.setText(cont.getUserno1());
        txtno2.setText(cont.getUserno2());

        return listViewItem;
    }

    private void sendSMS(View context, String contactNumber) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            String defaultname = Telephony.Sms.getDefaultSmsPackage(context.getContext());

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");

            if (defaultname != null)
            {
                sendIntent.setPackage(defaultname);
            }
            sendIntent.putExtra("address",contactNumber);
            context.getContext().startActivity(sendIntent);

        }
        else         {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address",contactNumber);
            context.getContext().startActivity(smsIntent);
        }
    }
}
