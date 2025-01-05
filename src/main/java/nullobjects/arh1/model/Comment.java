package nullobjects.arh1.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private String content;

    public Comment() {

    }

    public Comment(User user, String content) {
        this.user = user;
        this.content = content;
    }
}