import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HotelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Hotel> getAllHotels() {
        String sql = "SELECT * FROM Hotel";
        return jdbcTemplate.query(sql, new HotelRowMapper());
    }

    public Hotel getHotelById(Long id) {
        String sql = "SELECT * FROM Hotel WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new HotelRowMapper());
    }

    public void createHotel(Hotel hotel) {
        String sql = "INSERT INTO Hotel (name, latitude, longitude) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, hotel.getName(), hotel.getLatitude(), hotel.getLongitude());
    }

    public void updateHotel(Hotel hotel) {
        String sql = "UPDATE Hotel SET name = ?, latitude = ?, longitude = ? WHERE id = ?";
        jdbcTemplate.update(sql, hotel.getName(), hotel.getLatitude(), hotel.getLongitude(), hotel.getId());
    }

    public void deleteHotel(Long id) {
        String sql = "DELETE FROM Hotel WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class HotelRowMapper implements RowMapper<Hotel> {
        @Override
        public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Hotel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );
        }
    }
}
