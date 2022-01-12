package org.binchoo.study.spring.aws.rds.service;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.aws.rds.entity.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * packageName : org.binchoo.study.spring.aws.rds.service
 * fileName : SimpleRdsWriter
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */

@Slf4j
public class JdbcPersonService implements PersonService {

    private final String INSERT_USER = "INSERT into person (id, last_name, first_name) values (?, ?, ?)";

    private final String FIND_BY_ID = "SELECT * from person WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    private PersonRowMapper rowMapper = new PersonRowMapper();

    public JdbcPersonService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * from person", rowMapper);
    }

    @Override
    public Person findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, rowMapper, new Object[] {id});
    }

    @Transactional
    public void save(Person person) {
        int result = jdbcTemplate.update(INSERT_USER, new Object[] {
                person.getId(), person.getLastName(), person.getFirstName()});
        log.info(result + "개 튜플 삽입 성공.");
    }

    private static class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Person.builder()
                    .id(rs.getLong("id"))
                    .lastName(rs.getString("last_name"))
                    .firstName(rs.getString("first_name"))
                    .build();
        }
    }
}
