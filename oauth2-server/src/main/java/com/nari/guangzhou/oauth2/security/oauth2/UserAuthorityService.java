package com.nari.guangzhou.oauth2.security.oauth2;

import com.nari.guangzhou.oauth2.model.UserAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oauth_user_authority表的业务类
 *
 * @author Zongwei
 * @date 2019/9/30 14:01
 */
@Slf4j
public class UserAuthorityService {

    private static final String DEFAULT_FIND_STATEMENT = "select authority_id, user_id, authority_type, authority from oauth_user_authority where user_id = :user_id";
    private RowMapper<UserAuthority> rowMapper = new UserAuthorityService.UserAuthorityRowMapper();
    private JdbcListFactory listFactory;

    public UserAuthorityService(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    public List<UserAuthority> queryUserAuthority(String userId) {
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("user_id", userId);
        return this.listFactory.getList(DEFAULT_FIND_STATEMENT, queryMap, this.rowMapper);
    }

    @Slf4j
    private static class UserAuthorityRowMapper implements RowMapper<UserAuthority> {

        @Override
        public UserAuthority mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserAuthority details = new UserAuthority();
            details.setAuthorityId(resultSet.getLong("authority_id"));
            details.setUserId(resultSet.getString("user_id"));
            details.setAuthorityType(resultSet.getString("authority_type"));
            String authority = resultSet.getString("authority");
            if (!StringUtils.isEmpty(authority)) {
                details.setAuthority(StringUtils.commaDelimitedListToSet(authority));
            }
            return details;
        }
    }

}
