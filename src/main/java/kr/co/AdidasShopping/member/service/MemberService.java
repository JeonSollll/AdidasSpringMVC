package kr.co.AdidasShopping.member.service;

import org.springframework.stereotype.Service;

import kr.co.AdidasShopping.member.domain.Member;


@Service
public interface MemberService {
	/**
	 * 맴버 등록 Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	
	/**
	 * 맴버 수정 Service
	 * @param 아이디
	 * @return int
	 */
	public int modifyMember(Member member);
	
	/**
	 * 맴버 삭제 Service
	 * @param 아이디
	 * @return int
	 */
	public int removeMember(String memberId);

	/**
	 * 맴버 로그인 Service
	 * @param 아이디, 비번
	 * @return member 객체
	 */
	public Member memberLoginCheck(Member member);
	
	/**
	 * 맴버 확인 Service
	 * @param member
	 * @return member 객체
	 */
	public Member showOneById(String memberId);

}
