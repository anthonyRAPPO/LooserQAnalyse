package apony.lol.LooserQAnalyse.service.interfaces;

import org.apache.http.client.HttpClient;

import apony.lol.LooserQAnalyse.exception.NotResultException;

public interface IRequestService {
    HttpClient createHttpClient();

    StringBuilder createRequestUri(String baseUrl, String summonerEndmpoint);

    String sendGetRequest(String req, HttpClient httpClient) throws NotResultException;
}