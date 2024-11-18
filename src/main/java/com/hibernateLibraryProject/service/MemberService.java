package com.hibernateLibraryProject.service;

import com.hibernateLibraryProject.dao.MemberDAO;
import com.hibernateLibraryProject.model.Member;
import java.util.List;

public class MemberService {
    private final MemberDAO memberDAO = new MemberDAO();

    public void registerMember(Member member) {
        memberDAO.saveMember(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }
}
