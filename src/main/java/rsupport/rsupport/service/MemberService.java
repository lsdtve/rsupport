package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberCreateForm form){
        Member member = new Member(
                form.getId(),
                DuplicateName(form.getName()),
                form.getNumber(),
                form.getPhone(),
                form.getTeam(),
                form.getSpot(),
                form.getPosition());
        memberRepository.save(member);
        return null;
    }

    /**
     * 동명이인인 경우 이름 + (B, C, D)로 표기
     */
    public String DuplicateName(String name){
        int cnt = 65;
        String result = name;
        while(memberRepository.existsByName(result)){
            result = name + (cnt==65 ? "" : "("+(char) cnt+")");
            cnt += 1;
        }
        return result;
    }

    public List<MemberDto> findAll() {
        List<MemberDto> result = new ArrayList<>();
        memberRepository.findAll().forEach(member -> {
            MemberDto memberDto = new MemberDto(member);
            result.add(memberDto);
        });
        return result;
    }
}
