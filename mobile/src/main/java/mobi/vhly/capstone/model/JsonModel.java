package mobi.vhly.capstone.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: vhly[FR]
 * Date: 16/1/26
 * Email: vhly@163.com
 */
public interface JsonModel {
    void parseJson(JSONObject json) throws JSONException;
}
