import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int airplaneModelCount = 0;
        int passengersCount = 0;
        int crewCountPerAirplane = 0;
        int airplanesCount = 0;
        int totalPassengersAirportCanServe = 0;
        String flight = "0";

        List<Airplane> airplanes = new ArrayList<>();
        List<String> crew = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/airport_db",
                "postgres",
                "root");
             Statement statement = connection.createStatement();
             ){

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as airplane_model_count FROM airplane_model WHERE id > 0");
            if (resultSet.next()){
                airplaneModelCount = Integer.parseInt(resultSet.getString("airplane_model_count"));
            }

            for (int i = 1; i <= airplaneModelCount; i++){
                resultSet = statement.executeQuery("SELECT model_seats FROM airplane_model WHERE id = " + i);
                if (resultSet.next()){
                    passengersCount = Integer.parseInt(resultSet.getString("model_seats"));
                }

                resultSet = statement.executeQuery("SELECT COUNT(*) as airplane_model_count1 FROM airplanes WHERE model_id = " + i);
                if (resultSet.next()){
                    airplanesCount = Integer.parseInt(resultSet.getString("airplane_model_count1"));
                }

                resultSet = statement.executeQuery("SELECT model_crew FROM airplane_model WHERE id = " + i);
                if (resultSet.next()){
                    crewCountPerAirplane = Integer.parseInt(resultSet.getString("model_crew"));
                }
                airplanes.add(new Airplane(airplanesCount, passengersCount, String.valueOf(i), String.valueOf(crewCountPerAirplane)));
            }

            for (Airplane airplane: airplanes){
                System.out.println(airplane);
            }

            for(int i = 1; i <= airplaneModelCount; i++){
                for(int j = 1; j <= airplanes.get(i - 1).getAirplanesCount(); j++){
                    resultSet = statement.executeQuery(new StringBuilder()
                            .append("SELECT drv.pilot_id, drv.model_id, drv.model_id FROM driving drv")
                            .append(" LEFT JOIN pilot p ON p.id=drv.pilot_id WHERE drv.model_id = ")
                            .append(airplanes.get(i - 1).getID())
                            .append(" AND drv.pilot_id NOT IN (")
                            .append(flight)
                            .append(") ORDER BY driving LIMIT ")
                            .append(airplanes.get(i - 1).getCrew()).toString());
                    while(resultSet.next()){
                        crew.add(resultSet.getString("pilot_id"));
                    }
                    if(crew.size() < Integer.parseInt(airplanes.get(i - 1).getCrew())){
                        continue;
                    }else{
                        totalPassengersAirportCanServe += airplanes.get(i - 1).getPassengersCount();
                        flight = flight + "," + crew.get(0) + "," + crew.get(1);
                    }
                }
            }
            System.out.println("The maximum number of passengers that airport can serve is: "
                    + totalPassengersAirportCanServe );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}