package com.javassf.basic.developer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * 模板代码生成器，例子：com.portal.pms.template包中
 *
 */
public class ModuleGenerator {
	private static final Logger log = LoggerFactory.getLogger(ModuleGenerator.class);
	public static final String SPT = File.separator;//文件分隔符
	public static final String ENCODING = "UTF-8";//编码
	private Properties prop = new Properties();
	private String packName;//配置文件所在包
	private String fileName;//配置文件名
	private File daoImplFile;//对应dao实现类文件
	private File daoFile;//对应dao接口文件
	private File managerFile;//对应manager接口类文件
	private File managerImplFile;//对应manager实现类文件
	private File actionFile;//对应action类文件
	private File pageListFile;//对应列表页面文件
	private File pageEditFile;//对应编辑页面文件
	private File pageAddFile;//对应添加页面文件
	private File daoImplTpl;//对应dao实现类模板代码文件
	private File daoTpl;//对应dao接口类模板代码文件
	private File managerTpl;//对应manager接口类模板代码文件
	private File managerImplTpl;//对应manager实现类模板代码文件
	private File actionTpl;//对应action类模板代码文件
	private File pageListTpl;//对应列表页面文件
	private File pageEditTpl;;//对应编辑页面文件
	private File pageAddTpl;//对应添加页面文件

	public ModuleGenerator(String packName, String fileName) {
		this.packName = packName;
		this.fileName = fileName;
	}

	/**
	 * 加载配置文件
	 */
	private void loadProperties() {
		try {
			FileInputStream fileInput = new FileInputStream(getFilePath(this.packName, this.fileName));
			this.prop.load(fileInput);//加载对应的配置文件
			String entityUp = this.prop.getProperty("Entity");//获取要生成的实体类名
			if ((entityUp == null) || (entityUp.trim().equals(""))) {
				log.warn("Entity not specified, exit!");
				return;
			}
			String entityLow = entityUp.substring(0, 1).toLowerCase() + entityUp.substring(1);//将实体类名首字母变小写
			this.prop.put("entity", entityLow);//加载到prop
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取各个文件路径
	 */
	private void prepareFile() {
		//获取要生成的dao实体类文件的路径
		String daoImplFilePath = getFilePath(this.prop.getProperty("dao_impl_p"), this.prop.getProperty("Entity") + "DaoImpl.java");
		this.daoImplFile = new File(daoImplFilePath);
		
		//获取要生成的dao接口类文件的路径
		String daoFilePath = getFilePath(this.prop.getProperty("dao_p"), this.prop.getProperty("Entity") + "Dao.java");
		this.daoFile = new File(daoFilePath);
		
		//获取要生成的manager接口类文件的路径
		String managerFilePath = getFilePath(this.prop.getProperty("manager_p"), this.prop.getProperty("Entity") + "Service.java");
		this.managerFile = new File(managerFilePath);

		//获取要生成的manager实体类文件的路径
		String managerImplFilePath = getFilePath(this.prop.getProperty("manager_impl_p"), this.prop.getProperty("Entity") + "ServiceImpl.java");
		this.managerImplFile = new File(managerImplFilePath);
		
		//获取要生成的action实体类文件的路径
		String actionFilePath = getFilePath(this.prop.getProperty("action_p"), this.prop.getProperty("Entity") + "Act.java");
		this.actionFile = new File(actionFilePath);

		//获取要生成的页面文件目录
		String pagePath = "WebContent/WEB-INF/" + this.prop.getProperty("config_sys") + "/" + this.prop.getProperty("config_entity") + "/";
		this.pageListFile = new File(pagePath + "list.html");
		this.pageEditFile = new File(pagePath + "edit.html");
		this.pageAddFile = new File(pagePath + "add.html");
	}

	/**
	 * 获取模板文件
	 */
	private void prepareTemplate() {
		
		String tplPack = this.prop.getProperty("template_dir");
		
		this.daoImplTpl = new File(getFilePath(tplPack, "dao_impl.txt"));
		this.daoTpl = new File(getFilePath(tplPack, "dao.txt"));
		this.managerImplTpl = new File(getFilePath(tplPack, "manager_impl.txt"));
		this.managerTpl = new File(getFilePath(tplPack, "manager.txt"));
		this.actionTpl = new File(getFilePath(tplPack, "action.txt"));
		this.pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
		this.pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
		this.pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
	}

	/**
	 * 写入文件
	 * @param file
	 * @param s
	 * @throws IOException
	 */
	private static void stringToFile(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, "UTF-8");
	}

	/**
	 * 判断是否生成，并写入文件
	 */
	private void writeFile() {
		try {
			if ("true".equals(this.prop.getProperty("is_dao"))) {
				stringToFile(this.daoImplFile, readTpl(this.daoImplTpl));
				stringToFile(this.daoFile, readTpl(this.daoTpl));
			}
			if ("true".equals(this.prop.getProperty("is_manager"))) {
				stringToFile(this.managerImplFile, readTpl(this.managerImplTpl));
				stringToFile(this.managerFile, readTpl(this.managerTpl));
			}
			if ("true".equals(this.prop.getProperty("is_action"))) {
				stringToFile(this.actionFile, readTpl(this.actionTpl));
			}
			if ("true".equals(this.prop.getProperty("is_page"))) {
				stringToFile(this.pageListFile, readTpl(this.pageListTpl));
				stringToFile(this.pageAddFile, readTpl(this.pageAddTpl));
				stringToFile(this.pageEditFile, readTpl(this.pageEditTpl));
			}
		} catch (IOException e) {
			log.warn("write file faild! " + e.getMessage());
		}
	}

	/**
	 * 替换关键字
	 * @param tpl
	 * @return
	 */
	private String readTpl(File tpl) {
		String content = null;
		try {
			content = FileUtils.readFileToString(tpl, "UTF-8");
			Set ps = this.prop.keySet();
			for (Iterator localIterator = ps.iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				String key = (String) o;
				String value = this.prop.getProperty(key);
				content = content.replaceAll("\\#\\{" + key + "\\}", value);
			}
		} catch (IOException e) {
			log.warn("read file faild. " + e.getMessage());
		}
		return content;
	}

	/**
	 * 将包名改为路径
	 * @param packageName
	 * @param name
	 * @return
	 */
	private String getFilePath(String packageName, String name) {
		String path = packageName.replaceAll("\\.", "/");
		return "src/" + path + "/" + name;
	}

	//生成代码步奏
	public void generate() {
		loadProperties();
		prepareFile();
		prepareTemplate();
		writeFile();
	}

}
