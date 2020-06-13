package it.raffinato.dev.lensminder.utils.enums;

public enum Duration {

    BIWEEKLY(14, 0), MONTHLY(30, 1), BIMONTHLY(60, 2), QUARTERLY(120, 3), ANNUAL(365, 4), UNKNOWN(1, 5);

    private final int t, id;

    Duration(int t, int id) {
        this.t = t;
        this.id = id;
    }

    public static Duration fromInt(int i) {
        switch (i) {
            case 14:
                return Duration.BIWEEKLY;
            case 30:
                return Duration.MONTHLY;
            case 60:
                return Duration.BIMONTHLY;
            case 120:
                return Duration.QUARTERLY;
            case 365:
                return Duration.ANNUAL;
            default:
                return Duration.UNKNOWN;
        }
    }

    public static Duration fromSpinnerSelection(int i) {
        switch (i) {
            case 0:
                return Duration.BIWEEKLY;
            case 1:
                return Duration.MONTHLY;
            case 2:
                return Duration.BIMONTHLY;
            case 3:
                return Duration.QUARTERLY;
            case 4:
                return Duration.ANNUAL;
            default:
                return null;
        }
    }


    public int getTime() {
        return this.t;
    }

    public int getId() {
        return id;
    }
}