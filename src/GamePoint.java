public enum GamePoint {
    LOVE,
    FIFTEEN,
    THIRTY,
    FORTY,
    AD;

    public static int toInt(GamePoint gamePoint) {
        switch (gamePoint) {
            case LOVE:
                return 0;
            case FIFTEEN:
                return 1;
            case THIRTY:
                return 2;
            case FORTY:
                return 3;
            case AD:
                return 4;
        }
        return 999;
    }

    @Override
    public String toString() {
        String s = "";
        switch (this) {
            case LOVE:
                s = "0";
                break;
            case FIFTEEN:
                s = "15";
                break;
            case THIRTY:
                s = "30";
                break;
            case FORTY:
                s = "40";
                break;
            case AD:
                s = "AD";
                break;
        }
        return s;
    }
}
