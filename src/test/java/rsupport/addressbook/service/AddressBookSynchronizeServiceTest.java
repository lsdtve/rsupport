package rsupport.addressbook.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;
import rsupport.addressbook.util.FileUtils;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AddressBookSynchronizeServiceTest {

    @Autowired private FileUtils fileUtils;
    @Autowired private AddressBookSynchronizeService addressBookSynchronizeService;
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;

    @Value("${custom.file.addressbook.member.file.path}")
    String memberCsvPath;

    @Test
    public void dbInit() throws Exception {
        //given

        List<MemberCreateForm> memberCreateFormList = fileUtils.readCsvFile(memberCsvPath)
                .map(MemberCreateForm::new)
                .collect(Collectors.toList());

        //when
        List<Member> saveMembers = memberCreateFormList.stream()
                .map(memberService::save)
                .collect(Collectors.toList());

        //then
        for (int i=0 ; i < memberCreateFormList.size() ; i++) {
            MemberCreateForm form = memberCreateFormList.get(i);
            Member saveMember = saveMembers.get(i);
            Assert.assertEquals(form.getName(), saveMember.getOriginalName());
            Assert.assertEquals(form.getGrade(), saveMember.getGrade());
            Assert.assertEquals(form.getNumber(), saveMember.getNumber());
            Assert.assertEquals(form.getPhone(), saveMember.getPhone());
            Assert.assertEquals(form.getPosition(), saveMember.getPosition());
            Assert.assertEquals(form.getTeamName(), saveMember.getTeam().getName());
        }
     }

     @Test
     public void removeAll() throws Exception {
         //given
         Team team = Team.builder().name("웹개발1팀").build();

         memberRepository.save(Member.builder().name("AKim").team(team).build());
         memberRepository.save(Member.builder().name("KimSu").team(team).build());

         //when
         addressBookSynchronizeService.dbClearAll();

         //then
         Assert.assertEquals(0, memberRepository.count());
         Assert.assertEquals(0, teamRepository.count());
      }

}