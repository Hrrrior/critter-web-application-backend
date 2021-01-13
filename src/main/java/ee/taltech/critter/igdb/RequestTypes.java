package ee.taltech.critter.igdb;

import lombok.Getter;

@Getter
public enum RequestTypes {
    GAMES("{0} fields name,slug,videos.video_id,summary,cover.url,platforms.name,genres.name,involved_companies.developer,involved_companies.company.name, release_dates.date; limit {1};", "https://api.igdb.com/v4/games/"),
    GENRES("{0} fields name; limit {1};", "https://api.igdb.com/v4/genres/"),
    PLATFORMS("{0} fields name; limit {1};", "https://api.igdb.com/v4/platforms/");

    public final String body;
    public final String link;

    RequestTypes(String body, String link) {
        this.body = body;
        this.link = link;
    }
}
