public class JSONStation {
    private String station_name;
    private String depth;

    public JSONStation(String station_name, String depth) {
        this.station_name = station_name;
        this.depth = depth;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }
}
