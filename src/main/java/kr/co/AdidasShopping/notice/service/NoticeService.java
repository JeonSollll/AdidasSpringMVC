package kr.co.AdidasShopping.notice.service;

import kr.co.AdidasShopping.notice.domain.Notice;
import kr.co.AdidasShopping.notice.domain.PageData;

public interface NoticeService {
	
	/**
	 * ���� ���
	 * @param notice
	 * @return int
	 */
	public int registerNotice(Notice notice);
	
	/**
	 * ���� ����
	 * @param notice
	 * @return int
	 */
	public int modifyNotice(Notice notice);
	
	/**
	 * ���� ����
	 * @param ������ȣ
	 * @return int
	 */
	public int removeNotice(int noticeNo);
	
	public PageData selectNoticeList(int currentPage);
	
	/**
	 * ���� Ȯ��
	 * @param noticeNo
	 * @return ����
	 */
	public Notice showOneByNo(int noticeNo);
	
}
