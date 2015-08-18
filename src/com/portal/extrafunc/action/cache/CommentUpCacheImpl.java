package com.portal.extrafunc.action.cache;

import com.portal.extrafunc.entity.Comment;
import com.portal.extrafunc.service.CommentService;
import java.util.List;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommentUpCacheImpl implements CommentUpCache, DisposableBean {
	private CommentService commentService;
	private Ehcache cache;

	public Integer upAndGet(Integer id) {
		Comment c = this.commentService.findById(id);
		if (c == null) {
			return null;
		}
		Element e = this.cache.get(id);
		Integer ups;
		// Integer ups;
		if (e != null)
			ups = Integer.valueOf(((Integer) e.getValue()).intValue() + 1);
		else {
			ups = c.getUps();
		}
		this.cache.put(new Element(id, ups));
		return ups;
	}

	public void upsToDB() {
		List<Integer> keys = this.cache.getKeys();
		if (keys.size() <= 0) {
			return;
		}
		for (Integer id : keys) {
			Element e = this.cache.get(id);
			if (e != null) {
				Integer ups = (Integer) e.getValue();
				this.commentService.ups(id, ups);
			}
		}
		this.cache.removeAll();
	}

	public void destroy() throws Exception {
		List<Integer> keys = this.cache.getKeys();
		if (keys.size() <= 0) {
			return;
		}
		for (Integer id : keys) {
			Element e = this.cache.get(id);
			if (e != null) {
				Integer ups = (Integer) e.getValue();
				this.commentService.ups(id, ups);
			}
		}
		this.cache.removeAll();
	}

	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	@Autowired
	public void setCache(@Qualifier("commentUp") Ehcache cache) {
		this.cache = cache;
	}
}
