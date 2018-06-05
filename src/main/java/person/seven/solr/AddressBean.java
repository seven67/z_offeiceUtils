package person.seven.solr;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * 地址solr模型
 *
 */
public class AddressBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1794251L;

	/**
	 * 省
	 */
	@Field(value="id")
	private String id;
	
	/**
	 * 地级市，直辖市
	 */
	@Field(value="address")
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "AddressBean{" +
				"id='" + id + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
