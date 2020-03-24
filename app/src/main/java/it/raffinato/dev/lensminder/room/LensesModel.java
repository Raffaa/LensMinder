package it.raffinato.dev.lensminder.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lenses")
public class LensesModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private Integer id;

    @ColumnInfo(name = "initdatelx")
    private String initDateLx;

    @ColumnInfo(name = "durationlx")
    private Integer durationLx;

    @ColumnInfo(name = "isactivelx")
    private Boolean isActiveLx;

    @ColumnInfo(name = "initdaterx")
    private String initDateRx;

    @ColumnInfo(name = "durationrx")
    private Integer durationRx;

    @ColumnInfo(name = "isactiverx")
    private Boolean isActiveRx;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getInitDateLx() {
        return initDateLx;
    }

    public void setInitDateLx(String initDateLx) {
        this.initDateLx = initDateLx;
    }

    public Integer getDurationLx() {
        return durationLx;
    }

    public void setDurationLx(Integer durationLx) {
        this.durationLx = durationLx;
    }

    public Boolean getActiveLx() {
        return isActiveLx;
    }

    public void setActiveLx(Boolean activeLx) {
        isActiveLx = activeLx;
    }

    public String getInitDateRx() {
        return initDateRx;
    }

    public void setInitDateRx(String initDateRx) {
        this.initDateRx = initDateRx;
    }

    public Integer getDurationRx() {
        return durationRx;
    }

    public void setDurationRx(Integer durationRx) {
        this.durationRx = durationRx;
    }

    public Boolean getActiveRx() {
        return isActiveRx;
    }

    public void setActiveRx(Boolean activeRx) {
        isActiveRx = activeRx;
    }

    public LensesModel deactivate() {
        this.setActiveLx(false);
        this.setActiveRx(false);
        return this;
    }
}
