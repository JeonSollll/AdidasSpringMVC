package kr.co.AdidasShopping.notice.store.logic;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.AdidasShopping.notice.domain.Notice;
import kr.co.AdidasShopping.notice.store.NoticeStore;

@Repository
public class NoticeStoreLogic implements NoticeStore {

	@Override
	public int insertNotice(SqlSession sqlSession, Notice notice) {
		int result = sqlSession.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	@Override
	public int updateNotice(SqlSession sqlSession, Notice notice) {
		int result = sqlSession.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	@Override
	public int deleteNoticebyNo(SqlSession sqlSession, int noticeId) {
		int result = sqlSession.delete("NoticeMapper.deleteNoticebyNo", noticeId);
		return result;
	}

	@Override
	public List<Notice> selectNoticeList(SqlSession sqlSession, int currentPage) {
		int limit = 10;
		int offset = (currentPage-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = sqlSession.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	@Override
	public Notice selectOneByNo(SqlSession sqlSession, int noticeId) {
		Notice nOne = sqlSession.selectOne("NoticeMapper.selectOneByNo", noticeId);
		return nOne;
	}

	@Override
	public String generatePageNavi(SqlSession sqlSession, int currentPage) {
		int totalCount = getTotalCount(sqlSession);
		int recordCountPage = 10;
		int naviTotalCount = (int) Math.ceil((double) totalCount / recordCountPage);
		int naviCountPage = 5;
		int startNavi = ((currentPage - 1)/naviCountPage) * naviCountPage + 1;
		int endNavi = startNavi + naviCountPage - 1;
		// endNavi값이 총 범위의 개수보다 커지는 것을 막아주는 코드
		if(endNavi > naviTotalCount) {
			endNavi = naviTotalCount;
		}
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi == 1) {
			needPrev = false;
		}
		if(endNavi == naviTotalCount) {
			needNext = false;
		}
		StringBuilder result = new StringBuilder();
		if(needPrev) result.append("<a href='/notice/list.do?currentPage=" + (startNavi-1) + "''>[이전]</a>");
		for(int i = startNavi; i <= endNavi; i++) {
			result.append("<a href='/notice/list.do?currentPage=" + i + "'>" + i + "</a>&nbsp;&nbsp;");
		}
		if(needNext) result.append("<a href='/notice/list.do?currentPage=" + (endNavi+1) + "''>[다음]</a>");
		return result.toString();
	}
	
	private int getTotalCount(SqlSession session) {
		int totalCount = session.selectOne("NoticeMapper.getTotalCount");
		return totalCount;
	}

}
