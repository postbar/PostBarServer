package com.sfs.pbserver.controller;

import com.sfs.pbserver.base.ResultBean;
import com.sfs.pbserver.base.ResultHandler;
import com.sfs.pbserver.entity.*;
import com.sfs.pbserver.execption.ForbiddenException;
import com.sfs.pbserver.execption.ResourceNotFoundException;
import com.sfs.pbserver.repo.*;
import com.sfs.pbserver.security.JwtUserDetails;
import com.sfs.pbserver.vo.CommentVo;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/")
public class PostController {
    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    PostPictureRepo postPictureRepo;

    @Autowired
    CommentPictureRepo commentPictureRepo;

    // 某个吧的贴子列表
    @GetMapping(value = "/bar/{barId}/post")
    ResultBean getPostsByBarId(
            @PathVariable Integer barId,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postPage = postRepo.findPostsByBarId(barId,pageable);
        return ResultHandler.ok(postPage);
    }

    @PutMapping(value = "/bar/{barId}/post")
    ResultBean putPostByBarId(
            @PathVariable Integer barId,
            @RequestBody CommentVo commentVo) {

        System.out.println("commentVo: "+commentVo);

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JwtUserDetails jwtUserDetails = null;

        if(object instanceof String) {
            System.out.println("putCommentByPostId String = "+(String) object);
            throw new ForbiddenException();
        }

        if(object instanceof JwtUserDetails) {
            jwtUserDetails = (JwtUserDetails) object;
            System.out.println("putCommentByPostId jwtUserDetails = "+ jwtUserDetails);
        }
        String email = jwtUserDetails.getUsername();

        System.out.println("email="+email);

        User userRes = userRepo.findUserByEmail(email);
        if (userRes == null)
            throw new ForbiddenException();

        List<String> imageList = commentVo.getImages();

        Post post = new Post();
        post.setContent(commentVo.getContent());
        post.setUser(new User(userRes.getId()));
        post.setBar(new Bar(barId));
        post.setOrdre(imageList.size());

        Post postRes = postRepo.save(post);

        for (int i=0;i<imageList.size();i++){
            postPictureRepo.save(new PostPicture(postRes,i,imageList.get(i)));
        }

        return ResultHandler.ok(postRes);
    }

    // 帖子
    @GetMapping(value = "/bar/{barId}/post/{postId}")
    ResultBean getPostByPostId(
            @PathVariable Integer barId,
            @PathVariable Integer postId) {
        Post post = postRepo.findPostById(postId);
        return ResultHandler.ok(post);
    }

    @GetMapping(value = "/bar/{barId}/post/{postId}/comment")
    ResultBean getCommentByPostId(
            @PathVariable Integer barId,
            @PathVariable Integer postId,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Comment>  commentPage= commentRepo.findCommentByPostId(postId,pageable);
        return ResultHandler.ok(commentPage);
    }

    @PutMapping(value = "/bar/{barId}/post/{postId}/comment")
    ResultBean putCommentByPostId(
            @PathVariable Integer barId,
            @PathVariable Integer postId,
            @RequestBody CommentVo commentVo) {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        JwtUserDetails jwtUserDetails = null;

        if(object instanceof String) {
            System.out.println("putCommentByPostId String = "+(String) object);
            throw new ForbiddenException();
        }

        if(object instanceof JwtUserDetails) {
            jwtUserDetails = (JwtUserDetails) object;
            System.out.println("putCommentByPostId jwtUserDetails = "+ jwtUserDetails);
        }
        String email = jwtUserDetails.getUsername();

        System.out.println("email="+email);

        User userRes = userRepo.findUserByEmail(email);
        if (userRes == null)
            throw new ForbiddenException();

        List<String> imageList = commentVo.getImages();

        Comment comment = new Comment();
        comment.setContent(commentVo.getContent());
        comment.setUser(new User(userRes.getId()));
        comment.setPost(new Post(postId));
        comment.setOrdre(imageList.size());

        Comment commentRes = commentRepo.save(comment);

        for (int i=0;i<imageList.size();i++){
            commentPictureRepo.save(new CommentPicture(commentRes,i,imageList.get(i)));
        }

        return ResultHandler.ok(commentRes);
    }

    public String getSubUtilOne(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){
            return m.group(1);
        }
        return "";
    }

    @GetMapping(value = "/bar/{barId}/post/{postId}/image/{orderId}")
    public ResponseEntity<byte[]>  getPostImageByPostIdAndImageOrder(
            @PathVariable Integer barId,
            @PathVariable Integer postId,
            @PathVariable Integer orderId, HttpServletResponse response) {
        PostPicture postPicture = postPictureRepo.findPostPictureByPostIdAndOrderId(postId,orderId);

        if (postPicture==null)
            throw new ResourceNotFoundException();

        System.out.println("Get /bar/{barId}/post/{postId}/image/{orderId} ="+postPicture.getContent());

        String[] list= postPicture.getContent().split(",");
        byte[] decodedBytes = Base64.getDecoder().decode(list[1].getBytes());
        System.out.println("image type="+getSubUtilOne(list[0],":(.*?);"));
        response.setContentType(getSubUtilOne(list[0],":(.*?);"));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", getSubUtilOne(list[0],":(.*?);"));

        return new ResponseEntity<byte[]>(decodedBytes, responseHeaders, HttpStatus.OK);

        //return decodedBytes;
    }


    @GetMapping(value = "/bar/{barId}/post/{postId}/comment/{commentId}/image/{orderId}")
    public ResponseEntity<byte[]>  getCommentImageByCommentIdAndImageOrder(
            @PathVariable Integer barId,
            @PathVariable Integer postId,
            @PathVariable Integer commentId,
            @PathVariable Integer orderId) {
        CommentPicture commentPicture = commentPictureRepo.findCommentPictureByCommentIdAndOrderId(commentId,orderId);
        String[] list= commentPicture.getContent().split(",");
        byte[] decodedBytes = Base64.getDecoder().decode(list[1].getBytes());
        System.out.println("image type="+getSubUtilOne(list[0],":(.*?);"));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", getSubUtilOne(list[0],":(.*?);"));

        return new ResponseEntity<byte[]>(decodedBytes, responseHeaders, HttpStatus.OK);

        //return decodedBytes;
    }
}
