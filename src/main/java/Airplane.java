public class Airplane {
    private final int airplanesCount;
    private final int passengersCount;
    private final String id;
    private final String crew;

    public Airplane(int airplanesCount, int passengersCount, String id, String crew) {
        this.airplanesCount = airplanesCount;
        this.passengersCount = passengersCount;
        this.id = id;
        this.crew = crew;
    }

    public int getAirplanesCount() {
        return airplanesCount;
    }

    public int getPassengersCount() {
        return passengersCount;
    }

    public String getID() {
        return id;
    }

    public String getCrew() {
        return crew;
    }

    @Override
    public String toString() {
        return String.format("Airplane{id = %s, crew = %s, passengers = %s, airplanes at stock = %s}",
                id,
                crew,
                passengersCount,
                airplanesCount);
    }
}
