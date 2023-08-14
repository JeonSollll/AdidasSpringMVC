package kr.co.AdidasShopping.member.service;

import org.springframework.stereotype.Service;

import kr.co.AdidasShopping.member.domain.Member;


@Service
public interface MemberService {
	/**
	 * �ɹ� ��� Service
	 * @param member
	 * @return int
	 */
	public int registerMember(Member member);
	
	/**
	 * �ɹ� ���� Service
	 * @param ���̵�
	 * @return int
	 */
	public int modifyMember(Member member);
	
	/**
	 * �ɹ� ���� Service
	 * @param ���̵�
	 * @return int
	 */
	public int removeMember(String memberId);

	/**
	 * �ɹ� �α��� Service
	 * @param ���̵�, ���
	 * @return member ��ü
	 */
	public Member memberLoginCheck(Member member);
	
	/**
	 * �ɹ� Ȯ�� Service
	 * @param member
	 * @return member ��ü
	 */
	public Member showOneById(String memberId);

}
