package rsupport.addressbook.dto;

import java.util.StringTokenizer;
import lombok.Getter;
import lombok.Setter;
import rsupport.addressbook.domain.Position;

@Getter
@Setter
public class MemberCreateForm {
    private String name;
    private String number;
    private String phone;
    private String teamName;
    private String grade;
    private Position position;

    public MemberCreateForm(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        st.nextToken();
        this.name = st.nextToken().trim();
        this.number = st.nextToken().trim();
        this.phone = st.nextToken().trim();
        this.teamName = st.nextToken().trim();
        this.grade = st.nextToken().trim();
        this.position = st.hasMoreTokens() ? Position.getLeaderOrElseMember(st.nextToken().trim()) : Position.MEMBER;
    }
}
