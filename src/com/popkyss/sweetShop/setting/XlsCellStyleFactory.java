package com.popkyss.sweetShop.setting;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

public class XlsCellStyleFactory {
	private Workbook sesit;
	private Map<Object, Map<Object, Object>> numericStyles = new HashMap<Object, Map<Object,Object>>();
	private Map<Object, Map<Object, Object>> dateStyles = new HashMap<Object, Map<Object,Object>>();
	private CellStyle bigDecimalHlavickaStyle;
	private CellStyle horizontalVerticalAlignCenter;
	private Map<Short, CellStyle> nadpisy = new HashMap<Short, CellStyle>();
	private Map<Short, CellStyle> nadpisyWrap = new HashMap<Short, CellStyle>();
	private Map<Short, CellStyle> texty = new HashMap<Short, CellStyle>();
	private CellStyle cislo;
	private Map<String, CellStyle> userStyles = new HashMap<String, CellStyle>();

	public XlsCellStyleFactory(Workbook sesit) {
		this.sesit = sesit;
	}

	public CellStyle getDateStyle(short zarovnani, String maska) {
		if (DatexCollections.getSafeFromLut(this.dateStyles,
				new Object[] { Short.valueOf(zarovnani), maska }) == null) {
			CellStyle dateStyle = this.sesit.createCellStyle();
			dateStyle.cloneStyleFrom(getTextStyle(zarovnani));
			DataFormat format = this.sesit.createDataFormat();
			dateStyle.setDataFormat(format.getFormat(maska));
			DatexCollections.putSafeIntoLut(this.dateStyles, dateStyle,
					new Object[] { Short.valueOf(zarovnani), maska });
		}
		return (CellStyle) DatexCollections.getSafeFromLut(this.dateStyles,
				new Object[] { Short.valueOf(zarovnani), maska });
	}

	public CellStyle getNumericStyle(short zarovnani, String maska) {
		if (DatexCollections.getSafeFromLut(this.numericStyles,
				new Object[] { Short.valueOf(zarovnani), maska }) == null) {
			CellStyle numericStyle = this.sesit.createCellStyle();
			numericStyle.cloneStyleFrom(getTextStyle(zarovnani));
			DataFormat format = this.sesit.createDataFormat();
			numericStyle.setDataFormat(format.getFormat(maska));

			DatexCollections.putSafeIntoLut(this.numericStyles, numericStyle,
					new Object[] { Short.valueOf(zarovnani), maska });
		}
		return (CellStyle) DatexCollections.getSafeFromLut(this.numericStyles,
				new Object[] { Short.valueOf(zarovnani), maska });
	}

	public CellStyle getCellAlignNumericStyle(String maska) {
		CellStyle styl = this.sesit.createCellStyle();
		DataFormat format = this.sesit.createDataFormat();
		styl.setAlignment((short) 3);
		styl.setDataFormat(format.getFormat(maska));
		return styl;
	}

	public CellStyle getCellDDMMYYYYStyle(String maska) {
		CellStyle styl = this.sesit.createCellStyle();
		DataFormat format = this.sesit.createDataFormat();
		styl.setAlignment((short) 1);
		styl.setDataFormat(format.getFormat(maska));
		return styl;
	}

	public CellStyle getBigDecimalHlavickaStyle() {
		if (this.bigDecimalHlavickaStyle == null) {
			this.bigDecimalHlavickaStyle = getCellAlignNumericStyle("#0.0000");
			this.bigDecimalHlavickaStyle.setVerticalAlignment((short) 1);
			this.bigDecimalHlavickaStyle.setAlignment((short) 2);
		}

		return this.bigDecimalHlavickaStyle;
	}

	public CellStyle getHorizontalVerticalAlignCenter() {
		if (this.horizontalVerticalAlignCenter == null) {
			this.horizontalVerticalAlignCenter = this.sesit.createCellStyle();
			this.horizontalVerticalAlignCenter.setVerticalAlignment((short) 1);
			this.horizontalVerticalAlignCenter.setAlignment((short) 2);
		}

		return this.horizontalVerticalAlignCenter;
	}

	public CellStyle getNadpis(short zarovnani) {
		if (this.nadpisy.get(Short.valueOf(zarovnani)) == null) {
			CellStyle nadpis = this.sesit.createCellStyle();
			nadpis.setVerticalAlignment((short) 1);
			nadpis.setAlignment(zarovnani);
			nadpis.setFont(XlsUtils.getBoldFont(this.sesit, 10, "Arial"));
			this.nadpisy.put(Short.valueOf(zarovnani), nadpis);
		}
		return this.nadpisy.get(Short.valueOf(zarovnani));
	}

	public CellStyle getNadpisWrap(short zarovnani) {
		if (this.nadpisyWrap.get(Short.valueOf(zarovnani)) == null) {
			CellStyle nadpisWrap = this.sesit.createCellStyle();
			nadpisWrap.cloneStyleFrom(getNadpis(zarovnani));
			nadpisWrap.setWrapText(true);
			this.nadpisyWrap.put(Short.valueOf(zarovnani), nadpisWrap);
		}
		return this.nadpisyWrap.get(Short.valueOf(zarovnani));
	}

	public CellStyle getTextStyle(short zarovnani) {
		if (this.texty.get(Short.valueOf(zarovnani)) == null) {
			CellStyle text = this.sesit.createCellStyle();
			text.setAlignment(zarovnani);
			this.texty.put(Short.valueOf(zarovnani), text);
		}
		return this.texty.get(Short.valueOf(zarovnani));
	}

	public CellStyle getCisloStyle(Workbook sesit) {
		if (this.cislo == null) {
			this.cislo = sesit.createCellStyle();
			this.cislo.setAlignment((short) 3);
		}
		return this.cislo;
	}

	public CellStyle getUserStyle(String styleName) {
		return this.userStyles.get(styleName);
	}

	public CellStyle registerNewUserStyle(String styleName) {
		CellStyle style = this.sesit.createCellStyle();
		this.userStyles.put(styleName, style);
		return style;
	}

	public void registerUserStyle(String styleName, CellStyle style) {
		this.userStyles.put(styleName, style);
	}
}