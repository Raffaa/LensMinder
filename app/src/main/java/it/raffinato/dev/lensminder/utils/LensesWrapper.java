package it.raffinato.dev.lensminder.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.Serializable;

import it.raffinato.dev.lensminder.room.LensesModel;
import it.raffinato.dev.lensminder.utils.enums.Duration;

import static it.raffinato.dev.lensminder.LensMinderApplication.DBDateTimeFormat;

public class LensesWrapper implements Serializable {

    private final Lens lxLens;
    private final Lens rxLens;

    private final int _id;

    public LensesWrapper(Lens lxLens, Lens rxLens, Integer id) {
        this.lxLens = lxLens;
        this.rxLens = rxLens;
        this._id = id;
    }

    public LensesWrapper(Lens lxLens, Lens rxLens) {
        this.lxLens = lxLens;
        this.rxLens = rxLens;
        this._id = -1;
    }

    public LensesWrapper(Lens lens) {
        this.lxLens = lens;
        this.rxLens = lens;
        this._id = -1;
    }

    public DateTime getLxLensInitialDate() {
        return this.lxLens.getInitialDate();
    }

    public Duration getLxLensDuration() {
        return this.lxLens.getDuration();
    }

    public DateTime getLxLensExpDate() {
        return this.lxLens.getExpDate();
    }

    public boolean isLxLensActive() {
        return this.lxLens.isActive();
    }

    public Integer getLxLensRemainingTime() {
        return this.lxLens.getRemainingTime();
    }

    public DateTime getRxLensInitialDate() {
        return this.rxLens.getInitialDate();
    }

    public Duration getRxLensDuration() {
        return this.rxLens.getDuration();
    }

    public DateTime getRxLensExpDate() {
        return this.rxLens.getExpDate();
    }

    public boolean isRxLensActive() {
        return this.rxLens.isActive();
    }

    public boolean hasLensesActive() {
        return isLxLensActive() || isRxLensActive();
    }

    public Integer getRxLensRemainingTime() {
        return this.rxLens.getRemainingTime();
    }

    public int get_id() {
        return _id;
    }

    public LensesModel toModel() {
        LensesModel model = new LensesModel();
        //LxLens
        model.setInitDateLx(getLxLensInitialDate().toString(DateTimeFormat.forPattern(DBDateTimeFormat)));
        model.setDurationLx(getLxLensDuration().getTime());
        model.setActiveLx(isLxLensActive());
        //RxLens
        model.setInitDateRx(getRxLensInitialDate().toString(DateTimeFormat.forPattern(DBDateTimeFormat)));
        model.setDurationRx(getRxLensDuration().getTime());
        model.setActiveRx(isRxLensActive());
        //id
        if(_id > 0) {
            model.setId(_id);
        }

        return model;
    }

    public boolean areEqual() {
        return getLxLensDuration() == getRxLensDuration();
    }
}
