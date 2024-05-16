package org.example.initialization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.example.configuration.DbConfigurator;
import org.example.model.Hotel;
import org.example.model.Room;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataInitializer {
    private static final String HOTELS_JSON_PATH = "C://Users//elena//IdeaProjects//HotelApp//src//hotels.json";
    private static final String INSERT_HOTEL_SQL = "INSERT INTO hotels (id, name, latitude, longitude) VALUES (?, ?, ?, ?)";
    private static final String INSERT_ROOM_SQL = "INSERT INTO rooms (number, type, price, isAvailable, hotel_id) VALUES (?, ?, ?, ?, ?)";

    public static void main(String[] args) {
        importHotelsAndRoomsFromJson(HOTELS_JSON_PATH);
    }

    public static void importHotelsAndRoomsFromJson(String filePath) {
        try (Connection connection = DriverManager.getConnection(DbConfigurator.URL, DbConfigurator.USERNAME, DbConfigurator.PASSWORD);
             PreparedStatement hotelPreparedStatement = connection.prepareStatement(INSERT_HOTEL_SQL);
             PreparedStatement roomPreparedStatement = connection.prepareStatement(INSERT_ROOM_SQL)) {

            JSONTokener tokener = new JSONTokener(filePath);
            JSONArray hotelsArray = new JSONArray(tokener);

            for (int i = 0; i < hotelsArray.length(); i++) {
                JSONObject hotelJson = hotelsArray.getJSONObject(i);
                Hotel hotel = parseHotel(hotelJson);

                // Insert hotel
                hotelPreparedStatement.setInt(1, hotel.getId());
                hotelPreparedStatement.setString(2, hotel.getName());
                hotelPreparedStatement.setDouble(3, hotel.getLatitude());
                hotelPreparedStatement.setDouble(4, hotel.getLongitude());
                hotelPreparedStatement.executeUpdate();

                // Insert rooms for the hotel
                for (Room room : hotel.getRooms()) {
                    roomPreparedStatement.setInt(1, room.getNumber());
                    roomPreparedStatement.setInt(2, room.getType());
                    roomPreparedStatement.setDouble(3, room.getPrice());
                    roomPreparedStatement.setBoolean(4, room.isAvailable());
                    roomPreparedStatement.setInt(5, hotel.getId());
                    roomPreparedStatement.executeUpdate();
                }
            }

            System.out.println("Hotels and rooms imported successfully.");
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static Hotel parseHotel(JSONObject hotelJson) throws JSONException {
        int id = hotelJson.getInt("id");
        String name = hotelJson.getString("name");
        double latitude = hotelJson.getDouble("latitude");
        double longitude = hotelJson.getDouble("longitude");

        JSONArray roomsArray = hotelJson.getJSONArray("rooms");
        ArrayList<Room> rooms = new ArrayList<>();
        for (int j = 0; j < roomsArray.length(); j++) {
            JSONObject roomJson = roomsArray.getJSONObject(j);
            Room room = parseRoom(roomJson);
            rooms.add(room);
        }

        Hotel hotel = new Hotel(id, name, latitude, longitude);
        hotel.setRooms(rooms.toArray(new Room[0]));

        return hotel;
    }

    private static Room parseRoom(JSONObject roomJson) throws JSONException {
        int number = roomJson.getInt("roomNumber");
        int type = roomJson.getInt("type");
        double price = roomJson.getDouble("price");
        boolean isAvailable = roomJson.getBoolean("isAvailable");

        Room room = new Room(0, number, type, price, isAvailable, 0); // Dummy ID and hotel_id for now
        return room;
    }
}
