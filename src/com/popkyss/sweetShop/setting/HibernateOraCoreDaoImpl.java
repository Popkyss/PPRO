
package com.popkyss.sweetShop.setting;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

public class HibernateOraCoreDaoImpl extends AHibernateTransactionDao implements ICoreDao {
	public Date getCurrentDate() {
		String sql = "select trunc(sysdate) as currentDate from dual";
		SQLQuery q = hs().createSQLQuery(sql);
		q.addScalar("currentDate", (Type) StandardBasicTypes.DATE);
		return (Date) q.uniqueResult();
	}

	public Timestamp getCurrentTimestamp() {
		String sql = "select systimestamp as currentDate from dual";
		SQLQuery q = hs().createSQLQuery(sql);
		q.addScalar("currentDate", (Type) StandardBasicTypes.TIMESTAMP);
		return (Timestamp) q.uniqueResult();
	}
}
