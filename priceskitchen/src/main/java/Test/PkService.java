package Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PkService {
	@Autowired
	private WPbean wpbean;
	@Autowired
	private PkjJdbcDAO pKJdbcDAO;

	public PkService(PkjJdbcDAO pKJdbcDAO, WPbean wpbean) {
		this.pKJdbcDAO = pKJdbcDAO;
		this.wpbean = wpbean;
	}

	public static void main(String[] args) throws IOException {
		String jurl = "http://data.coa.gov.tw/Service/OpenData/FromM/FarmTransData.aspx?$top=1";// UTF-8
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.config.xml");
		// 由Jsoup產生所取資料轉換字串
		String jtext = Jsoup.connect(jurl).ignoreContentType(true).get().body().text();
		JSONArray jsonArray = new JSONArray(jtext);

		PkService pkService = (PkService) context.getBean("pkService");
		pkService.updatePrice(jsonArray);

		((ConfigurableApplicationContext) context).close();
	}

	public WPbean updatePrice(JSONArray jsonArray) {
		Integer i = 0;
		Integer jID = new Integer(1);// 流水號
		WPbean bean = new WPbean();
		/*
		 * 這邊用bean封裝意義不大,因我們只需要將每一筆資料進行上傳資料庫,還無須考慮crd的其他方向
		 * 使用bean存取主要是以後可用hibernate的功能所需
		 */
		for (i = 0; i < jsonArray.length(); i++) {
			JSONObject result = (JSONObject) jsonArray.get(i);
			String jIngre_name = new String((String) result.get("作物名稱"));
			/* 放入資料庫時將作物代號換成類別名(Class)寫死 */
			String jClass = new String((String) result.get("作物代號"));
			String jUpdate_date = new String((String) result.get("交易日期"));
			Double jPrice_upper = new Double((Double)result.get("上價"));
			Double jPrice_middle = new Double((Double) result.get("中價"));
			Double jPrice_bottom = new Double((Double) result.get("下價"));
			Double jPrice_average = new Double((Double) result.get("平均價"));
			String market = new String((String) result.get("市場名稱"));
			/* 資料庫沒有下面兩項資料欄位 */
			//Double jtPrice = new Double((Double) result.get("交易量"));
			//String jmNumb = new String((String) result.get("市場代號"));// (資料庫沒此欄位)
			/*
			 * 蔬菜:[A-Z]{1,3}[0-9]{1,2} 水果:[A-Z]{1}[0-9]{1,3}
			 * 水果特別編號(("[0-9]{2,4}")): 11 119 12 129 22 229 31 32 30 41 42 43 45
			 * 459 46 469 51 50 61 62 71 72 73 74 70 811 819 812 829 839 849 859
			 * 869 879 91 919
			 */
			if (jClass.matches("[A-Z]{1,3}[0-9]{1,2}")) {
				java.sql.Date update_Date = this.formatDateTime(jUpdate_date);
				BigDecimal price_Upper = this.converBigDecimal(jPrice_upper);
				BigDecimal price_Middle = this.converBigDecimal(jPrice_middle);
				BigDecimal price_Bottom = this.converBigDecimal(jPrice_bottom);
				BigDecimal price_Average = this.converBigDecimal(jPrice_average);
				
				/*
				  //每一筆資料可以先在進資料庫時,顯示出來
				  System.out.println("跟新次數="+jID);//篩選需要改jID
				  System.out.println("作物名稱="+jIngre_name);
				  System.out.println("作物代號="+jClass);//目前先取代為類別名稱
				  System.out.println("交易日期="+jUpdate_date);
				  System.out.println("上價="+jPrice_upper);
				  System.out.println("中價="+jPrice_middle);
				  System.out.println("下價="+jPrice_bottom);
				  System.out.println("平均價="+jPrice_average);
				  //System.out.println("市場名稱="+jMarket);
				  System.out.println("交易量="+jtPrice);//原資料有,但資料庫無此項欄位
				  System.out.println("市場代號="+jmNumb);//原資料有,但資料庫無此項欄位
				*/
				
				/* 資料庫的動作 */
				bean.setId(jID);
				bean.setIngre_name(jIngre_name);
				bean.setClassName("蔬菜");
				bean.setUpdate_date(update_Date);
				bean.setPrice_upper(price_Upper);
				bean.setPrice_middle(price_Middle);
				bean.setPrice_bottom(price_Bottom);
				bean.setPrice_average(price_Average);
				bean.setMarket(market);
				pKJdbcDAO.insert(bean);
				jID++;// 新增一筆放一筆
			} else if (jClass.matches("[A-Z]{1}[0-9]{1,3}") || jClass.matches("[0-9]{2,4}")) {
				java.sql.Date update_Date = this.formatDateTime(jUpdate_date);
				BigDecimal price_Upper = this.converBigDecimal(jPrice_upper);
				BigDecimal price_Middle = this.converBigDecimal(jPrice_middle);
				BigDecimal price_Bottom = this.converBigDecimal(jPrice_bottom);
				BigDecimal price_Average = this.converBigDecimal(jPrice_average);

				bean.setId(jID);
				bean.setIngre_name(jIngre_name);
				bean.setClassName("水果");
				bean.setUpdate_date(update_Date);
				bean.setPrice_upper(price_Upper);
				bean.setPrice_middle(price_Middle);
				bean.setPrice_bottom(price_Bottom);
				bean.setPrice_average(price_Average);
				bean.setMarket(market);
				pKJdbcDAO.insert(bean);
				jID++;// 新增一筆放一筆
			}
		}
		System.out.println("更新筆數:" + jID);
		return bean;
	}

	// 字串轉換浮點數
	public java.math.BigDecimal converBigDecimal(Double temp) {
		BigDecimal xx = new java.math.BigDecimal(temp.toString());
		return xx;
	}

	// 民國年與.轉換java.util.date
	public java.sql.Date formatDateTime(String jdate) {
		java.util.Date javaDate = null;
		java.sql.Date sqlDate = null;
		String[] adate = jdate.split("\\.");
		String yy = String.valueOf(Integer.parseInt(adate[0]) + 1911);
		String dateString = yy + "-" + adate[1] + "-" + adate[2];
		// 已將時間轉換成西元字串格式
		// System.out.println("dateString=" + dateString);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			javaDate = sdf.parse(dateString);
			sqlDate = new java.sql.Date(javaDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sqlDate != null) {
			return sqlDate;
		} else {
			return null;
		}
	}
}
