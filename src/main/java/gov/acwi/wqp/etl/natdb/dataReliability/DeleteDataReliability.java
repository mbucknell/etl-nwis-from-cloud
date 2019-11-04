package gov.acwi.wqp.etl.natdb.dataReliability;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.BaseTruncateNwisTable;

@Component
@StepScope
public class DeleteDataReliability extends BaseTruncateNwisTable {

	@Autowired
	public DeleteDataReliability(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "data_reliability");
	}

}
