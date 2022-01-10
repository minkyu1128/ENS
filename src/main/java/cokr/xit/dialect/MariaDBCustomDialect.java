package cokr.xit.dialect;

import org.hibernate.dialect.MariaDB103Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MariaDBCustomDialect extends MariaDB103Dialect {

	public MariaDBCustomDialect() {
		super();

		registerFunction("ECL_DECRYPT",
				new StandardSQLFunction("ECL_DECRYPT", StandardBasicTypes.STRING)
				);
		registerFunction("ECL_ENCRYPT",
				new StandardSQLFunction("ECL_ENCRYPT", StandardBasicTypes.STRING)
				);
	}
}
