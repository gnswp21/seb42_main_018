package com.codestates.mainproject.group018.somojeon.user.entity;

import com.codestates.mainproject.group018.somojeon.audit.Auditable;
import com.codestates.mainproject.group018.somojeon.candidate.entity.Candidate;
import com.codestates.mainproject.group018.somojeon.club.entity.UserClub;
import com.codestates.mainproject.group018.somojeon.comment.entity.Comment;
import com.codestates.mainproject.group018.somojeon.images.entity.Images;
import com.codestates.mainproject.group018.somojeon.join.entity.Joins;
import com.codestates.mainproject.group018.somojeon.oauth.entity.OAuthUser;
import com.codestates.mainproject.group018.somojeon.team.entity.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false)
    String password;

    // 이미지 연관관계 매핑 바꿔봄.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "IMAGE_ID")
    private Images images;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Joins> joinsList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserClub> userClubList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTeam> userTeamList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Candidate> candidateList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus = UserStatus.USER_NEW;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    OAuthUser oAuthUser;

    public void setImages(Images images) {
        this.images = images;
    }

    public enum UserStatus{

        USER_NEW("USER_NEW"),
        USER_ACTIVE("USER_ACTIVE"),
        USER_SLEEP("USER_SLEEP"),
        USER_QUIT("USER_QUIT");

        @Getter
        String status;

        UserStatus(String status) {
            this.status = status;
        }
    }

    public void addUserTeam(UserTeam userTeam) {
        this.userTeamList.add(userTeam);
        if (userTeam.getUser() != this) {
            userTeam.setUser(this);
        }
    }

    public void setUserTeam(UserTeam userTeam) {
        userTeamList.add(userTeam);
    }

}
