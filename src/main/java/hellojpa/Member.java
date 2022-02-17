package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //JPA 관리 대상으로 인식한다
public class Member {
    @Id //무엇이 PK인지 알려주는 어노테이션
    private long id;
    private String name;

    public Member() {
    }

    public Member(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
