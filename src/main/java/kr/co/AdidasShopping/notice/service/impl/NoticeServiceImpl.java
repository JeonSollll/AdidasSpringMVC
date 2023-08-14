package kr.co.AdidasShopping.notice.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.AdidasShopping.notice.domain.Notice;
import kr.co.AdidasShopping.notice.domain.PageData;
import kr.co.AdidasShopping.notice.service.NoticeService;
import kr.co.AdidasShopping.notice.store.NoticeStore;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private NoticeStore nStore;

	@Override
	public int registerNotice(Notice notice) {
		int result = nStore.insertNotice(sqlSession, notice);
		return result;
	}

	@Override
	public int modifyNotice(Notice notice) {
		int result = nStore.updateNotice(sqlSession, notice);
		return result;
	}

	@Override
	public int removeNotice(int noticeNo) {
		int result = nStore.deleteNoticebyNo(sqlSession, noticeNo);
		return result;
	}

	@Override
	public Notice showOneByNo(int noticeNo) {
		Notice nOne = nStore.selectOneByNo(sqlSession, noticeNo);
		return nOne;
	}

	@Override
	public PageData selectNoticeList(int currentPage) {
		List<Notice> nList = nStore.selectNoticeList(sqlSession, currentPage);
		String pageNavi = nStore.generatePageNavi(sqlSession,currentPage);
		PageData pd = new PageData(nList, pageNavi);
		return pd;
	}

}
