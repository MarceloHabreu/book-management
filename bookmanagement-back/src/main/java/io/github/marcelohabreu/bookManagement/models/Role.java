package io.github.marcelohabreu.bookManagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;


    @Getter
    public enum Values {
        ADMIN(1L),
        BASIC(2L);

        long roleId;

        Values(long roleId){
            this.roleId = roleId;
        }
    }
}
