package com.emetaplus.workplace.userworkplace.model;

import com.emetaplus.workplace.board.model.AdminBoard;
import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.session.model.Session;
import com.emetaplus.workplace.user.model.User;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user_workplaces")
public class UserWorkplace {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID workplaceId;

    @OneToOne()
    private User owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<UserDeck> userDecks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<AdminDeck> adminDecks;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<Client> clients;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<User> workers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<SessionBoard> sessionBoards;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<AdminBoard> adminBoards;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id", nullable = false, insertable = false, updatable = false)
    private List<Session> sessions;

}
