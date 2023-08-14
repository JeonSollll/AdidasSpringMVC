package kr.co.AdidasShopping.notice.service;

import kr.co.AdidasShopping.notice.domain.Notice;
import kr.co.AdidasShopping.notice.domain.PageData;

public interface NoticeService {
	
	/**
	 * 공지 등록
	 * @param notice
	 * @return int
	 */
	public int registerNotice(Notice notice);
	
	/**
	 * 공지 수정
	 * @param notice
	 * @return int
	 */
	public int modifyNotice(Notice notice);
	
	/**
	 * 공지 삭제
	 * @param 공지번호
	 * @return int
	 */
	public int removeNotice(int noticeNo);
	
	public PageData selectNoticeList(int currentPage);
	
	/**
	 * 공지 확인
	 * @param noticeNo
	 * @return 공지
	 */
	public Notice showOneByNo(int noticeNo);
	
}
