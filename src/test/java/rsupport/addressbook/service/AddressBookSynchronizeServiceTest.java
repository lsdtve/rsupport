package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;
import rsupport.addressbook.util.FileUtils;
import rsupport.addressbook.util.PropertyUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AddressBookSynchronizeServiceTest {

    @Autowired private PropertyUtils propertyUtils;
    @Autowired private AddressBookSynchronizeService addressBookSynchronizeService;
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;

    @Test
    void dbInit() {
        //given
        List<MemberCreateForm> memberCreateFormList = FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath())
                .map(MemberCreateForm::new)
                .collect(Collectors.toList());

        //when
        List<Member> saveMembers = memberCreateFormList.stream()
                .map(form -> {
                    Team team = teamService.findTeamOrElseNewTeam(form.getTeamName());

                    Member member = Member.builder()
                        .originalName(form.getName())
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


        //then
        for (int i=0 ; i < memberCreateFormList.size() ; i++) {
            MemberCreateForm form = memberCreateFormList.get(i);
            Member saveMember = saveMembers.get(i);
            assertEquals(form.getName(), saveMember.getOriginalName());
            assertEquals(form.getGrade(), saveMember.getGrade());
            assertEquals(form.getNumber(), saveMember.getNumber());
            assertEquals(form.getPhone(), saveMember.getPhone());
            assertEquals(form.getPosition(), saveMember.getPosition());
            assertEquals(form.getTeamName(), saveMember.getTeam().getName());
        }
    }

    @Test
    void removeAll() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();

        memberRepository.save(Member.builder().name("AKim").team(team).build());
        memberRepository.save(Member.builder().name("KimSu").team(team).build());

        //when
        addressBookSynchronizeService.dbClearAll();

        //then
        assertEquals(0, memberRepository.count());
        assertEquals(0, teamRepository.count());
    }

}
