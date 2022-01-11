import com.kanishka.virustotal.exception.APIKeyNotFoundException;
import com.kanishka.virustotal.exception.QuotaExceededException;
import com.kanishka.virustotal.exception.UnauthorizedAccessException;
import io.github.glowman554.nudel.api.BaseApi;
import io.github.glowman554.nudel.api.VirusTotalApi;
import io.github.glowman554.nudel.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestVirusTotalApi {
    public boolean allow_virus_download = !System.getProperty("os.name").toLowerCase().contains("windows");
    public String wanacry_download = "https://github.com/asdasdasdasdsadsa/wanacry/raw/master/WannaCry.EXE";
    @Test
    public void testVirusTotalApi() throws QuotaExceededException, APIKeyNotFoundException, UnauthorizedAccessException, IOException {
        VirusTotalApi api = new VirusTotalApi();

        if (api.key == null) {
            System.out.println("API key not set skipping...");
            return;
        }

        if (allow_virus_download) {
            String tmp_path = FileUtils.randomTmpFile("exe");
            System.out.println("Downloading wannacry to: " + tmp_path);
            new BaseApi().download(tmp_path, wanacry_download);

            VirusTotalApi.SampleResult result = api.upload_sample(new File(tmp_path));
            System.out.println(result.permalink);
            System.out.println(result.toString());

            new File(tmp_path).delete();
        }
    }
}
