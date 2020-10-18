package com.popkyss.sweetShop.setting;

import java.sql.Timestamp;
import java.util.Date;

public interface ICoreDao {
	Date getCurrentDate();

	Timestamp getCurrentTimestamp();
}