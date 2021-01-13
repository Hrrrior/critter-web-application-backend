package ee.taltech.critter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Game {
    @Id
    private Long id;
    private String name;
    private String slug;
    @Lob
    @Column
    private String description;
    private String imgUrl;
    private String videoUrl;
    private String developer;
    private Long releaseDate;
    private int rating;
    @ElementCollection
    private Set<String> genres;
    @ElementCollection
    private Set<String> platforms;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<Review> reviews;

    public void updateRating() {
        rating = (int) (10 * reviews.stream()
                .mapToInt(Review::getRating)
                .average().orElse(0));
    }
}
