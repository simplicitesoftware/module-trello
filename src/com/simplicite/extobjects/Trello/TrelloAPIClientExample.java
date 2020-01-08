package com.simplicite.extobjects.Trello;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.Parameters;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello API example
 */
public class TrelloAPIClientExample extends com.simplicite.util.ExternalObject {
	private static final long serialVersionUID = 1L;

	public static final String BOARD_ID = "5612e4f91b25c15e873722b8";

	@Override
	public Object display(Parameters params) {
		try {
			StringBuilder html = new StringBuilder();

			TrelloTool tt = new TrelloTool(getGrant());
			JSONObject b = tt.getBoard(BOARD_ID, null);
			//html.append("<pre style=\"height: 300px;\">" + b.toString(2) + "</pre>");

			JSONArray ls = tt.getBoardLists(BOARD_ID, null);
			//html.append("<pre style=\"height: 300px;\">" + ls.toString(2) + "</pre>");

			html.append("<div>BOARD ID: " + b.getString("id") + "</div>");
			html.append("<div>BOARD NAME: " + b.getString("name") + "</div>");
			html.append("<div>BOARD SHORT URL: " + b.getString("shortUrl") + "</div>");
			html.append("<div>BOARD LISTS: </div><ul>");
			for (int i = 0; i < ls.length(); i++)
			{
				JSONObject l = ls.getJSONObject(i);
				html.append("<li>LIST ID: " + l.getString("id") + "</li>");
				html.append("<li>LIST NAME: " + l.getString("name") + "</li>");
			}
			html.append("</ul>");

			setHTML(html.toString());
		} catch (APIException e) {
			setHTML("<div>ERROR: " + e.getMessage() + "</div>");
		}
		return javascript("void(0);");
	}
}
