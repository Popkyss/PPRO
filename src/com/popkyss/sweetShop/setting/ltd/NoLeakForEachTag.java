package com.popkyss.sweetShop.setting.ltd;

import org.apache.taglibs.standard.tag.rt.core.ForEachTag;


/**
 * Jedna se upravu/opravu standardniho tagu c:forEach tk ,a by po vykresleni doslo k uvolneni
 * objektu, ktere tag drzi, z nejakeho duvodu se nevola metoda release, takze se v tagu drzi
 * informace o kolekci, pres kterou se iterovalo, coz muze byt docela hodne dat
 * @author MB
 *
 */
public class NoLeakForEachTag extends ForEachTag {

	private static final long serialVersionUID = 1L;

	
	@Override
	public void doFinally() {
		super.doFinally();
		release();
	}
	

}
