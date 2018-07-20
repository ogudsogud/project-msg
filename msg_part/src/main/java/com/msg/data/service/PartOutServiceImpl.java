package com.msg.data.service;

import com.msg.data.model.PartOutModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by yoga.wiguna on 10/07/2018.
 */

@Transactional
@Repository
public class PartOutServiceImpl implements PartOutService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    class PartsRowMapp implements RowMapper<PartOutModel> {

        @Override
        public PartOutModel mapRow(ResultSet rs, int i) throws SQLException {
            PartOutModel partOutModel = new PartOutModel();
            partOutModel.setTicket_no(rs.getString("ticket_no"));
            partOutModel.setPart_name(rs.getString("id_unit_parts"));
            partOutModel.setPart_name(rs.getString("id_unit_institution"));
            partOutModel.setPart_name(rs.getString("id_part_number"));
            partOutModel.setPart_name(rs.getString("part_name"));
            partOutModel.setPart_name(rs.getString("institution_name"));
            partOutModel.setIns_unit_name(rs.getString("ins_unit_name"));
            partOutModel.setUnit_parts_name(rs.getString("unit_parts_name"));
            partOutModel.setDescription(rs.getString("description"));
            partOutModel.setQuantity_unit(rs.getInt("quantity_unit"));
            partOutModel.setRequested_by(rs.getString("requested_by"));
            partOutModel.setRequested_on(rs.getString("requested_on"));
            partOutModel.setApproved_by(rs.getString("approved_by"));
            partOutModel.setApproved_on(rs.getString("approved_on"));
            partOutModel.setUpdated_by(rs.getString("updated_by"));
            partOutModel.setUpdated_on(rs.getString("updated_on"));
            partOutModel.setStatus(rs.getInt("status"));
            return partOutModel;
        }
    }

    //untuk menampilkan data parts stock-out
    @Override
    public List<PartOutModel> getPartsStockOut() {
        String sql = "SELECT * FROM trx_part_stock_out WHERE status = 1";
        RowMapper<PartOutModel> rowMapper = new PartsRowMapp();
        return this.jdbcTemplate.query(sql,rowMapper);
    }

    //untuk insert parts stock-out
    @Override
    public boolean insertPartsOut(PartOutModel partOutModel) {

        String sql = "INSERT INTO trx_part_stock_out (" +
                "ticket_no," +
                "id_unit_parts," +
                "id_unit_institution, " +
                "id_part_number, " +
                "part_name, " +
                "institution_name, " +
                "ins_unit_name,  " +
                "unit_parts_name, " +
                "description, " +
                "quantity_unit, " +
                "requested_by, " +
                "requested_on, " +
                "approved_by, " +
                "approved_on, " +
                "updated_by, " +
                "updated_on, " +
                "status) " +

                "VALUES (    ?, ?,?,?,?,?,?,?,?,?,?,(SELECT NOW()),?,(SELECT NOW()),?,(SELECT NOW()),1)";
        jdbcTemplate.update(sql,
                partOutModel.getTicket_no(),
                partOutModel.getId_unit_parts(),
                partOutModel.getId_unit_institution(),
                partOutModel.getId_part_number(),
                partOutModel.getPart_name(),
                partOutModel.getInstitution_name(),
                partOutModel.getIns_unit_name(),
                partOutModel.getUnit_parts_name(),
                partOutModel.getDescription(),
                partOutModel.getQuantity_unit(),
                partOutModel.getRequested_by(),
//                partOutModel.getRequested_on(),
                partOutModel.getApproved_by(),
//                partOutModel.getApproved_on(),
                partOutModel.getUpdated_by()
//                partOutModel.getUpdated_on()
//                partOutModel.getStatus()
        );
        return false;
    }

    //jika insert parts-out sudah ada
    @Override
    public boolean isPartsOutExist(String po_number) {
        String sql = "SELECT count(*) from trx_part_stock_out WHERE ticket_no = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, po_number);
        if(count == 0) {
            return true;
        } else {
            return false;
        }
    }

    //untuk mencari data parts berdasarkan parameter
    @Override
    public PartOutModel getByPoNumb(String ticket_no) {
        String sql = "SELECT * FROM trx_part_stock_out WHERE ticket_no = ?";
        RowMapper<PartOutModel> rowMapper = new PartsRowMapp();
        PartOutModel partOutModel = jdbcTemplate.queryForObject(sql, rowMapper, ticket_no);
        return partOutModel;
    }

    @Override
    public PartOutModel getByPartNumb(String part_number) {
        String sql = "SELECT * FROM trx_part_stock_out WHERE part_number = ?";
        RowMapper<PartOutModel> rowMapper = new PartsRowMapp();
        PartOutModel partOutModel = jdbcTemplate.queryForObject(sql, rowMapper, part_number);
        return partOutModel;
    }

    //menghapus data part yg keluar
    @Override
    public void deleteByTicket(String ticket_no) {
        String sql = "UPDATE trx_part_stock_out SET status = 0 WHERE ticket_no = ?";
        jdbcTemplate.update(sql, ticket_no);
    }

}
