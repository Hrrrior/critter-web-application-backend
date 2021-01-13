package ee.taltech.critter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Review {
    @Id
    @GeneratedValue()
    private Long id;
    @Lob
    @Column
    private String reviewText;
    private String author;
    private int rating;
    @Builder.Default
    private Long reviewDate = System.currentTimeMillis();

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Game game;
}
