package it.raffinato.dev.lensminder.utils.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.joda.time.DateTime;

import java.util.Calendar;

import it.raffinato.dev.lensminder.LensMinderApplication;
import it.raffinato.dev.lensminder.utils.LensesWrapper;

import static android.content.Context.ALARM_SERVICE;

public class NotificationScheduler {
    public static void setReminder(Context context, Class<?> cls, int doy, int min, int hour, int notificationId) {
        Calendar calendar = Calendar.getInstance();

        Calendar setcalendar = Calendar.getInstance();

        setcalendar.set(Calendar.DAY_OF_YEAR, doy);
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);
        // cancel already scheduled reminders
        cancelReminder(context, cls, notificationId);

        if (setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE, 1);

        // Enable a receiver
        ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Intent intent1 = new Intent(context, cls);
        intent1.putExtra("notification-id", notificationId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        assert am != null;
        am.setRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelReminder(Context context, Class<?> cls, int notificationId) {
        Intent intent1 = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent1, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        assert am != null;
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public static void cancelReminders(Context context, Class<?> cls) {
        NotificationScheduler.cancelReminder(context, cls, 0);
        NotificationScheduler.cancelReminder(context, cls, NotificationHelper.LX_LENS_NOT_ID);
        NotificationScheduler.cancelReminder(context, cls, NotificationHelper.RX_LENS_NOT_ID);
    }

    public static void initNotifications(LensesWrapper lenses) {
        Context ctx = LensMinderApplication.instance();
        NotificationScheduler.cancelReminders(ctx, AlarmReceiver.class);

        if (lenses.getLxLensExpDate().equals(lenses.getRxLensExpDate())) {
            NotificationScheduler.setReminder(ctx, AlarmReceiver.class, lenses.getLxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), 0);
        } else {
            NotificationScheduler.setReminder(ctx, AlarmReceiver.class, lenses.getLxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), NotificationHelper.LX_LENS_NOT_ID);
            NotificationScheduler.setReminder(ctx, AlarmReceiver.class, lenses.getRxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), NotificationHelper.RX_LENS_NOT_ID);
        }
    }

}
