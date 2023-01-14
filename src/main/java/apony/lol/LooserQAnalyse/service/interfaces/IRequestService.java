package apony.lol.LooserQAnalyse.service.interfaces;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import apony.lol.LooserQAnalyse.model.enumeration.Regions;

public interface IRequestService {
    HttpClient createHttpClient();

    StringBuilder createRequestUri(Regions region, String summonerEndmpoint);

    JSONObject sendGetRequest(String req, HttpClient httpClient);
}