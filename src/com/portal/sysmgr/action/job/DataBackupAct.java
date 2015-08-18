package com.portal.sysmgr.action.job;

import com.javassf.basic.plugin.springmvc.RealPathResolver;
import com.javassf.basic.utils.DateUtils;
import com.portal.sysmgr.entity.DatabaseConfig;
import com.portal.sysmgr.service.DataBackService;
import com.portal.sysmgr.service.DatabaseConfigService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class DataBackupAct {

	@Autowired
	private DatabaseConfigService configService;

	@Autowired
	private DataBackService dataBackService;

	@Autowired
	private RealPathResolver realPathResolver;

	public void dataBackupJob() {
		DatabaseConfig config = this.configService.findUnique();
		if (config != null) {
			if ((DateUtils.daysBetween(new Date(), config.getPreTime()) > config.getInter().intValue()) && (DateUtils.getSameHourTime(config.getBackupTime()).booleanValue()))
				try {
					backupdata();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	private void backupdata() throws SQLException {
		String backpath = this.realPathResolver.get("backup");
		File backDirectory = new File(backpath);
		if (!backDirectory.exists()) {
			backDirectory.mkdir();
		}
		List<String> tables = this.dataBackService.getTables();
		String backFilePath = backpath + "/" + DateUtils.getNowToString() + ".sql";
		File file = new File(backFilePath);

		OutputStreamWriter writer = null;
		try {
			FileOutputStream out = new FileOutputStream(file);
			writer = new OutputStreamWriter(out, "utf8");
			writer.write("SET FOREIGN_KEY_CHECKS = 0");
			writer.write(";--end\r\n");
			for (int i = 0; i < tables.size(); i++) {
				backupTable(writer, (String) tables.get(i));
			}
			for (int i = 0; i < tables.size(); i++) {
				try {
					String sf = this.dataBackService.getAllExportedKeys((String) tables.get(i));
					if (StringUtils.isNotBlank(sf))
						writer.write(sf);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				writer.flush();
			}
			writer.write("SET FOREIGN_KEY_CHECKS = 1");
			writer.write(";--end\r\n");
			writer.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String backupTable(OutputStreamWriter writer, String tablename) throws IOException {
		writer.write(this.dataBackService.getWholeTableSql(tablename));
		writer.flush();
		return tablename;
	}
}
