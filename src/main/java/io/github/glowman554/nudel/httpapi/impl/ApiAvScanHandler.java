package io.github.glowman554.nudel.httpapi.impl;

import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.api.VirusTotalApi;
import io.github.glowman554.nudel.httpapi.HttpApiHandler;
import io.github.glowman554.nudel.utils.FileUtils;
import io.github.glowman554.nudel.utils.TokenUtils;

import java.io.File;
import java.util.Map;

public class ApiAvScanHandler implements HttpApiHandler {
    @Override
    public String execute(Map<String, String> query) throws Exception {
        String token = query.get("login_token");
        String user = TokenUtils.checkToken(token, Main.authManager);

        String url = query.get("url");
        if (url == null) {
            return "Please set url";
        }

        String tmp_file = FileUtils.randomTmpFile(FileUtils.getFileExtension(url));

        new BaseApi().download(tmp_file, url);

        VirusTotalApi.SampleResult result = new VirusTotalApi().upload_sample(new File(tmp_file));

        return result.toString();
    }
}
