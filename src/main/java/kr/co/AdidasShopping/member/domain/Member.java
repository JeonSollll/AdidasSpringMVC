package kr.co.AdidasShopping.member.domain;

import java.sql.*;

public class Member {
	private String memberName;
	private String memberId;
	private String memberPw;
	private String memberEmail;
	private Date memberBirthDate;
	private String memberGender;
	private String memberAddress;
	private String memberPhone;
	private Timestamp memberDate;
	private Timestamp updateDate;
	private String memberYn;
	
	public Member() {}
	
	public Member(String memberId, String memberPw) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
	}



	public Member(String memberName, String memberId, String memberPw, String memberEmail, Date memberBirthDate,
			String memberGender, String memberAddress, String memberPhone) {
		super();
		this.memberName = memberName;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberEmail = memberEmail;
		this.memberBirthDate = memberBirthDate;
		this.memberGender = memberGender;
		this.memberAddress = memberAddress;
		this.memberPhone = memberPhone;
	}
	
	public Member(String memberId, String memberPw, String memberEmail, String memberAddress, String memberPhone) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberEmail = memberEmail;
		this.memberAddress = memberAddress;
		this.memberPhone = memberPhone;
	}

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public Date getMemberBirthDate() {
		return memberBirthDate;
	}
	public void setMemberBirthDate(Date memberBirthDate) {
		this.memberBirthDate = memberBirthDate;
	}
	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public String getMemberAddress() {
		return memberAddress;
	}
	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Timestamp getMemberDate() {
		return memberDate;
	}
	public void setMemberDate(Timestamp memberDate) {
		this.memberDate = memberDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getMemberYn() {
		return memberYn;
	}
	public void setMemberYn(String memberYn) {
		this.memberYn = memberYn;
	}
	
	@Override
	public String toString() {
		return "맴버 [이름=" + memberName + ", 아이디=" + memberId + ", 비밀번호=" + memberPw
				+ ", 이메일=" + memberEmail + ", 생년월일=" + memberBirthDate + ", 성별="
				+ memberGender + ", memberAddress=" + memberAddress + ", 휴대전화=" + memberPhone + ", 가입일="
				+ memberDate + ", 수정일=" + updateDate + ", 회원여부=" + memberYn + "]";
	}
	
}
