package hibernate.hibernate_cache_level_1.entity.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 联系方式
 * @author Administrator
 *
 */
@Entity
@Table(name="t_contact_way")
public class ContactWay {
	
	private String id;//此处用封装类,而不用基本类型 ,便于判断
	private String portraiture;//传真
	private String neturl;//网址
	private String whchat;//微信
	private String email;
	private String QQ;
	private String otherContactWay;//其他联系方式
	private String detailedAddress;//详细地址
	private String zipCode;//邮编
	private Set<Contacts> contactsSet = new HashSet<Contacts>();//联系人
	
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid") //这个是hibernate的注解/生成32位UUID
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getPortraiture() {
		return portraiture;
	}
	public void setPortraiture(String portraiture) {
		this.portraiture = portraiture;
	}
	
	//@Column(name="comp_prefix");
	public String getNeturl() {
		return neturl;
	}
	public void setNeturl(String neturl) {
		this.neturl = neturl;
	}
	public String getWhchat() {
		return whchat;
	}
	public void setWhchat(String whchat) {
		this.whchat = whchat;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getOtherContactWay() {
		return otherContactWay;
	}
	public void setOtherContactWay(String otherContactWay) {
		this.otherContactWay = otherContactWay;
	}
	public String getDetailedAddress() {
		return detailedAddress;
	}
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)  //oneTomany  默认是延迟加载，manyToOne  默认是立即加装   EAGER表示立刻加载
    @JoinColumn(name="contact_way_id")//在多的一端 加入一个列，来引用 一的一端的id
	public Set<Contacts> getContactsSet() {
		return contactsSet;
	}
	public void setContactsSet(Set<Contacts> contactsSet) {
		this.contactsSet = contactsSet;
	}
	
		
}
