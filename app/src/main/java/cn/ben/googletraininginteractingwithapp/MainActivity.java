package cn.ben.googletraininginteractingwithapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import cn.ben.googletraininginteractingwithapp.databinding.ActivityMainBinding;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    public View.OnClickListener buildImplicitIntentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            initiatePhoneCall();
//
//            viewMap();
//
//            viewWebPage();
//
//            sendEmailWithAttachment();
//
//            createCalendarEvent();
//
//            showAppChooser();
//
//            pseudoStartFromOtherActivitySendTo();
//
            pseudoStartFromOtherActivitySend();
        }
    };

    private void pseudoStartFromOtherActivitySendTo() {
//        Uri sms_uri = Uri.parse("sms:13517596490");
        Uri sms_uri = Uri.parse("smsto:13517596490");
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, sms_uri);

        Intent chooser = Intent.createChooser(smsIntent, "ACTION_SENDTO");
        // Verify the intent will resolve to at least one activity??
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    static final int SEND_REQUEST = 2;  // The request code

    private void pseudoStartFromOtherActivitySend() {
        Intent smsIntent = new Intent(Intent.ACTION_SEND);
//        smsIntent.setType("image/png");
        smsIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(smsIntent, "ACTION_SEND");
        // Verify the intent will resolve to at least one activity??
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(chooser, SEND_REQUEST);
        }
    }

    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public View.OnClickListener startActivityForResultOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickContact();
        }
    };

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        //The MIME type of CONTENT_URI providing a directory of phones.??
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    private void createCalendarEvent() {
        // TODO: 2016/10/17 ACTION_INSERT?? CONTENT_URI???
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);
        // TODO: 2016/10/17 CalendarContract.EXTRA_EVENT_BEGIN_TIME
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        if (hasAppReceivingIntent(calendarIntent)) startActivity(calendarIntent);
    }

    private void sendEmailWithAttachment() {
        // TODO: 2016/10/17 ACTION_SEND
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        // You can also attach multiple items by passing an ArrayList of Uris
        if (hasAppReceivingIntent(emailIntent)) startActivity(emailIntent);
    }

    private void viewWebPage() {
        Uri webPage = Uri.parse("http://www.android.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
        if (hasAppReceivingIntent(webIntent)) startActivity(webIntent);
    }

    private void viewMap() {
        // Map point based on address??
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        if (hasAppReceivingIntent(mapIntent)) startActivity(mapIntent);
    }

    private void initiatePhoneCall() {
        // create an intent to initiate a phone call using the Uri data to specify the telephone number:
        Uri number = Uri.parse("tel:5551234");
        // Input: If nothing, an empty dialer is started; else getData() is URI of a phone number to be dialed or a tel: URI of an explicit phone number.
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (hasAppReceivingIntent(callIntent)) startActivity(callIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setActivity(this);
    }

    private boolean hasAppReceivingIntent(Intent intent) {
        PackageManager packageManager = getPackageManager();
        // TODO: 2016/10/17 MATCH_DEFAULT_ONLY??
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }

    private void showAppChooser() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = getResources().getString(R.string.chooser_title);
        // Create intent to show chooser
        Intent chooser = Intent.createChooser(emailIntent, title);

        // Verify the intent will resolve to at least one activity??
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    // Retrieve the phone number from the NUMBER column
                    int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(column);

                    // Do something with the phone number...
                    Toast.makeText(this, String.valueOf(number), Toast.LENGTH_SHORT).show();
                    cursor.close();
                }
            }
        } else if (requestCode == SEND_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getAction() + " " + data.getData(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == ShareActivity.RESULT_COLOR_RED) {
                Toast.makeText(this, "result is an integer: " + resultCode, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
