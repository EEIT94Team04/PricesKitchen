package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notebook")
public class NotebookBean implements java.io.Serializable{

	private int note_id;
	private String member_id;
	private java.util.Date date;
	private String ingre_name;
	private int quantity;
	private double weight_kilo;
	private double price;
	private double price_each;
	private double price_perkilo;
	private java.util.Date expire_date;
	private String comment;
	
	public NotebookBean(){

	}
	
	@Override
	public String toString() {
		return "NotebookBean ["+ note_id+ ","+ member_id+ ","+ date+ ","+ ingre_name+ ","+ quantity+ "," + weight_kilo + 
				"," + price + ","+ price_each + "," + price_perkilo + "," + expire_date + "," + comment +"]";
	}
	
	@Id
//	@SequenceGenerator(name="genNote_id", allocationSize=1) //1.先用@SequenceGenerator建立一個generator
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="genNote_id")      //2.再用@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	public Integer getNote_id() {
		return note_id;
	}
	
	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}
	
	public String getMember_id() {
		return member_id;
	}
	
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	@Temporal(TemporalType.DATE)
	public java.util.Date getDate() {
		return date;
	}
	
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getIngre_name() {
		return ingre_name;
	}
	public void setIngre_name(String ingre_name) {
		this.ingre_name = ingre_name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getWeight_kilo() {
		return weight_kilo;
	}
	public void setWeight_kilo(Double weight_kilo) {
		this.weight_kilo = weight_kilo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice_each() {
		return price_each;
	}
	public void setPrice_each(Double price_each) {
		this.price_each = price_each;
	}
	public Double getPrice_perkilo() {
		return price_perkilo;
	}
	public void setPrice_perkilo(Double price_perkilo) {
		this.price_perkilo = price_perkilo;
	}
	
	@Temporal(TemporalType.DATE)
	public java.util.Date getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(java.util.Date datetime) {
		this.expire_date = datetime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}//end of NotebookBean
