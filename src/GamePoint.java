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
}
