package org.lrj.code.fetchRobot;

import java.util.List;

public interface CaptureArticleDAO {
    public List<Article> getCaptureArticle(String url, String baseurl);
    public boolean createArticleToDisk(Article article);
}
