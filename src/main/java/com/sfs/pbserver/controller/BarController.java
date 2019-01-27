package com.sfs.pbserver.controller;

import com.sfs.pbserver.base.ResultBean;
import com.sfs.pbserver.base.ResultEnum;
import com.sfs.pbserver.base.ResultHandler;
import com.sfs.pbserver.entity.Bar;
import com.sfs.pbserver.entity.Post;
import com.sfs.pbserver.execption.BaseException;
import com.sfs.pbserver.repo.BarRepo;
import com.sfs.pbserver.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BarController {

    @Autowired
    BarRepo barRepo;

    // 某个吧
    @GetMapping(value = "/bar/{barId}")
    ResultBean getBarInfoByBarId(
            @PathVariable Integer barId) {
        Bar bar = barRepo.findBarById(barId);
        return ResultHandler.ok(bar);
    }

    // 新增bar
    @PutMapping(value = "/bar")
    ResultBean putNewBar(
            @RequestBody Bar bar) {

        Bar barRes = barRepo.findBarByName(bar.getName());

        if (barRes!=null)
            throw new BaseException(ResultEnum.REGISTER_BAR_EXISTED);

        Bar barRes1 = barRepo.save(bar);
        return ResultHandler.ok(barRes1);
    }
}
