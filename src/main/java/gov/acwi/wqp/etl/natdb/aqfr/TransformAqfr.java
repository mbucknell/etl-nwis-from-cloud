package gov.acwi.wqp.etl.natdb.aqfr;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import gov.acwi.wqp.etl.Application;

@Component
public class TransformAqfr {
	
	@Autowired
	@Qualifier(Application.DATASOURCE_NWIS_QUALIFIER)
	private DataSource dataSourceNwis;

	@Autowired
	@Qualifier(Application.DATASOURCE_NATDB_QUALIFIER)
	private DataSource dataSourceNatdb;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public JdbcCursorItemReader<Aqfr> aqfrReader() {
		return new JdbcCursorItemReaderBuilder<Aqfr>()
				.dataSource(dataSourceNatdb)
				.name("natdbAqfr")
				.sql("select aqfr_state.country_cd, aqfr_state.state_cd, aqfr.aqfr_cd, aqfr.aqfr_nm, aqfr.aqfr_dt aqfr_md from aqfr join aqfr_state on aqfr.aqfr_cd = aqfr_state.aqfr_cd")
				.rowMapper(new AqfrRowMapper())
				.build();
	}

	@Bean
	public AqfrProcessor aqfrProcessor() {
		return new AqfrProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Aqfr> aqfrWriter() {
		return new JdbcBatchItemWriterBuilder<Aqfr>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO aqfr" + 
						" (country_cd, state_cd, aqfr_cd, aqfr_nm, aqfr_md) VALUES (:countryCd, :stateCd, :aqfrCd, :aqfrNm, :aqfrMd)")
				.dataSource(dataSourceNwis)
				.build();
	}
}
