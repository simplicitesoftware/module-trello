package com.simplicite.objects.Trello;

import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.Message;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello card business object example
 */
public class TrelloCardExample extends com.simplicite.util.ObjectDB {
	private static final long serialVersionUID = 1L;

	private TrelloTool tt = null;
	private JSONObject settings = null;

	@Override
	public void postLoad() {
		AppLog.info(getClass(), "postLoad", "Instance: " + getInstanceName(), getGrant());
		if (!getInstanceName().startsWith("webhook_")) {
			tt = new TrelloTool(getGrant());
			AppLog.info(getClass(), "postLoad", "Trello tool API key: " + tt.getKey(), getGrant());
			settings = getGrant().getJSONObjectParameter("TRELLO_CARDEX_SETTINGS");
			AppLog.info(getClass(), "postLoad", "Settings: " + settings.toString(2), getGrant());
		}
	}

	@Override
	public String preCreate() {
		if (tt == null) return null;
		try {
			JSONObject card = new JSONObject()
				.put("name", getFieldValue("trelloCardExName"))
				.put("desc", getFieldValue("trelloCardExDescription"));
			card = tt.addCard(settings.getString("defaultListId"), card);
			AppLog.info(getClass(), "preCreate", card.toString(2), getGrant());
			setFieldValue("trelloCardExCardId", card.getString("id"));

			return Message.formatSimpleInfo("Trello card created");
		} catch (APIException e) { // Prevents creation if card creation fails
			AppLog.error(getClass(), "preCreate", null, e, getGrant());
			return Message.formatSimpleError("Card creation error: " + e.getMessage());
		}
	}

	@Override
	public String preUpdate() {
		if (tt == null) return null;
		try {
			String id = getFieldValue("trelloCardExCardId");
			JSONObject card = tt.getCard(id);

			card.put("name", getFieldValue("trelloCardExName"));
			card.put("desc", getFieldValue("trelloCardExDescription"));
			tt.updateCard(id, card);
			AppLog.info(getClass(), "preUpdate", card.toString(2), getGrant());

			return Message.formatSimpleInfo("Trello card updated");
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postUpdate", null, e, getGrant());
			return Message.formatSimpleError("Card update error: " + e.getMessage());
		}
	}

	@Override
	public String preDelete() {
		if (tt == null) return null;
		try {
			tt.deleteCard(getFieldValue("trelloCardExCardId"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
}
