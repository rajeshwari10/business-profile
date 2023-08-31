package com.example.businessprofile.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "public")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "seq_user_id", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private BigInteger userId;

    @NotEmpty
    @Column(name = "user_name", nullable = false)
    private String userName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<UserProductSubscription> userProductSubscriptions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_profile_id", referencedColumnName = "id")
    @ToString.Exclude
    private BusinessProfile businessProfile;
}
