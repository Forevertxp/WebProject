/**
 * 
 */
package cn.edu.bit.common;

import java.sql.Timestamp;
import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class Json {
	public static String Encode(Object obj) {
		if (obj == null || obj.toString().equals("null"))
			return null;
		if (obj != null && obj.getClass() == String.class) {
			return obj.toString();
		}
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"),
				Date.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"),
				Timestamp.class);
		return serializer.deepSerialize(obj);
	}

	public static Object Decode(String json) {
		if (HelperString.isNullOrEmpty(json))
			return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		deserializer.use(String.class, new DateTransformer(
				"yyyy-MM-dd'T'HH:mm:ss"));
		Object obj = deserializer.deserialize(json);
		if (obj != null && obj.getClass() == String.class) {
			return Decode(obj.toString());
		}
		return obj;
	}
}
