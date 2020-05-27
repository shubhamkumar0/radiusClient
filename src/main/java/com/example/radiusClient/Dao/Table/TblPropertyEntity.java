package com.example.radiusClient.Dao.Table;

import com.example.radiusClient.Dao.PropertyEntity;
import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TblPropertyEntity {

    private TblPropertyEntity() {
        throw new IllegalStateException("Utility Class");
    }
    public static final String A_TABLE_NAME = "property_entity";

    public static final String C_ID = "id";
    public static final String C_D = "D";
    public static final String C_Latitude = "latitude";
    public static final String C_Longitude = "longitude";
    public static final String C_Price = "price";
    public static final String C_N_Bedroom = "n_bedroom";
    public static final String C_N_Bathroom = "n_bathroom";

    public static class TblPropertyEntityMapper implements RowMapper<Pair<Double, PropertyEntity>> {
        @Override
        public Pair<Double, PropertyEntity> mapRow(ResultSet rs, int rowNum) throws SQLException {
            PropertyEntity dao = new PropertyEntity();
            dao.setId(rs.getLong(C_ID));
            dao.setLatitude(rs.getDouble(C_Latitude));
            dao.setLongitude(rs.getDouble(C_Longitude));
            dao.setPrice(rs.getDouble(C_Price));
            dao.setnBedroom(rs.getInt(C_N_Bedroom));
            dao.setnBathroom(rs.getInt(C_N_Bathroom));
            Pair<Double, PropertyEntity> ans = new Pair<>(rs.getDouble(C_D),dao);
            return ans;
        }
    }
}
