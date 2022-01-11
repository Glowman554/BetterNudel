package io.github.glowman554.nudel.api;

import com.kanishka.virustotal.dto.FileScanReport;
import com.kanishka.virustotal.dto.ScanInfo;
import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import com.kanishka.virustotalv2.VirusTotalConfig;
import com.kanishka.virustotalv2.VirustotalPublicV2;
import com.kanishka.virustotalv2.VirustotalPublicV2Impl;
import io.github.glowman554.nudel.Main;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

import java.io.File;
import java.io.IOException;

public class VirusTotalApi extends BaseApi {
    public String key = null;

    public VirusTotalApi()
    {
        if (System.getenv("VIRUSTOTAL_KEY") != null)
        {
            key = System.getenv("VIRUSTOTAL_KEY");

            System.out.printf("[VirusTotalApi] Loaded Virustotal key '%s' from environment variables.\n", key);
        }
        else
        {
            try
            {
                String config = FileUtils.readFile(Main.config_file);

                Json config_json = Json.json();
                JsonNode config_root = config_json.parse(config);

                key = config_root.get("virustotal_key").asString();

                System.out.printf("[VirusTotalApi] Loaded Virustotal key '%s' from config file.\n", key);
            }
            catch (IOException | JsonSyntaxException e)
            {
                e.printStackTrace();
            }
        }

        if (key == null)
        {
            System.out.println("[VirusTotalApi] Could not load Virustotal key. Please set them in the config file or in the environment variables.");
        }
    }


    public SampleResult upload_sample(File sample) throws APIKeyNotFoundException, QuotaExceededException, UnauthorizedAccessException, IOException {
        VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(key);
        VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();
        ScanInfo scanInformation = virusTotalRef.scanFile(sample);

        System.out.printf("___SCAN INFORMATION(%s)___\n", sample.getAbsolutePath());
        System.out.println("MD5 :\t" + scanInformation.getMd5());
        System.out.println("Perma Link :\t" + scanInformation.getPermalink());
        System.out.println("Resource :\t" + scanInformation.getResource());
        System.out.println("SHA1 :\t" + scanInformation.getSha1());
        System.out.println("SHA256 :\t" + scanInformation.getSha256());
        System.out.println("done.");

        return new SampleResult(scanInformation, scanInformation.getPermalink());
    }

    public class SampleResult {
        public final ScanInfo scanInfo;
        public final String permalink;

        public SampleResult(ScanInfo scanInfo, String permalink) {
            this.scanInfo = scanInfo;
            this.permalink = permalink;
        }

        @Override
        public String toString() {
            try {
                VirusTotalConfig.getConfigInstance().setVirusTotalAPIKey(key);
                VirustotalPublicV2 virusTotalRef = new VirustotalPublicV2Impl();

                FileScanReport report = virusTotalRef.getScanReport(scanInfo.getResource());
                while (report.getScanId() == null) {
                    System.out.println(report.getVerboseMessage());
                    Thread.sleep(1000);

                    report = virusTotalRef.getScanReport(scanInfo.getResource());
                }
                return String.format("%d out of %d antivirus scanners flagged this file!", report.getPositives(), report.getTotal());
            } catch (Exception e) {
                return "Error :o";
            }
        }
    }
}
