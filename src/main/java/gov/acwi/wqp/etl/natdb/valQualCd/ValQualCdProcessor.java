package gov.acwi.wqp.etl.natdb.valQualCd;

import org.springframework.batch.item.ItemProcessor;

import gov.acwi.wqp.etl.natdb.BaseProcessor;


public class ValQualCdProcessor extends BaseProcessor implements ItemProcessor<ValQualCd, ValQualCd> {

	@Override
	public ValQualCd process(ValQualCd source) throws Exception {
		ValQualCd result = new ValQualCd();
		result.setValQualCd(trimString(source.getValQualCd()));
		result.setValQualTp(trimString(source.getValQualTp()));
		result.setValQualSrtNu(source.getValQualSrtNu());
		result.setValQualVldFg(source.isValQualVldFg());
		result.setValQualNm(trimString(source.getValQualNm()));
		result.setValQualDs(trimString(source.getValQualDs()));
		
		return result;
	}
}
