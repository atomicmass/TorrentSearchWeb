package za.co.blacklemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.blacklemon.rest.TvService;
import za.co.blacklemon.tv.api.ApiEpisode;
import za.co.blacklemon.tv.api.ApiSeries;
import za.co.blacklemon.tv.api.ApiTorrent;
import za.co.blacklemon.tv.dao.Episode;
import za.co.blacklemon.tv.dao.Series;
import za.co.blacklemon.tv.database.SeriesDb;

public class SeriesDownloader {

    private static SeriesDownloader _instance;

    private SeriesDownloader() {
    }

    public static SeriesDownloader getInstance() {
        if (_instance == null) {
            _instance = new SeriesDownloader();
        }

        return _instance;
    }

    public List<Series> downloadSeries() {
        List<Series> series = SeriesDb.getInstance().getAllSeries();

        for (Series s : series) {
            ApiSeries api = TvService.getInstance().getSeries(s.getId());
            api.getEp().forEach(e -> {
                s.addEpisode(e.getId(), e.getSeasonAsInt(), e.getEpAsInt());
            });

            for (Episode e : s.getUnQueued()) {
                ApiEpisode ep = api.getEpisode(e.getSeason(), e.getEpisode());
                ApiTorrent t = ep.getBestQuality();
                if(t == null)
                    continue;
                String cmd = String.format("transmission-remote -n media:media -w /Data/media/Series -a %s", t.getValue());
                System.out.println(cmd);
                
                try {
                    Process p = Runtime.getRuntime().exec(cmd);
                    p.waitFor();

                    BufferedReader reader
                            = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    System.out.println(sb.toString());

                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(SeriesDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }

                e.setDone("Y");
            }

            SeriesDb.getInstance().update(s);
        }

        return series;
    }
}
