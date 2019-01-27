package com.sfs.pbserver.controller;

import com.sfs.pbserver.base.ResultBean;
import com.sfs.pbserver.base.ResultEnum;
import com.sfs.pbserver.base.ResultHandler;
import com.sfs.pbserver.entity.*;
import com.sfs.pbserver.execption.BaseException;
import com.sfs.pbserver.execption.ResourceNotFoundException;
import com.sfs.pbserver.repo.*;
import com.sfs.pbserver.security.GrantedAuthorityImpl;
import com.sfs.pbserver.security.JwtAuthenticationToken;
import com.sfs.pbserver.security.JwtUserDetails;
import com.sfs.pbserver.util.PasswordEncoder;
import com.sfs.pbserver.util.SecurityUtils;
import com.sfs.pbserver.vo.AuthenVo;
import com.sfs.pbserver.vo.IsFocusVo;
import com.sfs.pbserver.vo.LoginBean;
import com.sfs.pbserver.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserFocusRepo userFocusRepo;

    @Autowired
    BarRepo barRepo;

    @Autowired
    BarFocusRepo barFocusRepo;


    @PostMapping(value = "/login")
    ResultBean login(HttpSession session, @RequestBody LoginBean loginBean, HttpServletRequest request){
        String email = loginBean.getEmail();
        String passwd = loginBean.getPasswd();

        User user = userRepo.findUserByEmail(email);

        System.out.println(loginBean);
        System.out.println(user);

        if(user==null)
            throw new BaseException(ResultEnum.LOGIN_ADMIN_NOT_FOUND);

        if (user.getValid()==0) {
            throw new BaseException(ResultEnum.LOGIN_ACCOUNT_FREEZED);
        }

        //session.setAttribute();
        // 系统登录认证

        System.out.println(String.format("rawPass=%s encodePass=%s",passwd,new PasswordEncoder(user.getSalt()).encode(passwd) ));

        if(!new PasswordEncoder(user.getSalt()).encode(passwd).toString().equals(user.getPassword()))
            throw new BaseException(ResultEnum.LOGIN_WRONG_PASSWD);

        JwtAuthenticationToken token = SecurityUtils.login(request,email , passwd, authenticationManager);

        return ResultHandler.ok(new TokenVo(token,user.getEmail()));
    }

    @PutMapping(value = "/register")
    ResultBean register(HttpSession session, @RequestBody User user) {
        assert user.getId()!=null;

        System.out.println("/register："+user);

        String email = user.getEmail();

        User _userRes = userRepo.findUserByEmail(email);

        if(_userRes!=null)
            throw new BaseException(ResultEnum.REGISTER_ACCOUNT_EXISTED);

        String passwd = user.getPassword();
        String salt = user.getName();
        String passwdEncoded = new PasswordEncoder(salt).encode(passwd);
        user.setSalt(salt);
        user.setPassword(passwdEncoded);
        User userRes = userRepo.save(user);
        if(userRes == null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(user.getEmail());
    }

    @GetMapping(value = "/user/info")
    ResultBean adminInfo(HttpSession session, HttpServletRequest request){
        System.out.println("Header:" + request.getHeader("Authorization"));
        System.out.println("JWT:" + SecurityContextHolder.getContext().getAuthentication());

        //Object jwtUserDetailsObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //if(jwtUserDetailsObj instanceof JwtUserDetails){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        //AdminRole adminRole = adminRoleRepo.findAdminRoleById(admin_id);

        //Collection<GrantedAuthorityImpl> grantedAuthoritieList = (Collection<GrantedAuthorityImpl>)jwtUserDetails.getAuthorities();

        return ResultHandler.ok(user);
    }

    @GetMapping(value = "/user/{userId}")
    ResultBean userInfo(@PathVariable Integer userId){
        User user = userRepo.findUserById(userId);
        return ResultHandler.ok(user);
    }

    @GetMapping(value = "/bar/history")
    ResultBean userBarHistory(
            HttpSession session,
            HttpServletRequest request,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable
    ){
        // TODO: 2019/1/26 尚未实现

        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 1
        String email = jwtUserDetails.getUsername();

        System.out.println("ss");

        User user = userRepo.findUserByEmail(email);

        Page<BarFocus> barFocusPage = barFocusRepo.findBarFocusByEmail(email,pageable);


        return ResultHandler.ok(barFocusPage);

    }

    @GetMapping(value = "/user/focus")
    ResultBean userUserFocus( //关注用户列表
            HttpSession session,
            HttpServletRequest request,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable
    ){

        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        Page<UserFocus> userFocusPage = userFocusRepo.findUserFocusByEmail(email,pageable);


        return ResultHandler.ok(userFocusPage);
    }

    @GetMapping(value = "/user/{userId}/focus")
    ResultBean isUserFocused(//是否关注指定用户
            @PathVariable Integer userId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            return ResultHandler.ok(new IsFocusVo(false));

        User userTo = userRepo.findUserById(userId);

        if (userTo==null)
            return ResultHandler.ok(new IsFocusVo(false));

        UserFocus userFocusRes = userFocusRepo.findUserFocusByIdId(user.getId(),userId);

        System.out.println("userFocusRes= "+userFocusRes);

        if(userFocusRes==null)
            return ResultHandler.ok(new IsFocusVo(false));
        else
            return ResultHandler.ok(new IsFocusVo(true));
    }

    @PutMapping(value = "/user/{userId}/focus")
    ResultBean focusUser(//关注指定用户
            @PathVariable Integer userId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            throw new ResourceNotFoundException();

        User userTo = userRepo.findUserById(userId);

        if (userTo==null)
            throw new ResourceNotFoundException();

        if(userFocusRepo.findUserFocusByIdIdNoValidation(user.getId(),userId)==null){
            UserFocus userFocus = new UserFocus();
            userFocus.setUserFrom(new User(user.getId()));
            userFocus.setUserTo(new User(userId));
            userFocusRepo.save(userFocus);
        }else{
            userFocusRepo.updateUserFocusByIdId(user.getId(),userId,1);
        }


        return ResultHandler.ok(userFocusRepo.findUserFocusByIdId(user.getId(),userId));
    }

    @DeleteMapping(value = "/user/{userId}/focus")
    ResultBean unFocusUser(//取消关注指定用户
            @PathVariable Integer userId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            throw new ResourceNotFoundException();

        User userTo = userRepo.findUserById(userId);

        if (userTo==null)
            throw new ResourceNotFoundException();

        userFocusRepo.updateUserFocusByIdId(user.getId(),userId,0);


        return ResultHandler.ok(userFocusRepo.findUserFocusByIdId(user.getId(),userId));
    }

    @GetMapping(value = "/bar/focus")
    ResultBean userBarFocus( //关注贴吧列表
            HttpSession session,
            HttpServletRequest request,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable
    ){

        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        Page<BarFocus> barFocusPage = barFocusRepo.findBarFocusByEmail(email,pageable);


        return ResultHandler.ok(barFocusPage);
    }

    @GetMapping(value = "/bar/{barId}/focus")
    ResultBean isBarFocused( //是否关注指定贴吧
            @PathVariable Integer barId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            return ResultHandler.ok(new IsFocusVo(false));

        BarFocus barFocusRes = barFocusRepo.findBarFocusByIdId(user.getId(),barId);

        System.out.println("barFocusRes= "+barFocusRes);

        if(barFocusRes==null)
            return ResultHandler.ok(new IsFocusVo(false));
        else
            return ResultHandler.ok(new IsFocusVo(true));
    }

    @PutMapping(value = "/bar/{barId}/focus")
    ResultBean focusBar( //关注贴吧
            @PathVariable Integer barId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            throw new ResourceNotFoundException();

        Bar bar = barRepo.findBarById(barId);

        if (bar==null)
            throw new ResourceNotFoundException();

        if(barFocusRepo.findBarFocusByIdIdNoValiddation(user.getId(),barId)==null){
            BarFocus barFocus = new BarFocus();
            barFocus.setUser(new User(user.getId()));
            barFocus.setBar(new Bar(barId));
            barFocusRepo.save(barFocus);
        }else{
            barFocusRepo.updateBarFocusByIdId(user.getId(),barId,1);
        }


        return ResultHandler.ok(barFocusRepo.findBarFocusByIdId(user.getId(),barId));
    }

    @DeleteMapping(value = "/bar/{barId}/focus")
    ResultBean unFocusBar( //取消关注贴吧
            @PathVariable Integer barId
    ){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        if (user==null)
            throw new ResourceNotFoundException();

        Bar bar = barRepo.findBarById(barId);

        if (bar==null)
            throw new ResourceNotFoundException();

        barFocusRepo.updateBarFocusByIdId(user.getId(),barId,0);


        return ResultHandler.ok(barFocusRepo.findBarFocusByIdId(user.getId(),barId));
    }

    @GetMapping(value = "/user/{userId}/post")
    ResultBean userPosts( //用户发贴记录
                          @PathVariable Integer userId,
                         @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable
    ){

        Page<Post> postPage = postRepo.findPostsByUserId(userId,pageable);

        return ResultHandler.ok(postPage);
    }

    @GetMapping(value = "/user/post/home")
    ResultBean userHome( //用户个性化推荐首页
            HttpSession session,
            HttpServletRequest request,
            @PageableDefault(value = 20, sort = { "createdTime" }, direction = Sort.Direction.DESC) Pageable pageable
    ){

        //Object jwtUserDetailsObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //if(jwtUserDetailsObj instanceof JwtUserDetails){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email = jwtUserDetails.getUsername();
        User user = userRepo.findUserByEmail(email);

        Page<Post> postPage = postRepo.findNewPosts(pageable);

        // TODO: 2019/1/25  推荐算法 个性化主页

        return ResultHandler.ok(postPage);
    }

    //user列表
    ResultBean getAdmins(HttpSession session,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable) {
        System.out.println(pageable);
        Page<User> userPage = userRepo.findUsers(pageable);
        return ResultHandler.ok(userRepo);
    }

}
