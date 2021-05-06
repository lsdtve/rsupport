package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;
import rsupport.addressbook.util.FileUtils;
import rsupport.addressbook.util.PropertyUtils;

@Service
@RequiredArgsConstructor
public class AddressBookSynchronizeService {

    private final PropertyUtils propertyUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void runSync() {
        dbClearAll();
        dbInit();
    }

    public void dbInit() {
        FileUtils.readCsvFile(propertyUtils.getAddressbookFilePath())
            .map(MemberCreateForm::new)
            .forEach(memberService::save);
    }

    public void dbClearAll() {
        memberRepository.deleteAll();
        teamRepository.deleteAll();
    }
}
