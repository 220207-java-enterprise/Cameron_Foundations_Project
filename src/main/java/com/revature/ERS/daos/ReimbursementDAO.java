package com.revature.ERS.daos;
import com.revature.ERS.models.*;
import com.revature.ERS.util.ConnectionFactory;
import com.revature.ERS.util.exceptions.DataSourceException;
import com.revature.ERS.util.exceptions.ResourcePersistenceException;
import com.revature.ERS.util.Bytea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<Reimbursements> {

    private final String rootSelect = "SELECT " +
            "Reimbursement.reim_id, Reimbursement.amount, Reimbursement.submitted, Reimbursement.resolved, Reimbursement.description, " +
            "Reimbursement.receipt, Reimbursement.payment_id, Reimbursement.author_id, Reimbursement.resolver_id, Reimbursement.status_id, " +
            "Reimbursement.type_id, Reimbursement_status.status, Reimbursement_type.type " +
            "FROM Reimbursement " +
            "JOIN Reimbursement_status " +
            "ON Reimbursement.status_id = Reimbursement_status.status_id " +
            "JOIN Reimbursements_type " +
            "ON Reimbursement.type_id = Reimbursement_type.type_id ";

    /// save new reimbursement request

    @Override
    public void save(Reimbursements newReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Reimbursement VALUES (?, ?, ?, ?, ?, NULL, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newReimbursement.getReim_id());
            pstmt.setDouble(2, newReimbursement.getAmount());
            pstmt.setTimestamp(3, newReimbursement.getSubmitted());
            pstmt.setTimestamp(4, newReimbursement.getResolved());
            pstmt.setString(5, newReimbursement.getDescription());
            pstmt.setString(6, newReimbursement.getPayment_id());
            pstmt.setString(7, newReimbursement.getAuthor_id());
            pstmt.setString(8, newReimbursement.getResolver_id());
            pstmt.setString(9, newReimbursement.getStatus().getStatus_id());
            pstmt.setString(10, newReimbursement.getType().getType_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to transfer data to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    //view reimbursement by reim_id
    @Override
    public Reimbursements getById(String id) {

        Reimbursements reimbursement = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE reim_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reimbursement = new Reimbursements();
                reimbursement.setReim_id(rs.getString("reim_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor_id(rs.getString("author_id"));
                reimbursement.setResolver_id(rs.getString("resolver_id"));
                reimbursement.setStatus(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursement.setType(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));

            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursement;

    }

    //view all reimbursement requests(FM)

    @Override
    public List<Reimbursements> getAll() {

        List<Reimbursements> reimbursements = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            if (rs.next()) {
                Reimbursements reimbursement = new Reimbursements();
                reimbursement.setReim_id(rs.getString("reim_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor_id(rs.getString("author_id"));
                reimbursement.setResolver_id(rs.getString("resolver_id"));
                reimbursement.setStatus(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursement.setType(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));
                reimbursements.add(reimbursement);
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursements;
    }

    //update reimbursement request(FM)
    @Override
    public void update(Reimbursements updatedReimbursement) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE Reimbursement " +
                    "SET amount = ?, " +
                    "resolved = ?, " +
                    "description = ?, " +
                    "receipt = ?, " +
                    "payment_id = ? " +
                    "status_id = ? " +
                    "WHERE reim_id = ?");
            pstmt.setDouble(1, updatedReimbursement.getAmount());
            pstmt.setTimestamp(2, updatedReimbursement.getResolved());
            pstmt.setString(3, updatedReimbursement.getDescription());
            pstmt.setBinaryStream(4, updatedReimbursement.getReceipt().getBinaryStream());
            pstmt.setString(5, updatedReimbursement.getPayment_id());
            pstmt.setString(6, updatedReimbursement.getStatus().getStatus_id());
            pstmt.setString(7, updatedReimbursement.getType().getType_id());


            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Data update attempt unsuccessful");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

    }


    @Override
    public void deleteById(Reimbursements deleteReimbursement) {

        {

            try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

                conn.setAutoCommit(false);
                PreparedStatement pstmtStatus = conn.prepareStatement("DELETE FROM Reimbursement_status WHERE status_id = ?");
                pstmtStatus.setString(1, deleteReimbursement.getStatus().getStatus_id());

                int rowsInsertedStatus = pstmtStatus.executeUpdate();
                if (rowsInsertedStatus != 1) {
                    conn.rollback();
                    throw new ResourcePersistenceException("Failed to delete user data from data source");
                }

                conn.commit();

                conn.setAutoCommit(false);
                PreparedStatement pstmtType = conn.prepareStatement("DELETE FROM Reimbursement_type WHERE type_id = ?");
                pstmtType.setString(1, deleteReimbursement.getType().getType_id());

                int rowsInsertedType = pstmtType.executeUpdate();
                if (rowsInsertedType != 1) {
                    conn.rollback();
                    throw new ResourcePersistenceException("Failed to delete user from data source");
                }

                conn.commit();

            } catch (SQLException e) {
                throw new DataSourceException(e);
            }

        }
    }
}

