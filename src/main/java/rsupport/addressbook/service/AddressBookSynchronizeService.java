package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.util.FileUtils;
import rsupport.addressbook.util.PropertyUtils;

@Service
@RequiredArgsConstructor
public class AddressBookSynchronizeService {

    private final PropertyUtils propertyUtils;
    private final TeamService teamService;
    private final MemberService memberService;

    @Transactional
    public void runSync() {
        dbClearAll();
        dbInit();
    }

    public void dbInit() {
        FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath())
            .map(MemberCreateForm::new)
            .forEach(form -> {
                Team team = teamService.findTeamOrElseSave(form.getTeamName());

                Member member = Member.builder()
                    .name(memberService.duplicateName(form.getName()))
                    .number(form.getNumber())
                    .phone(form.getPhone())
                    .team(team)
                    .grade(form.getGrade())
                    .position(form.getPosition())
                    .build();

                memberService.save(member);
            });
    }

    public void dbClearAll() {
        memberService.deleteAll();
        teamService.deleteAll();
    }
}
