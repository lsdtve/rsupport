package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Position;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.util.FileUtils;
import rsupport.addressbook.util.PropertyUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AddressBookSynchronizeServiceTest {

    @Autowired private PropertyUtils propertyUtils;
    @Autowired private AddressBookSynchronizeService addressBookSynchronizeService;
    @Autowired private MemberService memberService;
    @Autowired private TeamService teamService;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("DB Init")
    void dbInit() {
        //given
        List<MemberCreateForm> memberCreateFormList = FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath())
                .map(MemberCreateForm::new)
                .collect(Collectors.toList());

        //when
        List<Member> saveMembers = memberCreateFormList.stream()
            .map(form -> {
                Team team = teamService.findTeamOrElseSave(form.getTeamName());

                Member member = Member.builder()
                    .name(memberService.duplicateName(form.getName()))
                    .number(form.getNumber())
                    .phone(form.getPhone())
                    .team(team)
                    .grade(form.getGrade())
                    .position(form.getPosition())
                    .build();

                return memberService.save(member);
            })
            .collect(Collectors.toList());

        em.flush();
        em.clear();

        //then
        List<Member> findMembers = memberService.findAll();

        Assertions.assertThat(saveMembers)
            .hasSize(findMembers.size())
            .hasSameElementsAs(findMembers);
    }

    @Test
    @DisplayName("DB Remove")
    void removeAll() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();

        memberService.save(Member.builder().name("AKim").team(team).position(Position.MEMBER).build());
        memberService.save(Member.builder().name("KimSu").team(team).position(Position.MEMBER).build());

        //when
        addressBookSynchronizeService.dbClearAll();

        //then
        assertAll(
            () -> assertEquals(0, memberService.count()),
            () -> assertEquals(0, teamService.count())
        );
    }

}
