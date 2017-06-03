package Test;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class WPbean {
	private Integer id;
	private String ingre_name;
	private String className;
	private java.sql.Date update_date;
	private BigDecimal price_upper;
	private BigDecimal price_middle;
	private BigDecimal price_bottom;
	private BigDecimal price_average;
	private String market;
	private java.sql.Blob photo;
	private Integer click_rate;

	@Override
	public String toString() {
		return "WPbean [id=" + id + ", ingre_name=" + ingre_name + ", className=" + className + ", update_date="
				+ update_date + ", price_upper=" + price_upper + ", price_middle=" + price_middle + ", price_bottom="
				+ price_bottom + ", price_average=" + price_average + ", market=" + market + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIngre_name() {
		return ingre_name;
	}

	public void setIngre_name(String ingre_name) {
		this.ingre_name = ingre_name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public java.sql.Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(java.sql.Date update_date) {
		this.update_date = update_date;
	}

	public BigDecimal getPrice_upper() {
		return price_upper;
	}

	public void setPrice_upper(BigDecimal price_upper) {
		this.price_upper = price_upper;
	}

	public BigDecimal getPrice_middle() {
		return price_middle;
	}

	public void setPrice_middle(BigDecimal price_middle) {
		this.price_middle = price_middle;
	}

	public BigDecimal getPrice_bottom() {
		return price_bottom;
	}

	public void setPrice_bottom(BigDecimal price_bottom) {
		this.price_bottom = price_bottom;
	}

	public BigDecimal getPrice_average() {
		return price_average;
	}

	public void setPrice_average(BigDecimal price_average) {
		this.price_average = price_average;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public java.sql.Blob getPhoto() {
		return photo;
	}

	public void setPhoto(java.sql.Blob photo) {
		this.photo = photo;
	}

	public Integer getClick_rate() {
		return click_rate;
	}

	public void setClick_rate(Integer click_rate) {
		this.click_rate = click_rate;
	}

}
