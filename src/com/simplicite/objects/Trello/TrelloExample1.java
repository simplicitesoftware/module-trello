package com.simplicite.objects.Trello;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.Message;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello business object example 1
 */
public class TrelloExample1 extends com.simplicite.util.ObjectDB {
	private static final long serialVersionUID = 1L;

	private TrelloTool tt = null;
	private JSONObject settings = null;;
	
	@Override
	public void postLoad() {
		tt = new TrelloTool(getGrant());
		settings = getGrant().getJSONObjectParameter("TRELLO_EX1_SETTINGS");
	}

	@Override
	public String preCreate() {
		JSONObject card = null;
		try {
			card = tt.addCard(settings.getString("defaultListId"), getFieldValue("trelloEx1Name"), getFieldValue("trelloEx1Description"), null);
			AppLog.info(getClass(), "preCreate", card.toString(2), getGrant());
			setFieldValue("trelloEx1CardId", card.getString("id"));
			return Message.formatSimpleInfo("Trello card created");
		} catch (APIException e) { // Prevents creation if card creation fails
			AppLog.error(getClass(), "preCreate", null, e, getGrant());
			return Message.formatSimpleError("Card creation error: " + e.getMessage());
		}
	}
	
	@Override
	public String preUpdate() {
		try {
			String id = getFieldValue("trelloEx1CardId"); 
			JSONObject card = tt.getCard(id, null);
			card.put("name", getFieldValue("trelloEx1Name"));
			card.put("desc", getFieldValue("trelloEx1Description"));
			tt.updateCard(id, card);
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}

	@Override
	public String preDelete() {
		try {
			tt.deleteCard(getFieldValue("trelloEx1CardId"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
}
