package com.popkyss.sweetShop.setting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface KriteriumFiltru {
	String cestaDoLng() default "";

	String formatDatum() default "dd.MM.yyyy";

	ExpandList expandovaniSeznamu() default ExpandList.NONE;

	public enum ExpandList {
		NONE {
			public String getOddelovacKriterii() {
				return ", ";
			}
		},

		NEW_LINE {
			public String getOddelovacKriterii() {
				return "\n";
			}
		},

		COLSPAN {
			public String getOddelovacKriterii() {
				return ", ";
			}
		};

		public abstract String getOddelovacKriterii();
	}
}
