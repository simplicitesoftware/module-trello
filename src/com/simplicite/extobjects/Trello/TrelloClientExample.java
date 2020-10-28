package com.simplicite.extobjects.Trello;

import org.json.JSONArray;
import org.json.JSONObject;

import com.simplicite.util.exceptions.APIException;
import com.simplicite.util.tools.Parameters;
import com.simplicite.util.tools.TrelloTool;

/**
 * Trello client example
 */
public class TrelloClientExample extends com.simplicite.webapp.web.ResponsiveExternalObject {
	private static final long serialVersionUID = 1L;

	public static final String BOARD_ID = "SFldG59G";

	@Override
	public String getRenderStatement(Parameters params) {
		return "void(0)";
	}

	@Override
	public String content(Parameters params) {
		try {
			StringBuilder html = new StringBuilder();

			TrelloTool tt = new TrelloTool(getGrant());

			JSONObject b = tt.getBoard(BOARD_ID, null);
			html.append("<h1> " + b.getString("name") + " / " + b.getString("shortUrl") + " (" + b.getString("id") + ")</h1>");

			JSONArray ls = tt.getBoardLists(BOARD_ID, null);
			html.append("<ul>");
			for (int i = 0; i < ls.length(); i++)
			{
				JSONObject l = ls.getJSONObject(i);
				html.append("<li>");
				html.append("<h3>" + l.getString("name") + " (" + l.getString("id") + ")</h3>");

				JSONArray cs = tt.getListCards(l.getString("id"), null);
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

			return html.toString();
		} catch (APIException e) {
			return "<div>Error: " + e.getMessage() + "</div>";
		}
	}
}