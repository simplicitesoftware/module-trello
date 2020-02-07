package com.simplicite.objects.Trello;

import org.json.JSONObject;

import com.simplicite.util.AppLog;
import com.simplicite.util.Globals;
import com.simplicite.util.Message;
import com.simplicite.util.Tool;
import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.HTMLTool;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello card business object example
 */
public class TrelloCardExample extends com.simplicite.util.ObjectDB {
	private static final long serialVersionUID = 1L;

	private transient TrelloTool tt = null;
	private void initTrelloTool() {
		if (tt == null)
			tt = new TrelloTool(getGrant());
	}
	
	private boolean isWebhookInstance() {
		return getInstanceName().startsWith("webhook_");
	}

	@Override
	public void postLoad() {
		AppLog.info(getClass(), "postLoad", "Instance: " + getInstanceName(), getGrant());
		if (!isWebhookInstance()) { // Don't register webhook form the instances used on the webhhok
			try {
				initTrelloTool();
				String contextURL = getGrant().getContextURL();
				if (!Tool.isEmpty(contextURL)) {
					String webhookURL = contextURL + HTMLTool.getPublicExternalObjectURL("TrelloWebhook");
					String webhookId = tt.registerWebhook(webhookURL, "Webhook for " + Globals.getPlatformName());
					AppLog.info(getClass(), "postLoad", "Registered webhook: " + webhookId, getGrant());
				}
			} catch (APIException e) {
				AppLog.error(getClass(), "postLoad", "Unable to register the webhook", e, getGrant());
			}
		}
	}

	@Override
	public String preCreate() {
		if (isWebhookInstance()) return null; // Avoids infinite looping
		try {
			initTrelloTool();
			JSONObject card = new JSONObject()
				.put("name", getFieldValue("trelloCardExName"))
				.put("desc", getFieldValue("trelloCardExDescription"));
			card = tt.addCard(card);
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
		if (isWebhookInstance()) return null; // Avoids infinite looping
		try {
			initTrelloTool();
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
		if (isWebhookInstance()) return null; // Avoids infinite looping
		try {
			initTrelloTool();
			tt.deleteCard(getFieldValue("trelloCardExCardId"));
			return null;
		} catch (APIException e) { // Prevents deletion if card creation fails
			AppLog.error(getClass(), "postLoad", null, e, getGrant());
			return null;//Message.formatSimpleError("Card deletion error: " + e.getMessage());
		}
	}
}
