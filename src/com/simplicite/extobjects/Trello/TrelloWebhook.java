package com.simplicite.extobjects.Trello;

import java.util.List;

import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.tools.BusinessObjectTool;
import com.simplicite.util.Grant;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.Tool;
import com.simplicite.util.tools.Parameters;
import com.simplicite.util.exceptions.HTTPException;

/**
 * Trello webhook
 */
public class TrelloWebhook extends com.simplicite.webapp.services.RESTServiceExternalObject {
	private static final long serialVersionUID = 1L;
	
	private static final JSONObject OK = new JSONObject().put("result", "ok");
	
    @Override
    public Object head(Parameters params) throws HTTPException {
        return OK;
    }

    @Override
    public Object get(Parameters params) throws HTTPException {
        return error(400, "Call me in POST please!");
    }

	private void updateCard(JSONObject card) {
		try {
			AppLog.info(getClass(), "updateCard", card.toString(2), getGrant());
			String o = "TrelloCardExample";
			ObjectDB obj = Grant.getSystemAdmin().getObject("webhook_" + o, o);
			BusinessObjectTool objt = new BusinessObjectTool(obj);
			obj.resetFilters();
			obj.getField("trelloCardExCardID").setFilter(card.getString("id"));
			List<String[]> rows = objt.search();
			if (rows.size() == 1) {
				obj.setValues(rows.get(0), true);
				if (card.has("name"))
					obj.setFieldValue("trelloCardExName", card.getString("name"));
				if (card.has("desc"))
					obj.setFieldValue("trelloCardExDescription", card.getString("desc"));
				objt.validateAndSave();
			}
		} catch (Exception e) {
			AppLog.error(getClass(), "updateCard", null, e, getGrant());
		}
	}

    @Override
    public Object post(Parameters params) throws HTTPException {
        try {
            JSONObject req = params.getJSONObject();
            if (req != null) {
            	AppLog.info(getClass(), "post", req.toString(2), getGrant());
            	if (req.has("action")) {
            		JSONObject action = req.getJSONObject("action");
            		String type = action.optString("type");
            		if ("updateCard".equals(type)) {
            			if (action.has("data")) {
            				JSONObject data = action.getJSONObject("data");
            				if (data.has("card")) {
            					updateCard(data.getJSONObject("card"));
            				}
            			}
            		}
            	}
                return OK;
            } else {
                return error(400, "Call me with a JSON body please!");
            }
        } catch (Exception e) {
            return error(e);
        }
    }
}
