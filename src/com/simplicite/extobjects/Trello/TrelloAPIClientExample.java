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

	public static final String BOARD_ID = "SFldG59G";

	@Override
	public Object display(Parameters params) {
		try {
			StringBuilder html = new StringBuilder();

			TrelloTool tt = new TrelloTool(getGrant());
			JSONObject b = tt.getBoard(BOARD_ID, null);
			//html.append("<!--\n" + b.toString(2) + "\n -->");

			JSONArray ls = tt.getBoardLists(BOARD_ID, null);
			//html.append("<!--\n" + ls.toString(2) + "\n -->");

			html.append("<h1> xxxx " + b.getString("name") + " / " + b.getString("shortUrl") + " (" + b.getString("id") + ")</h1>");
			html.append("<ul>");
			for (int i = 0; i < ls.length(); i++)
			{
				JSONObject l = ls.getJSONObject(i);
				html.append("<li>");
				html.append("<h3>" + l.getString("name") + " (" + l.getString("id") + ")</h3>");
				
				JSONArray cs = tt.getListCards(l.getString("id"), null);
				//html.append("<!--\n" + cs.toString(2) + "\n -->");

				html.append("<ul>");
				for (int j = 0; j < cs.length(); j++)
				{
					JSONObject c = cs.getJSONObject(j);
					html.append("<li>" + c.getString("name") + " (" + c.getString("id") + ")");
					html.append("<pre>" + c.getString("desc") + "</pre>");
					html.append("</li>");
				}
				html.append("</ul>");
				
				html.append("</li>");
			}
			html.append("</ul>");

			setHTML(html.toString());
		} catch (APIException e) {
			setHTML("<div>ERROR: " + e.getMessage() + "</div>");
		}
		return javascript("void(0);");
	}
}
