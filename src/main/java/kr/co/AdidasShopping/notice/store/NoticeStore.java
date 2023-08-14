package kr.co.AdidasShopping.notice.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.co.AdidasShopping.notice.domain.Notice;

public interface NoticeStore {

	public int insertNotice(SqlSession sqlSession, Notice notice);
	
	public int updateNotice(SqlSession sqlSession, Notice notice);

	public int deleteNoticebyNo(SqlSession sqlSession, int noticeId);

	public List<Notice> selectNoticeList(SqlSession sqlSession, int currentPage);

	public Notice selectOneByNo(SqlSession sqlSession, int noticeId);
	
	public String generatePageNavi(SqlSession session, int currentPage);

}
