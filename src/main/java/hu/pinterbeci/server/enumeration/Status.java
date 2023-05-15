package hu.pinterbeci.server.enumeration;

public enum Status {

    SERVER_UP("SERVER_UP"),
    SERVER_DOWN("SERVER_DOWN");

    //mivel final-é tettük, és kapott értéket, így nem tudunk, és nem is kell neki setter-t készíteni
    private final String status;

    Status(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }
}

