package com.yz.blog.service;

import com.yz.blog.Bean.Comment;
import com.yz.blog.dao.commentRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class commentServiceImpl implements commentService {

    //存放迭代找出的所有子代的集合，也是一个临时集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Autowired
    private commentRepositoryDao commentRepositoryDao;

    // 列表展示评论
    @Override
    public List<Comment> getListCommentByBlogId(Long blogId) {

        //根据blogid，查询出该博客的评论父级评论, 父级评论的id为null.
        List<Comment> comments = commentRepositoryDao.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));
        for(Comment comment : comments){
            // 当前父级评论的id
            Long id = comment.getId();
            String parentusername = comment.getUsername();
            //根据某个blog的id和父类评论的id，去查询该父级评论所有的子级评论
            List<Comment> childComments = commentRepositoryDao.findByBlogIdParentIdNotNull(blogId,id);
//            递归查询出子评论,并且放入临时集合中
            combineChildren(blogId, childComments, parentusername);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }
    private void combineChildren(Long blogId, List<Comment> childComments, String parentusername) {
//        判断是否有一级子评论
        if(childComments.size() > 0){
//                循环找出子评论的id
            for(Comment childComment : childComments){
                String parentusername1 = childComment.getUsername();
                childComment.setParentUsername(parentusername);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
//                    查询出子二级评论
                recursively(blogId, childId, parentusername1);
            }
        }
    }
    // 递归调用的一个方法，一层一层的找到子类评论
    private void recursively(Long blogId, Long childId, String parentusername) {
//        根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentRepositoryDao.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentUsername1 = replayComment.getUsername();
                replayComment.setParentUsername(parentusername);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentUsername1);
            }
        }
    }

    // 新增评论
    @Transactional
    @Override
    public int saveComment(Comment comment) {
        Long prentCommentId = comment.getParentComment().getId();
        if(prentCommentId != -1){
            // 子级评论
            comment.setParentComment(commentRepositoryDao.getPrentCommentById(prentCommentId));
        }
        else {
            // 父级评论

            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepositoryDao.saveComment(comment);
    }
    @Transactional
    @Override
    public void deleteComment(Comment comment, Long id) {

    }
}
