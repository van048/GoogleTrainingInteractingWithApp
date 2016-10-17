package cn.ben.googletraininginteractingwithapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

            showAppChooser();
        }
    };

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
}
