package com.sfs.pbserver.controller;

import com.sfs.pbserver.base.ResultBean;
import com.sfs.pbserver.base.ResultHandler;
import com.sfs.pbserver.entity.*;
import com.sfs.pbserver.repo.*;
import com.sfs.pbserver.vo.SearchVo;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SearchController {

    @Autowired
    PostRepo postRepo;

    @Autowired
    BarRepo barRepo;

    @Autowired
    BarFocusRepo barFocusRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserFocusRepo userFocusRepo;

    // 搜索贴
    @PostMapping(value = "/search/post")
    ResultBean searchPostList(
            @RequestBody SearchVo searchVo,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> postPage = postRepo.findPostsByContent(searchVo.getContent(),pageable);
        return ResultHandler.ok(postPage);
    }

    // 搜索吧
    @PostMapping(value = "/search/bar")
    ResultBean searchBarList(
            @RequestBody SearchVo searchVo,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Bar> barPage = barRepo.findBarsByName(searchVo.getContent(),pageable);
        return ResultHandler.ok(barPage);
    }

    // 搜索人
    @PostMapping(value = "/search/user")
    ResultBean searchUserList(
            @RequestBody SearchVo searchVo,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userPage = userRepo.findUsersByName(searchVo.getContent(),pageable);
        return ResultHandler.ok(userPage);
    }
}
