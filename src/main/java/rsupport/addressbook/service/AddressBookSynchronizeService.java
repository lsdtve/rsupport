package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.Dto.MemberCreateForm;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;
import rsupport.addressbook.util.FileUtils;

@Service
@RequiredArgsConstructor
public class AddressBookSynchronizeService {

    @Autowired private final FileUtils fileUtils;
    @Autowired private final MemberService memberService;
    @Autowired private final MemberRepository memberRepository;
    @Autowired private final TeamRepository teamRepository;

    @Value("${custom.file.addressbook.member.file.path}")
    String memberCsvPath;

    @Transactional
    public void runSync() {
        dbClearAll();
        dbInit();
    }

    public void dbInit() {
        fileUtils.readCsvFile(memberCsvPath)
                .map(MemberCreateForm::new)
                .forEach(memberService::save);
    }

    public void dbClearAll(){
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }

}
