package com.popkyss.sweetShop.setting;

import java.util.List;

public interface ICrudDao<Typ, Id> {
	Typ find(Id paramId);

	Id persist(Typ paramTyp);

	void remove(Id paramId);

	Typ merge(Typ paramTyp);

	List<Typ> findAll();

	void refresh(Typ paramTyp);

	void delete(Typ paramTyp);

	boolean isExist(Id paramId);

	boolean isExist(String paramString, Object paramObject);

	boolean isExist(String paramString, Object paramObject, Id paramId);
}
