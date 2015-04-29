package br.com.daniloporcelani;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Path {

	@XmlAttribute
	private String action;
	@XmlAttribute
	private String kind;
	@XmlAttribute(name = "prop-mods")
	private String propMods;
	@XmlAttribute(name = "text-mods")
	private String textMods;
	
	@XmlValue
	private String path;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getPropMods() {
		return propMods;
	}

	public void setPropMods(String propMods) {
		this.propMods = propMods;
	}

	public String getTextMods() {
		return textMods;
	}

	public void setTextMods(String textMods) {
		this.textMods = textMods;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		int lastSlash = path.lastIndexOf('/');
		int lastDot = path.lastIndexOf('.');
		if (lastSlash > lastDot)
			return path;
		return path.substring(lastSlash, lastDot);
	}

}