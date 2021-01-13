package ee.taltech.critter.igdb;

import ee.taltech.critter.model.Game;
import ee.taltech.critter.model.Genre;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IgdbJsonParse {
    private final String youtubeLink = "https://www.youtube.com/watch?v=";
    private final String placeholderImageLink = "https://i.stack.imgur.com/yZlqh.png";

    public Game jsonToGame(JSONObject gameJson) {
        return Game.builder()
                .id(gameJson.getLong("id"))
                .name(gameJson.getString("name"))
                .slug(gameJson.has("slug") ? gameJson.getString("slug") : null)
                .imgUrl(gameJson.has("cover")
                        ? gameJson
                        .getJSONObject("cover")
                        .getString("url")
                        .replace("t_thumb", "t_cover_big")
                        .replace("//images", "https://images")
                        : placeholderImageLink)
                .videoUrl(gameJson.has("videos")
                        ? youtubeLink + gameJson
                        .getJSONArray("videos")
                        .getJSONObject(0)
                        .getString("video_id")
                        : null)
                .description(gameJson.has("summary")
                        ? gameJson.getString("summary")
                        : null)
                .developer(gameJson.has("involved_companies")
                        ? arrayParseDeveloper(gameJson.getJSONArray("involved_companies"))
                        : null)
                .genres(gameJson.has("genres")
                        ? arrayParse(gameJson.getJSONArray("genres"))
                        : null)
                .platforms(gameJson.has("platforms")
                        ? arrayParse(gameJson.getJSONArray("platforms"))
                        : null)
                .releaseDate(gameJson.has("release_dates")
                        ? arrayParseReleaseDate(gameJson.getJSONArray("release_dates"))
                        : null)
                .build();
    }

    public Set<String> arrayParse(JSONArray jsonArray) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            set.add(jsonArray.getJSONObject(i).getString("name"));
        }
        return set;
    }

    public Long arrayParseReleaseDate(JSONArray jsonArray) {
        List<Long> list = new ArrayList<>();
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has("date")) {
                list.add(jsonObject.getLong("date"));
            }
        }
        Long min = list.stream().min(Comparator.comparing(Long::longValue)).orElse(null);
        return min != null ? min * 1000 : null;
    }

    public String arrayParseDeveloper(JSONArray jsonArray) {
        JSONObject jsonObject;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.has("developer")) {
                return jsonObject.getJSONObject("company").getString("name");
            }
        }
        return null;
    }

    public Genre jsonToGenre(JSONObject genreJson) {
        return Genre.builder()
                .id(genreJson.getLong("id"))
                .name(genreJson.getString("name"))
                .build();
    }
}
