package com.sc504.huracan.repository;


import com.sc504.huracan.model.SystemUser;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class SystemUserRepository {

  private final JdbcTemplate jdbcTemplate;

  public SystemUserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createSystemUser(SystemUser systemUser) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("system_user_pkg")
        .withProcedureName("create_system_user_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_password", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_role", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_name", systemUser.getName(),
        "p_email", systemUser.getEmail(),
        "p_password", systemUser.getPassword(),
        "p_role", systemUser.getRole()
    ));
  }

  public SystemUser getSystemUserById(Long id) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("system_user_pkg")
        .withProcedureName("get_system_user_by_id_sp")
        .declareParameters(
            new SqlParameter("p_id", Types.NUMERIC),
            new SqlOutParameter("p_name", Types.VARCHAR),
            new SqlOutParameter("p_email", Types.VARCHAR),
            new SqlOutParameter("p_password", Types.VARCHAR),
            new SqlOutParameter("p_role", Types.VARCHAR),
            new SqlOutParameter("p_created_at", Types.TIMESTAMP)
        );

    Map<String, Object> result = jdbcCall.execute(Map.of("p_id", id));

    return new SystemUser(
        id,
        (String) result.get("p_name"),
        (String) result.get("p_email"),
        (String) result.get("p_password"),
        (String) result.get("p_role"),
        (Timestamp) result.get("p_created_at")
    );
  }

  public void updateSystemUser(SystemUser systemUser) {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("system_user_pkg")
        .withProcedureName("update_system_user_sp");

    jdbcCall.addDeclaredParameter(new SqlParameter("p_id", Types.NUMERIC));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_name", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_email", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_password", Types.VARCHAR));
    jdbcCall.addDeclaredParameter(new SqlParameter("p_role", Types.VARCHAR));

    jdbcCall.execute(Map.of(
        "p_id", systemUser.getId(),
        "p_name", systemUser.getName(),
        "p_email", systemUser.getEmail(),
        "p_password", systemUser.getPassword(),
        "p_role", systemUser.getRole()
    ));
  }

  public boolean deleteSystemUser(Long id) {

    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("system_user_pkg")
        .withProcedureName("delete_system_user_sp");

    jdbcCall.execute(Map.of("p_id", id));

    return true;
  }

  public List<SystemUser> getAllSystemUsers() {
    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        .withCatalogName("system_user_pkg")
        .withProcedureName("get_all_system_user_sp")
        .returningResultSet("p_system_user_cursor",
            (RowMapper<SystemUser>) (rs, rowNum) -> new SystemUser(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role"),
                null
            ));

    Map<String, Object> result = jdbcCall.execute(Map.of());

    return result.get("p_system_user_cursor")!= null ?
        (List<SystemUser>) result.get("p_system_user_cursor") : List.of();
  }

}
