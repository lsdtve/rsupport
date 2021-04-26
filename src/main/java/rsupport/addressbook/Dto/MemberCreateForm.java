
package rsupport.addressbook.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.StringTokenizer;

@Getter
@Setter
public class MemberCreateForm {
    private Long id;
    private String name;
    private String number;
    private String phone;
    private String teamName;
    private String grade;
    private String position;

    @Builder
    public MemberCreateForm(String name, String number, String phone, String teamName, String grade, String position) {
        this.name = name;
        this.number = number;
        this.phone = phone;
        this.teamName = teamName;
        this.grade = grade;
        this.position = position;
    }

    public MemberCreateForm(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        String id = st.nextToken();
        this.name = st.nextToken().trim();
        this.number = st.nextToken().trim();
        this.phone = st.nextToken().trim();
        this.teamName = st.nextToken().trim();
        this.grade = st.nextToken().trim();
        this.position = st.hasMoreTokens() ? st.nextToken().trim() : "팀원";
    }
}