package gq.glowman554.bot.wiki.api;

import java.io.File;
import java.net.URLDecoder;
import java.util.Map;

import gq.glowman554.bot.http.server.HttpApi;
import gq.glowman554.bot.http.server.HttpApiHandler;
import gq.glowman554.bot.http.server.filehost.FileHostObject;
import gq.glowman554.bot.utils.FileUtils;
import gq.glowman554.bot.wiki.PageManager;
import net.shadew.json.JsonNode;

public class PageGetHandler extends HttpApiHandler {

	public PageGetHandler(HttpApi api, String path) {
		super(api, path);
	}

	@Override
	public String execute(Map<String, String> query, Map<String, String> headers) throws Exception {
		String page_id = query.get("page_id");
		if (page_id == null) {
			return "{\"error\":\"Missing page_id\"}";
		}

		JsonNode _return = PageManager.instance.load(page_id).toJson();

		if (query.keySet().contains("download")) {
			String file = FileUtils.randomTmpFile("md");
			FileUtils.writeFile(file, new URLDecoder().decode(_return.get("page_text").asString()));
			
			FileHostObject fho = FileHostObject.new_object(new File(file), "<system>", true);

			_return.set("file_id", fho.getFile_id());
		}

		return _return.toString();
	}
	
}
