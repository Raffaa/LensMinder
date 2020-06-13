package it.raffinato.dev.lensminder.utils.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import it.raffinato.dev.lensminder.repository.LensesRepository;
import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.utils.Lens;
import it.raffinato.dev.lensminder.utils.LensesWrapper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationHelper nh = new NotificationHelper(context);
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<LensesModel> lensList = LensesRepository.instance().getLastLenses2();
                        if (!lensList.isEmpty()) {
                            LensesModel lensesModel = lensList.get(0);
                            Lens lxLens = new Lens(lensesModel.getInitDateLx(), lensesModel.getDurationLx(), lensesModel.getActiveLx());
                            Lens rxLens = new Lens(lensesModel.getInitDateRx(), lensesModel.getDurationRx(), lensesModel.getActiveRx());
                            LensesWrapper lenses = new LensesWrapper(lxLens, rxLens);
                            if (lenses.hasLensesActive()) {
                                if (lenses.areEqual()) {
                                    if (lenses.getLxLensRemainingTime() > 0) {
                                        NotificationScheduler.setReminder(context, AlarmReceiver.class, lenses.getLxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), 0);
                                    }
                                } else {
                                    if (lenses.isLxLensActive() && lenses.getLxLensRemainingTime() > 0)
                                        NotificationScheduler.setReminder(context, AlarmReceiver.class, lenses.getLxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), NotificationHelper.LX_LENS_NOT_ID);
                                    if (lenses.isRxLensActive() && lenses.getRxLensRemainingTime() > 0)
                                        NotificationScheduler.setReminder(context, AlarmReceiver.class, lenses.getRxLensExpDate().getDayOfYear(), new DateTime().withMinuteOfHour(0).getMinuteOfHour(), new DateTime().withHourOfDay(20).getHourOfDay(), NotificationHelper.RX_LENS_NOT_ID);
                                }
                            }
                        }
                    }
                });
            }
            if (intent.getAction().equalsIgnoreCase(NotificationHelper.ACTION_SILENCE)) {
                int notId = Objects.requireNonNull(intent.getExtras()).getInt(NotificationHelper.SILENCE_EXTRA, 0);
                NotificationScheduler.cancelReminder(context, AlarmReceiver.class, notId);
                nh.cancelNotifications();

                return;
            }
        }

        int id = intent.getIntExtra("notification-id", 0);
        //Trigger the notification
        nh.createNotification("Prova", "Prova body", id);
    }
}
