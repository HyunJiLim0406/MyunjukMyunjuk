package jpa.myunjuk.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MemberTest {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
}
